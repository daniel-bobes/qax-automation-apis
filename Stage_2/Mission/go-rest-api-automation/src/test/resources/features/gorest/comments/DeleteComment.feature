@Comments @DeleteComment
Feature: Eliminación de Comentarios
  Como tester de la API de GoRest
  Quiero validar la eliminación de comentarios (DELETE /comments/commentsId)
  Para asegurar que los comentarios se eliminan correctamente y no queda rastro en el sistema.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario
    And que tengo un comentario creado para esa publicación

  Scenario Outline: CP01-CP02 Intentar eliminar un comentario de una publicación sin enviar token o enviando un token inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición para eliminar el comentario
    Then la API responde con un código de estado <codigo_estado>
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | codigo_estado   | mensaje_error         |
      | ausente     | 404             | Resource not found    |
      | inválido    | 401             | Invalid token         |

  Scenario: CP03 Intentar eliminar un comentario no existente
    And que utilizo un ID de "comentario" inexistente 999999
    When realizo una petición para eliminar el comentario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP04 Intentar eliminar un comentario eliminado previamente
    And realizo una petición para eliminar el comentario
    And la API responde con un código de estado 204
    When realizo una petición para eliminar el comentario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP05 Eliminar un comentario existente
    When realizo una petición para eliminar el comentario
    Then la API responde con un código de estado 204