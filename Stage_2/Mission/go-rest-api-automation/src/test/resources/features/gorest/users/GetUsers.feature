@Users @GetUsers
Feature: Consulta Lista de Usuarios
  Como tester de la API de GoRest
  Quiero validar la consulta de la lista de usuarios (GET /users)
  Para asegurar que el sistema responde correctamente a las peticiones del catálogo de usuarios.

  Scenario Outline: CP01-CP02 Consultar la lista de usuarios sin enviar token o enviando un tokén válido
    Given que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición petición para consultar la lista de usuarios
    Then la API responde con un código de estado 200
    And la lista de usuarios no esta vacía
    And todos los usuarios tienen los campos id, name, email, gender y status

    Examples:
      | tipo_token  |
      | ausente     |
      | válido      |

  Scenario: CP03 Intentar consultar la lista de usuarios enviando un token inválido
    Given que uso un tipo de autenticación "inválido"
    When realizo una petición petición para consultar la lista de usuarios
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "Invalid token"