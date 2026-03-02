@Comments @ModifyComment
Feature: Modificación de Comentarios
  Como tester de la API de GoRest
  Quiero validar la modificación de comentarios (PUT /comments/commentId, PATCH /comments/commentId)
  Para asegurar que los cambios en el comentario se persisten correctamente.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario
    And que tengo un comentario creado para esa publicación

  Scenario Outline: CP01-CP02 Intentar modificar completamente un comentario sin enviar token o enviando un tokén inválido
    And que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición para modificar el comentario
    Then la API responde con un código de estado <codigo_estado>
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | codigo_estado   | mensaje_error         |
      | ausente     | 404             | Resource not found    |
      | inválido    | 401             | Invalid token         |

  Scenario Outline: CP03-CP04 Intentar modificar completamente un comentario con datos inválidos
    Given que preparo un comentario con "<name>", "<email>" y "<body>"
    When realizo una petición para modificar el comentario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "<campo>" y el mensaje "<mensaje_error>"

    Examples:
      | name  | email         | body                | campo   | mensaje_error   |
      |       | test@test.com | Contenido de prueba | name    | can't be blank  |
      | Name  |               | Contenido de prueba | email   | can't be blank  |
      | Name  | test@test.com |                     | body    | can't be blank  |
      | Name  | test_test.com | Contenido de prueba | email    | is invalid     |

  Scenario: CP05 Intentar modificar completamente un comentario no existente
    Given que utilizo un ID de "comentario" inexistente 999999
    When realizo una petición para modificar el comentario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP06 Intentar modificar un comentario eliminado previamente
    And realizo una petición para eliminar el comentario
    When realizo una petición para modificar el comentario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP07 Modificar completamente un comentario con datos válidos
    And que preparo un comentario válido
    When realizo una petición para modificar el comentario
    Then la API responde con un código de estado 200
    And la respuesta contiene el identificador del comentario
    And la respuesta contiene los datos enviados en la petición al modificar el comentario