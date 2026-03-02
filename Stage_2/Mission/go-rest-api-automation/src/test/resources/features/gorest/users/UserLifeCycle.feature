@Users @FullCycle
Feature: Gestión del Ciclo de Vida del Usuario
  Como tester de la API de GoRest
  Quiero ejecutar flujos de prueba de principio a fin (End-to-End)
  Para asegurar la integridad referencial desde que un usuario nace hasta que se elimina.

  Scenario: CP01 Crear usuario con datos válidos
    And que preparo un usuario válido
    When realizo una petición para crear un usuario
    Then la API responde con un código de estado 201
    And la respuesta contiene un identificador de usuario
    And la respuesta contiene los datos enviados en la petición

  Scenario: CP02 Consultar usuario existente
    Given que tengo un usuario creado anteriormente
    When realizo una petición para consultar el usuario
    Then la API responde con un código de estado 200
    And la respuesta contiene un identificador de usuario

  Scenario: CP03 Modificar completamente un usuario con datos válidos
    Given que tengo un usuario creado anteriormente
    And que preparo un usuario válido
    When realizo una petición para modificar el usuario
    Then la API responde con un código de estado 200
    And la respuesta contiene un identificador de usuario
    And la respuesta contiene los datos enviados en la petición

  Scenario: CP04 Eliminar usuario existente
    Given que tengo un usuario creado anteriormente
    When realizo una petición para eliminar el usuario
    Then la API responde con un código de estado 204

  Scenario: CP05 Verificar que el usuario eliminado ya no existe
    Given que tengo un usuario creado anteriormente
    And realizo una petición para eliminar el usuario
    When realizo una petición para consultar el usuario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP06 Intentar eliminar usuario eliminado previamente
    Given que tengo un usuario creado anteriormente
    And realizo una petición para eliminar el usuario
    When realizo una petición para eliminar el usuario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"