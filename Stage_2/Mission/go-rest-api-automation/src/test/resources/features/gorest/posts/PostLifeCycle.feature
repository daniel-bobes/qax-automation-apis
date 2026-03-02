@Posts @FullCycle
Feature: Gestión del Ciclo de Vida de Publicaciones
  Como tester de la API de GoRest
  Quiero ejecutar flujos de prueba de principio a fin (End-to-End)
  Para asegurar la integridad referencial desde que una publicación nace hasta que se elimina.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente

  Scenario: CP01 Crear publicación con datos válidos para un usuario existente
    Given que preparo una publicación válida
    When realizo una petición para crear la publicación
    Then la API responde con un código de estado 201
    And la respuesta contiene el identificador de la publicación
    And la respuesta contiene los datos enviados en la petición al crear la publicación

  Scenario: CP02 Consultar publicación existente de un usuario
    Given que preparo una publicación válida
    And realizo una petición para crear la publicación
    When realizo una petición para consultar la publicación
    Then la API responde con un código de estado 200
    And la respuesta contiene el identificador de la publicación

  Scenario: CP03 Modificar completamente una publicación con datos válidos
    Given que preparo una publicación válida
    And realizo una petición para crear la publicación
    When realizo una petición para modificar la publicación
    Then la API responde con un código de estado 200
    And la respuesta contiene el identificador de la publicación
    And la respuesta contiene los datos enviados en la petición al modificar la publicación

  Scenario: CP04 Eliminar publicación existente de un usuario
    Given que preparo una publicación válida
    And realizo una petición para crear la publicación
    When realizo una petición para eliminar la publicación
    Then la API responde con un código de estado 204

  Scenario: CP05 Verificar que la publicación eliminada ya no existe
    Given que tengo una publicación creada para ese usuario
    And realizo una petición para eliminar la publicación
    When realizo una petición para consultar la publicación
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP06 Intentar eliminar publicación eliminada previamente
    Given que tengo una publicación creada para ese usuario
    And realizo una petición para eliminar la publicación
    When realizo una petición para eliminar la publicación
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"