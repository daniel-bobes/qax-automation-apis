Feature: Gestión de registro y login de usuarios
  Como tester de APIS
  Quiero registrar usuarios y loguearme con los usuarios registrados
  Para validar que la API funciona correctamente y que los datos son consistentes

 Background:
    Given que la API está disponible en "https://practice.expandtesting.com/notes/api"

  # -----------------------------
  # Registrar usuarios (POST)
  # -----------------------------
  Scenario: Registrar un usuario correctamente
    Given que tengo la información de un usuario nuevo
      | name		| email 			| password    |
      | prueba	    | prueba@test.com   | prueba123   |
    When realizo una petición POST al endpoint "/users/register"
    Then la respuesta debe devolver un código de respuesta 201
    And debe mostrar un mensaje "User account created successfully"
    And debe asignar un id único al nuevo usuario
    And debe contener los siguientes datos enviados
      | name		| email 			|
      | prueba	    | prueba@test.com   |

  # -----------------------------
  # Login usuarios (POST)
  # -----------------------------
  Scenario: Login de usuario correctamente
    Given que tengo la información de autenticación de un usuario ya registrado
      | email 			| password  |
      | prueba@test.com   | prueba123 |
    When realizo una petición POST al endpoint "/users/login"
    Then la respuesta debe devolver un código de respuesta 200
    And debe mostrar un mensaje "Login successful"
    And debe contener los siguientes campos:
      | name    |
      | email   |
    And debe generar un token de autenticación


  # -----------------------------
  # Escenarios negativos
  # -----------------------------

  Scenario: Registrar un usuario ya registrado
    Given que tengo la información para crear un usuario nuevo y el email de un usuario ya registrado
      | name		| email 			| password    |
      | prueba	    | prueba@test.com   | prueba123   |
    When realizo una petición POST al endpoint "/users/register"
    Then la respuesta debe devolver un código de respuesta 409
    And debe mostrar un mensaje "An account already exists with the same email address"
    And debe contener los siguientes campos:
      | success	  |
      | status    |
      | message   |

  Scenario: Login de usuario incorrecto
    Given que tengo el email de un usuario ya registrado y una contraseña incorrecta para el usuario
      | email 			  | password  |
      | prueba@test.com   | 123prueba |
    When realizo una petición POST al endpoint "/users/login"
    Then la respuesta debe devolver un código de respuesta 401
    And debe mostrar un mensaje "Incorrect email address or password"
    And debe contener los siguientes campos:
      | success	  |
      | status    |
      | message   |