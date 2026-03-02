@Posts @CreatePost
Feature: Creación de Publicaciones
  Como tester de la API de GoRest
  Quiero validar la creación de publicaciones (POST /posts)
  Para asegurar que solo se registran publicaciones con datos válidos y tokens autorizados.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente

  Scenario Outline: CP01-CP02 Crear una publicación con datos válidos para un usuario existente sin enviar token o enviando un tokén inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    And que preparo una publicación válida
    When realizo una petición para crear la publicación
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | mensaje_error         |
      | ausente     | Authentication failed |
      | inválido    | Invalid token         |

  Scenario Outline: CP03-CP04 Intentar crear una publicación con datos inválidos (Validación de campos) para un usuario existente
    Given que preparo una publicación con "<title>" y "<body>"
    When realizo una petición para crear la publicación
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "<campo>" y el mensaje "<mensaje_error>"

    Examples:
      | title | body                | campo  | mensaje_error  |
      |       | Contenido de prueba | title  | can't be blank |
      | Title |                     | body   | can't be blank |

  Scenario: CP05 Intentar crear una publicación para un usuario que no existe
    And que preparo una publicación para un usuario con ID 999999
    When realizo una petición para crear la publicación
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "user" y el mensaje "must exist"

  Scenario: CP06 Intentar crear una publicación con ID de usuario no numérico
    When envío una petición de creación con el campo "user_id" valor "abc_123"
    Then la API responde con un código de estado 422

  Scenario: CP07 Intentar crear publicación para un usuario recién eliminado
    And realizo una petición para eliminar el usuario
    And la API responde con un código de estado 204
    And que preparo una publicación válida
    When realizo una petición para crear la publicación
    Then la API responde con un código de estado 422

  Scenario: CP05 Crear publicación con datos válidos para un usuario existente
    Given que preparo una publicación válida
    When realizo una petición para crear la publicación
    Then la API responde con un código de estado 201
    And la respuesta contiene el identificador de la publicación
    And la respuesta contiene los datos enviados en la petición al crear la publicación