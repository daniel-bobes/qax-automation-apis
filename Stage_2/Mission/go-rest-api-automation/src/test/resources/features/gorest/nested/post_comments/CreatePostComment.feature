@Comments @CreatePostComment
Feature: Creación de Comentario en una Publicación
  Como tester de la API de GoRest
  Quiero validar la creación de comentarios en una publicación (POST /posts/postId/comments)
  Para asegurar que solo se registran comentarios con datos válidos y tokens autorizados.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario

  Scenario Outline: CP01-CP02 Crear un comentario con datos válidos en una publicación existente sin enviar token o enviando un tokén inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    And que preparo un comentario válido
    When realizo una petición para crear un comentario en la publicación
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | mensaje_error         |
      | ausente     | Authentication failed |
      | inválido    | Invalid token         |
    
  Scenario: CP03 Crear comentario para una publicación inexistente
    Given que utilizo un ID de "publicación" inexistente 999999
    And que preparo un comentario válido
    When realizo una petición para crear un comentario en la publicación
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "post" y el mensaje "must exist"

  Scenario: CP04 Crear comentario para una publicación eliminada previamente
    Given realizo una petición para eliminar la publicación
    And que preparo un comentario válido
    When realizo una petición para crear un comentario en la publicación
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "post" y el mensaje "must exist"

  Scenario: CP05 Crear comentario para una publicación existente
    Given que preparo un comentario válido
    When realizo una petición para crear un comentario en la publicación
    Then la API responde con un código de estado 201
    And la respuesta contiene el identificador del comentario
    And la respuesta contiene los datos enviados en la petición al crear el comentario