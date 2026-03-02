@Users @ModifyUser
Feature: Modificación de Usuarios
  Como tester de la API de GoRest
  Quiero validar la modificación de usuarios (PUT /users/userId, PATCH /users/userId)
  Para asegurar que los cambios en el perfil de usuario se persisten correctamente.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente

  Scenario Outline: CP01-02 Intentar modificar completamente un usuario sin enviar token o enviando un tokén inválido
    And que uso un tipo de autenticación "<tipo_token>"
    When realizo una petición para modificar el usuario
    Then la API responde con un código de estado <codigo_estado>
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | codigo_estado   | mensaje_error         |
      | ausente     | 404             | Resource not found    |
      | inválido    | 401             | Invalid token         |

  Scenario Outline: CP03-CP09 Intentar modificar completamente un usuario con datos inválidos
    Given que preparo un usuario con "<nombre>", "<email>", "<genero>" y "<estado>"
    When realizo una petición para modificar el usuario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "<campo>" y el mensaje "<mensaje_error>"

    Examples:
      | nombre  | email             | genero              | estado   | campo  | mensaje_error                          |
      |         | test@qaxpert.com  | male                | active   | name   | can't be blank                         |
      | Test    |                   | female              | inactive | email  | can't be blank                         |
      | Test    | test@qaxpert.com  |                     | active   | gender | can't be blank, can be male of female  |
      | Test    | test@qaxpert.com  | male                |          | status | can't be blank                         |
      | Test    | email_sin_formato | female              | active   | email  | is invalid                             |
      | Test    | test@test.com     | prefiere no decirlo | inactive | gender | can't be blank, can be male of female  |
      | Test    | test@test.com     | male                | ausente  | status | can't be blank                         |

  Scenario: CP10 Intentar modificar completamente un usuario no existente
    Given que utilizo un ID de "usuario" inexistente 999999
    When realizo una petición para modificar el usuario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP11 Intentar modificar un usuario completamente con un email ya registrado para otro usuario
    And que obtengo un email de otro usuario ya existente
    When realizo una petición para modificar el usuario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "email" y el mensaje "has already been taken"

  Scenario: CP12 Intentar modificar un usuario eliminado previamente
    And realizo una petición para eliminar el usuario
    When realizo una petición para modificar el usuario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP13 Modificar completamente un usuario con datos válidos
    And que preparo un usuario válido
    When realizo una petición para modificar el usuario
    Then la API responde con un código de estado 200
    And la respuesta contiene un identificador de usuario
    And la respuesta contiene los datos enviados en la petición