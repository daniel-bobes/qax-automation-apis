@Posts @ModifyPost
Feature: Modificación de Publicaciones
  Como tester de la API de GoRest
  Quiero validar la modificación de publicaciones (PUT /posts/postId, PATCH /posts/postId)
  Para asegurar que los cambios en la publicación se persisten correctamente.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario

  Scenario Outline: CP01-CP02 Intentar modificar completamente una publicación sin enviar token o enviando un tokén inválido
    And que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición para modificar la publicación
    Then la API responde con un código de estado <codigo_estado>
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | codigo_estado   | mensaje_error         |
      | ausente     | 404             | Resource not found    |
      | inválido    | 401             | Invalid token         |

  Scenario Outline: CP03-CP04 Intentar modificar completamente una publicación con datos inválidos
    Given que preparo una publicación con "<title>" y "<body>"
    When realizo una petición para modificar la publicación
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "<campo>" y el mensaje "<mensaje_error>"

    Examples:
      | title | body                | campo  | mensaje_error  |
      |       | Contenido de prueba | title  | can't be blank |
      | Title |                     | body   | can't be blank |

  Scenario: CP05 Intentar modificar completamente una publicación no existente
    Given que utilizo un ID de "publicación" inexistente 999999
    When realizo una petición para modificar la publicación
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP06 Intentar modificar una publicación eliminada previamente
    And realizo una petición para eliminar la publicación
    When realizo una petición para modificar la publicación
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP07 Modificar completamente una publicación con datos válidos
    And que preparo una publicación válida
    When realizo una petición para modificar la publicación
    Then la API responde con un código de estado 200
    And la respuesta contiene el identificador de la publicación
    And la respuesta contiene los datos enviados en la petición al modificar la publicación