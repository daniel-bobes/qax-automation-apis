@Posts @GetPost
Feature: Consultar de Publicaciones
  Como tester de la API de GoRest
  Quiero validar la consulta de publicaciones (GET /posts/postId)
  Para asegurar que la información recuperada coincide con el recurso solicitado.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario

  Scenario Outline: CP01-02 Intentar consultar una publicación sin enviar token o enviando un token inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición para consultar la publicación
    Then la API responde con un código de estado <codigo_estado>
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | codigo_estado   | mensaje_error         |
      | ausente     | 404             | Resource not found    |
      | inválido    | 401             | Invalid token         |

  Scenario: CP04 Intentar consultar una publicación no existente
    Given que utilizo un ID de "publicación" inexistente 999999
    When realizo una petición para consultar la publicación
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP05 Intentar consultar publicación eliminada previamente
    Given realizo una petición para eliminar la publicación
    When realizo una petición para consultar la publicación
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP06 Consultar publicación existente de un usuario
    When realizo una petición para consultar la publicación
    Then la API responde con un código de estado 200
    And la respuesta contiene el identificador de la publicación

