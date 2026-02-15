Feature: Gestión del catálogo de libros

  Background: Preparación de la sesión y entorno
    Given el usuario tiene un token válido y un userId existente

  Scenario: CP-1 - Consultar catálogo de libros de la tienda
    When realiza una petición GET para consultar los libros disponibles en la tienda
    Then la API debe responder con un código 200
    And la lista de libros no debe estar vacía

  Scenario: CP-2 - Añadir el primer libro disponible a la colección de libros del usuario
    Given que el usuario consulta los libros disponibles en la tienda
    When realiza una petición POST para añadir el primer libro a su colección
    Then la API debe responder con un código 201
    And el libro debe aparecer en la colección del usuario