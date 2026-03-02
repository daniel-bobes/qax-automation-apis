@Comments @CreateComment
Feature: Creación de Comentarios
  Como tester de la API de GoRest
  Quiero validar la creación de comentarios (POST /comments)
  Para asegurar que solo se registran comentarios con datos válidos y tokens autorizados.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario

  Scenario Outline: CP01-CP02 Intentar crear un comentario con datos válidos para un usuario existente sin enviar token o enviando un token inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    And que preparo un comentario válido
    When realizo una petición para crear el comentario
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | mensaje_error         |
      | ausente     | Authentication failed |
      | inválido    | Invalid token         |

  Scenario Outline: CP03-CP06 Intentar crear un comentario con datos inválidos (Validación de campos) para una publicación existente
    Given que preparo un comentario con "<name>", "<email>" y "<body>"
    When realizo una petición para crear el comentario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "<campo>" y el mensaje "<mensaje_error>"

    Examples:
      | name  | email         | body                | campo   | mensaje_error   |
      |       | test@test.com | Contenido de prueba | name    | can't be blank  |
      | Name  |               | Contenido de prueba | email   | can't be blank  |
      | Name  | test@test.com |                     | body    | can't be blank  |
      | Name  | test_test.com | Contenido de prueba | email    | is invalid     |

  Scenario: CP05 Intentar crear un comentario para una publicación no existente
    And que preparo un comentario para una publicación con ID 999999
    When realizo una petición para crear el comentario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "post" y el mensaje "must exist"

  Scenario: CP06 Intentar crear un comentario con ID de publicación no existente y de tipo no numérico
    When realizo una petición para crear el comentario con el campo "post_id" informado con el valor "abc_123"
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "post" y el mensaje "must exist"
    And la respuesta de error contiene el campo "post_id" y el mensaje "is not a number"

  Scenario: CP07 Intentar crear un comentario para una publicación eliminada previamente
    And realizo una petición para eliminar la publicación
    And la API responde con un código de estado 204
    And que preparo un comentario válido
    When realizo una petición para crear el comentario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "post" y el mensaje "must exist"

  Scenario: CP08 Crear comentario con datos válidos para una publicación existente
    Given que preparo un comentario válido
    When realizo una petición para crear el comentario
    Then la API responde con un código de estado 201
    And la respuesta contiene el identificador del comentario
    And la respuesta contiene los datos enviados en la petición al crear el comentario