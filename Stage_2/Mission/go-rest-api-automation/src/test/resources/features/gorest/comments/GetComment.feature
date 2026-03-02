@Comments @GetComment
Feature: Consulta de Comentarios
  Como tester de la API de GoRest
  Quiero validar la consulta de comentarios (GET /comments/commentId)
  Para asegurar que la información recuperada coincide con el recurso solicitado.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario
    And que tengo un comentario creado para esa publicación

  Scenario Outline: CP01-02 Intentar consultar un comentario sin enviar token o enviando un token inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición para consultar el comentario
    Then la API responde con un código de estado <codigo_estado>
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | codigo_estado   | mensaje_error         |
      | ausente     | 404             | Resource not found    |
      | inválido    | 401             | Invalid token         |

  Scenario: CP03 Intentar consultar un comentario no existente
    Given que utilizo un ID de "comentario" inexistente 999999
    When realizo una petición para consultar el comentario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP04 Intentar consultar comentario eliminado previamente
    Given realizo una petición para eliminar el comentario
    When realizo una petición para consultar el comentario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP05 Consultar comentario existente de una publicación
    When realizo una petición para consultar el comentario
    Then la API responde con un código de estado 200
    And la respuesta contiene el identificador del comentario

