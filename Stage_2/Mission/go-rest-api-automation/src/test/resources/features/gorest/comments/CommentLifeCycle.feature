@Comments @FullCycle
Feature: Gestión del Ciclo de Vida de Comentarios
  Como tester de la API de GoRest
  Quiero ejecutar flujos de prueba de principio a fin (End-to-End)
  Para asegurar la integridad referencial desde que un comentario nace hasta que se elimina.

  Background: Creación de precondiciones
    Given que tengo un usuario creado anteriormente
    And que tengo una publicación creada para ese usuario

  Scenario: CP01 Crear comentario con datos válidos para una publicación existente
    Given que preparo un comentario válido
    When realizo una petición para crear el comentario
    Then la API responde con un código de estado 201
    And la respuesta contiene el identificador del comentario
    And la respuesta contiene los datos enviados en la petición al crear el comentario

  Scenario: CP02 Consultar comentario existente de una publicación
    Given que tengo un comentario creado para esa publicación
    When realizo una petición para consultar el comentario
    Then la API responde con un código de estado 200
    And la respuesta contiene el identificador del comentario
    And la respuesta contiene los datos enviados en la petición al crear el comentario

  Scenario: CP03 Modificar completamente un comentario con datos válidos
    Given que tengo un comentario creado para esa publicación
    When realizo una petición para modificar el comentario
    Then la API responde con un código de estado 200
    And la respuesta contiene el identificador de la publicación
    And la respuesta contiene los datos enviados en la petición al modificar el comentario

  Scenario: CP04 Eliminar comentario existente de una publicación
    Given que tengo un comentario creado para esa publicación
    When realizo una petición para eliminar el comentario
    Then la API responde con un código de estado 204

  Scenario: CP05 Verificar que el comentario eliminado ya no existe
    Given que tengo un comentario creado para esa publicación
    And realizo una petición para eliminar el comentario
    When realizo una petición para consultar el comentario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"

  Scenario: CP06 Intentar eliminar comentario eliminado previamente
    Given que tengo un comentario creado para esa publicación
    And realizo una petición para eliminar el comentario
    When realizo una petición para eliminar el comentario
    Then la API responde con un código de estado 404
    And la respuesta contiene el mensaje "Resource not found"