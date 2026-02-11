Feature: Gestión de comentarios en la aplicación

  Scenario: CP01 El usuario crea un comentario exitosamente
    Given que el usuario quiere agregar un comentario a un post existente
    And el usuario proporciona la siguiente información:
      | postId | nombre | email             | comentario                        |
      | 1      | Juan   | juan@email.com    | Este es un comentario de prueba   |
    When el usuario envía la solicitud para crear el comentario
    Then el sistema debe registrar el comentario correctamente
    And debe responder con un código 201
    And el comentario registrado debe incluir un ID generado por el sistema
    And los datos enviados deben coincidir con la información registrada

  Scenario: CP02 El usuario consulta los comentarios de un post
    Given que el usuario quiere ver los comentarios asociados a un post específico
    And el post tiene comentarios registrados
    When el usuario solicita ver los comentarios del post con ID 1
    Then el sistema debe devolver la lista de comentarios existentes
    And debe responder con un código 200
    And cada comentario debe incluir un ID, nombre, email válido y contenido
    And el email de cada comentario debe contener "@"
