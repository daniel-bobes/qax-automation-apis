@Comments @GetComments
Feature: Consulta de comentarios
  Como tester de la API de GoRest
  Quiero validar la consulta de la lista de comentarios (GET /comments)
  Para asegurar que el sistema responde correctamente a las peticiones del catálogo de comentarios.

  Scenario Outline: CP01-CP02 Consultar lista de comentarios sin enviar token o enviando un token válido
    Given que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición petición para consultar la lista de comentarios
    Then la API responde con un código de estado 200
    And la lista de comentarios no esta vacía
    And todos los comentarios tienen los campos id, post_id, name, email y body

    Examples:
      | tipo_token  |
      | ausente     |
      | válido      |

  Scenario: CP03 Consultar lista de comentarios con token no válido
    Given que uso un tipo de autenticación "inválido"
    When realizo una petición petición para consultar la lista de comentarios
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "Invalid token"