@Users @GetUser
Feature: Consulta de Usuarios
  Como tester de la API de GoRest
  Quiero validar la consulta de usuarios (GET /users/userId)
  Para asegurar que la información recuperada coincide con el recurso solicitado.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente

  Scenario Outline: CP01-CP02 Intentar consultar un usuario sin enviar token o enviando un token inválido
    And que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición para consultar el usuario
    Then la API responde con un código de estado <codigo_estado>
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | codigo_estado   | mensaje_error         |
      | ausente     | 404             | Resource not found    |
      | inválido    | 401             | Invalid token         |

  Scenario: CP03 Intentar consultar un usuario no existente
    And que utilizo un ID de "usuario" inexistente 999999
    When realizo una petición para consultar el usuario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP04 Intentar consultar usuario eliminado previamente
    Given que tengo un usuario creado anteriormente
    And realizo una petición para eliminar el usuario
    When realizo una petición para consultar el usuario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP05 Consultar usuario existente
    Given que tengo un usuario creado anteriormente
    When realizo una petición para consultar el usuario
    Then la API responde con un código de estado 200
    And la respuesta contiene un identificador de usuario