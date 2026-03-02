@Posts @DeletePost
Feature: Eliminación de Publicaciones
  Como tester de la API de GoRest
  Quiero validar la eliminación de publicaciones (DELETE /posts/postId)
  Para asegurar que las publicaciones se eliminan correctamente y no queda rastro en el sistema.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario

  Scenario Outline: CP01-CP02 Intentar eliminar una publicación de un usuario sin enviar token o enviando un token inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición para eliminar la publicación
    Then la API responde con un código de estado <codigo_estado>
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | codigo_estado   | mensaje_error         |
      | ausente     | 404             | Resource not found    |
      | inválido    | 401             | Invalid token         |

  Scenario: CP03 Intentar eliminar una publicación no existente
    Given que utilizo un ID de "publicación" inexistente 999999
    When realizo una petición para eliminar la publicación
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP04 Intentar eliminar publicación eliminada previamente
    Given realizo una petición para eliminar la publicación
    And la API responde con un código de estado 204
    When realizo una petición para eliminar la publicación
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP05 Eliminar una publicación existente
    When realizo una petición para eliminar la publicación
    Then la API responde con un código de estado 204