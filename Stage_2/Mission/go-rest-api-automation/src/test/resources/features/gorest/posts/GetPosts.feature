@Posts @GetPosts
Feature: Consulta de publicaciones
  Como tester de la API de GoRest
  Quiero validar la consulta de la lista de publicaciones (GET /posts)
  Para asegurar que el sistema responde correctamente a las peticiones del catálogo de publicaciones.

  Scenario Outline: CP01-CP02 Consultar la lista de publicaciones sin enviar token o enviando un tokén válido
    Given que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición petición para consultar la lista de publicaciones
    Then la API responde con un código de estado 200
    And la lista de publicaciones no esta vacía
    And todas las publicaciones tienen los campos id, user_id, title y body

    Examples:
      | tipo_token  |
      | ausente     |
      | válido      |

  Scenario: CP03 Intentar consultar la lista de publicaciones enviando un token inválido
    Given que uso un tipo de autenticación "inválido"
    When realizo una petición petición para consultar la lista de publicaciones
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "Invalid token"