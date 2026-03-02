@Posts @GetUserPosts
Feature: Consulta de Publicaciones de un Usuario
  Como tester de la API de GoRest
  Quiero validar la consulta de publicaciones de un usuario (GET /users/userId/posts)
  Para asegurar que la información recuperada coincide con el recurso solicitado.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente

  Scenario: CP01 Consultar lista de publicaciones de un usuario sin enviar token
    Given que tengo una publicación creada para ese usuario
    And que uso un tipo de autenticación "ausente"
    When realizo una petición para consultar las publicaciones del usuario
    Then la API responde con un código de estado 200
    And la lista de publicaciones esta vacía

  Scenario: CP02 Consultar lista de publicaciones de un usuario con token no válidon
    Given que tengo una publicación creada para ese usuario
    And que uso un tipo de autenticación "inválido"
    When realizo una petición para consultar las publicaciones del usuario
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "Invalid token"

  Scenario: CP03 Consultar publicaciones de un usuario recien creado
    When realizo una petición para consultar las publicaciones del usuario
    Then la API responde con un código de estado 200
    And la lista de publicaciones esta vacía
    
  Scenario: CP04 Consultar publicaciones de un usuario inexistente
    Given que utilizo un ID de "usuario" inexistente 999999
    When realizo una petición para consultar las publicaciones del usuario
    Then la API responde con un código de estado 200
    And la lista de publicaciones esta vacía

  Scenario: CP05 Consultar publicaciones de un usuario eliminado previamente
    Given realizo una petición para eliminar el usuario
    When realizo una petición para consultar las publicaciones del usuario
    Then la API responde con un código de estado 200
    And la lista de publicaciones esta vacía

  Scenario: CP06 Consultar publicaciones de un usuario creado con una publicación
    Given que tengo una publicación creada para ese usuario
    When realizo una petición para consultar las publicaciones del usuario
    Then la API responde con un código de estado 200
    And la lista de publicaciones no esta vacía