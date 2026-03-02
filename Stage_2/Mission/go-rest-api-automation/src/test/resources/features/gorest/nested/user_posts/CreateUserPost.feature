@Posts @CreateUserPost
Feature: Creación de Publicaciones de un Usuario
  Como tester de la API de GoRest
  Quiero validar la creación de publicaciones de un usuario (POST /users/userId/posts)
  Para asegurar que solo se registran publicaciones con datos válidos y tokens autorizados.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente

  Scenario Outline: CP01-CP02 Crear una publicación con datos válidos para un usuario existente sin enviar token o enviando un tokén inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    And que preparo una publicación válida
    When realizo una petición para crear una publicación por el usuario
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | mensaje_error         |
      | ausente     | Authentication failed |
      | inválido    | Invalid token         |
    
  Scenario: CP03 Crear publicación para un usuario inexistente
    Given que utilizo un ID de "usuario" inexistente 999999
    And que preparo una publicación válida
    When realizo una petición para crear una publicación por el usuario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "user" y el mensaje "must exist"

  Scenario: CP04 Crear publicación para un usuario eliminado previamente
    Given realizo una petición para eliminar el usuario
    And que preparo una publicación válida
    When realizo una petición para crear una publicación por el usuario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "user" y el mensaje "must exist"

  Scenario: CP05 Crear publicación para un usuario
    Given que preparo una publicación válida
    When realizo una petición para crear una publicación por el usuario
    Then la API responde con un código de estado 201
    And la respuesta contiene el identificador de la publicación
    And la respuesta contiene los datos enviados en la petición al crear la publicación