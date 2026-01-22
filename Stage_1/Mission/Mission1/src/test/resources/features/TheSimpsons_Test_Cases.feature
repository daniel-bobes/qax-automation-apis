Feature: Exponer listado y detalle de personajes de The Simpsons con paginación
  Como consumidor externo de la API (aplicaciones cliente y servicios internos)
  Quiero obtener un listado paginado de personajes y consultar el detalle por id
  Para mostrar información consistente y navegable en mis aplicaciones y reutilizarla en otros servicios.

  Background:
    Given que la API está disponible en "https://thesimpsonsapi.com/api"

  # ------------------------------------
  # Consultar listado de personajes
  # ------------------------------------
  Scenario: CP01 - Consultar listado de personajes paginado, validar campos obligatorios
    When realizo una petición GET al endpoint "/characters"
    Then la respuesta debe devolver un código de estado 200
    And debe tener los siguientes campos:
    | count   |
    | next    |
    | prev    |
    | pages   |
    | results |

  Scenario: CP02 - Consultar listado de personajes paginado, validar tipo de contenido de la respuesta
    When realizo una petición GET al endpoint "/characters"
    Then la respuesta debe devolver un código de estado 200
    And el content-type debe ser "application/json; charset=utf-8"

  Scenario: CP03 - Consultar listado de personajes paginado, validar estructura minima de cada personaje del listado
    When realizo una petición GET al endpoint "/characters"
    Then la respuesta debe devolver un código de estado 200
    And cada personaje del listado debe tener los siguientes campos:
    | id              |
    | age             |
    | birthdate       |
    | gender          |
    | name            |
    | occupation      |
    | portrait_path   |
    | phrases         |
    | status          |

  Scenario: CP04 - Consultar listado de personajes paginado, validar primera página sin indicar un numéro de página
    When realizo una petición GET al endpoint "/characters"
    Then la respuesta debe devolver un código de estado 200
    And prev debe ser null
    And next no debe ser null
    And next debe contener el valor "page=2"

  Scenario: CP05 - Consultar listado de personajes paginado, consultar primera página
    When realizo una petición GET al endpoint "/characters?page=1"
    Then la respuesta debe devolver un código de estado 200
    And prev debe ser null
    And next no debe ser null
    And next debe contener el valor "page=2"

  Scenario: CP06 - Consultar listado de personajes paginado, consultar segunda página
    When realizo una petición GET al endpoint "/characters?page=2"
    Then la respuesta debe devolver un código de estado 200
    And prev no debe ser null
    And next no debe ser null
    And prev debe contener el valor "page=1"
    And next debe contener el valor "page=3"

  Scenario: CP07 - Consultar listado de personajes paginado, consultar penúltima página
    When realizo una petición GET al endpoint "/characters?page=59"
    Then la respuesta debe devolver un código de estado 200
    And prev no debe ser null
    And next no debe ser null
    And prev debe contener el valor "page=58"
    And next debe contener el valor "page=60"

  Scenario: CP08 - Consultar listado de personajes paginado, consultar última página
    When realizo una petición GET al endpoint "/characters?page=60"
    Then la respuesta debe devolver un código de estado 200
    And prev no debe ser null
    And next debe ser null
    And prev debe contener el valor "page=59"

  Scenario: CP09 - Consultar listado de personajes paginado, consultar página mayor a la última página
    When realizo una petición GET al endpoint "/characters?page=61"
    Then la respuesta debe devolver un código de estado 200
    And prev debe ser null
    And next debe ser null
    And la lista de personajes debe estar vacia

  Scenario: CP10 - Consultar listado de personajes paginado, validar cálculo de metadatos
    When realizo una petición GET al endpoint "/characters"
    Then la respuesta debe devolver un código de estado 200
    And el campo "count" devuelve el total de personajes disponibles que es 1182
    And el tamaño de la lista "results" debe ser menor o igual a "pageSize" que es 20

  Scenario: CP11 - Consultar listado de personajes paginado, validar orden
    When realizo una petición GET al endpoint "/characters"
    Then la respuesta debe devolver un código de estado 200
    And el campo "result" debe estar ordenado por el campo "id" de forma ascendente

  # ------------------------------------
  # Consultar detalle de un personaje
  # ------------------------------------
  Scenario: CP12 - Consultar detalle de un personaje correctamente, validar campos obligatorios
    Given que tengo un identificador de un personaje que existe
    | id  |
    | 3   |
    When realizo una petición GET al endpoint "/characters/{id}"
    Then la respuesta debe devolver un código de estado 200
    And debe contener los siguientes campos:
    | id              |
    | age             |
    | birthdate       |
    | gender          |
    | name            |
    | occupation      |
    | portrait_path   |
    | phrases         |
    | status          |

  Scenario: CP13 - Consultar detalle de un personaje correctamente, el primer personaje debe ser Homer Simpson
    Given que tengo un identificador del primer personaje, que es Homer Simpson
    | id  |
    | 1   |
    When realizo una petición GET al endpoint "/characters/{id}"
    Then la respuesta debe devolver un código de estado 200
    And el campo "name" debe ser "Homer Simpson"

  Scenario: CP14 - Consultar detalle de un personaje, validar formato de la fecha de nacimiento
    Given que tengo un identificador de un personaje que existe
    | id  |
    | 3   |
    When realizo una petición GET al endpoint "/characters/{id}"
    Then la respuesta debe devolver un código de estado 200
    And el campo "birthdate" debe cumplir con el formato "^\d{4}-\d{2}-\d{2}$"

  Scenario: CP15 - Consultar detalle de un personaje, validar formato de la ruta del retrato
    Given que tengo un identificador de un personaje que existe
    | id  |
    | 3   |
    When realizo una petición GET al endpoint "/characters/{id}"
    Then la respuesta debe devolver un código de estado 200
    And el campo "portrait_path" debe cumplir con el formato "^/character/\d+.webp$"

  Scenario: CP16 - Consultar detalle de un personaje, validar formato de la lista de frases
    Given que tengo un identificador de un personaje que existe
    | id  |
    | 3   |
    When realizo una petición GET al endpoint "/characters/{id}"
    Then la respuesta debe devolver un código de estado 200
    And el campo "phrases" no debe ser null
    And el campo "phrases" debe ser un array

  # ------------------------------------
  # Errores bien definidos
  # ------------------------------------
  Scenario: CP17 - Consultar listado de personajes con paginación indicando un valor de página no entero
    Given que tengo un valor de página no válido y de tipo no entero
    | page  |
    | test  |
    When realizo una petición GET al endpoint "/characters?page={page}"
    Then la respuesta debe devolver un código de estado 400
    And debe mostrar un mensaje "Invalid page parameter"
    And debe contener los siguientes campos:
    | message     |
    | error       |
    | statusCode  |

  Scenario: CP18 - Consultar listado de personajes con paginación indicando un valor de página entero menor a 1
    Given que tengo un valor de página no válido y de tipo entero menor a 1
    | page  |
    | 0  |
    When realizo una petición GET al endpoint "/characters?page={page}"
    Then la respuesta debe devolver un código de estado 400
    And debe mostrar un mensaje "Invalid page parameter"
    And debe contener los siguientes campos:
    | error       |

  Scenario: CP19 - Consultar detalle de un personaje inexistente, identificador numérico
    Given que tengo el identificador de un personaje que no existe y que es de tipo numérico
    | id      |
    | 9999    |
    When realizo una petición GET al endpoint "/characters/{id}"
    Then la respuesta debe devolver un código de estado 404
    And debe mostrar un mensaje "Character not found"
    And debe devolver el id enviado
    And debe contener los siguientes campos:
    | error |
    | id    |

  Scenario: CP20 - Consultar detalle de un personaje inexistente, identificador no numérico
    Given que tengo el identificador de un personaje que no existe y que no es de tipo numérico
      | id      |
      | test    |
    When realizo una petición GET al endpoint "/characters/{id}"
    Then la respuesta debe devolver un código de estado 404
    And debe mostrar un mensaje "Character not found"
    And debe devolver el id enviado
    And debe contener los siguientes campos:
      | error |
      | id    |