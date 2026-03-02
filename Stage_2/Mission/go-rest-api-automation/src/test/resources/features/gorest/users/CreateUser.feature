@Users @CreateUser
Feature: Creación de Usuarios
  Como tester de la API de GoRest
  Quiero validar la creación de usuarios (POST /users/userId)
  Para asegurar que solo se registran usuarios con datos válidos y tokens autorizados.

  Scenario Outline: CP01-CP02 Intentar crear usuario con datos válidos sin enviar token o enviando un token inválido
    Given que uso un tipo de autenticación "<tipo_token>"
    And que preparo un usuario válido
    When realizo una petición para crear un usuario
    Then la API responde con un código de estado 401
    And la respuesta contiene el mensaje "<mensaje_error>"

    Examples:
      | tipo_token  | mensaje_error         |
      | ausente     | Authentication failed |
      | inválido    | Invalid token         |

  Scenario Outline: CP03-CP09 Intentar crear un usuario con datos inválidos
    Given que preparo un usuario con "<nombre>", "<email>", "<genero>" y "<estado>"
    When realizo una petición para crear un usuario
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

  Scenario: CP10 Intentar crear un usuario con un email ya registrado
    Given que preparo un usuario con el mismo email que el anterior
    When realizo una petición para crear un usuario
    Then la API responde con un código de estado 422
    And la respuesta de error contiene el campo "email" y el mensaje "has already been taken"

  Scenario: CP11 Crear usuario con datos válidos
    And que preparo un usuario válido
    When realizo una petición para crear un usuario
    Then la API responde con un código de estado 201
    And la respuesta contiene un identificador de usuario
    And la respuesta contiene los datos enviados en la petición