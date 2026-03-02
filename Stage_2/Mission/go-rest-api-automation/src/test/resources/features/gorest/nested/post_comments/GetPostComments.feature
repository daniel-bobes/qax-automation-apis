@Comments @GetPostComments
Feature: Consulta de Comentarios de una Publicación
  Como tester de la API de GoRest
  Quiero validar la consulta de comentarios de una publicación (GET /posts/postId/comments)
  Para asegurar que la información recuperada coincide con el recurso solicitado.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario

  Scenario: CP01 Consultar lista de comentarios de una publicación sin enviar token
    Given que tengo un comentario creado para esa publicación
    And que uso un tipo de autenticación "ausente"
    When realizo una petición para consultar los comentarios de la publicación
    Then la API responde con un código de estado 200
    And la lista de comentarios esta vacía

  Scenario: CP02 Consultar lista de comentarios con token no válido
    Given que tengo un comentario creado para esa publicación
    And que uso un tipo de autenticación "inválido"
    When realizo una petición para consultar los comentarios de la publicación
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "Invalid token"

  Scenario: CP03 Consultar comentarios de una publicación recien creada
    When realizo una petición para consultar los comentarios de la publicación
    Then la API responde con un código de estado 200
    And la lista de comentarios esta vacía
    
  Scenario: CP04 Consultar comentarios de una publicación inexistente
    Given que utilizo un ID de "publicación" inexistente 999999
    When realizo una petición para consultar los comentarios de la publicación
    Then la API responde con un código de estado 200
    And la lista de comentarios esta vacía

  Scenario: CP05 Consultar comentarios de una publicación eliminada previamente
    Given realizo una petición para eliminar la publicación
    When realizo una petición para consultar los comentarios de la publicación
    Then la API responde con un código de estado 200
    And la lista de comentarios esta vacía

  Scenario: CP06 Consultar comentarios de una publicación crear con un comentario
    Given que tengo un comentario creado para esa publicación
    When realizo una petición para consultar los comentarios de la publicación
    Then la API responde con un código de estado 200
    And la lista de comentarios no esta vacía
    And todos los comentarios tienen los campos id, post_id, name, email y body