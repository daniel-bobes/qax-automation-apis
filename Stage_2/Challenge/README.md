# Reto 1 - Escalando mi proyecto de automatización
- 📁 **Carpeta:** `Stage_2/Challenge/jsonplaceholder-api-automation`
- 🎯 **Objetivo:** Evolucionar de scripts de automatización simples hacia un framework profesional, modular y escalable.
___
## Introducción
En este reto, he aprendido a crear un proyecto de automatización para testing de APIs robusto. 

Los pilares de mi implementación han sido:
- Arquitectura modular: organización del proyecto siguiendo una clara separación de responsabilidades (configuración, modelos de datos, runners, lógica de los steps de los escenarios en Gherkin y utilidades).
- Manejo de modelos (POJO): implementación de objetos Java para la serialización y deserialización de las peticiones y respuestas de la API.
- BDD con Cucumber: implementación de la lógica de negocio en pasos de Gherkin, permitiendo que las pruebas sean legibles y estén alineadas con los requisitos.
- Gestión de datos dinámicos: uso de DataTables para enviar información estructurada desde los archivos `.feature`, optimizando la reutilización de código.
___
## ⚙️ Requisitos
Para poder ejecutar el proyecto necesitas:
* Java 11 instalado.
* Maven instalado.
* Una terminal de comandos o tu IDE de preferencia.

## 🚀 Ejecución y reportes
1. Clona o descarga el repositorio `https://github.com/daniel-bobes/qax-automation-apis.git`.
2. Abre la terminal en la carpeta raíz `qax-automation-apis`.
3. Sitúate en el directorio del proyecto con el comando:
    ```bash
    cd Stage_2/Challenge/jsonplaceholder-api-automation
    ```
4. Ejecuta el siguiente comando para ejecutar todas las suites de pruebas:
    ```bash
    mvn test
    ```
5. Cucumber habrá generado el reporte en el directorio `target/cucumber-reports`.
6. Accede al directorio anterior y abre el archivo `main-report.html` en tu navegador.

## 📊️ Evidencias
Puedes encontrar el reporte de la ejecución exitosa en la carpeta: 📁[ Ver Carpeta de Evidencias](./evidencias/challenge1)

- Reporte HTML con la ejecución de todos los escenarios de prueba: [JSONPlaceholder Cucumber Main Report](./evidencias/challenge1/JSONPlaceholder%20Cucumber%20Main%20Report.pdf)

## 🗒️ Historia de usuario: Gestión de Publicaciones y Comentarios
**Como** tester de APIs<br>
**Quiero** poder crear posts, agregar comentarios en un post y consultar los comentarios de un post<br>
**Para** validar que la API funcione correctamente y que los datos sean consistentes.

## ✅ Escenarios de prueba
### `CreatePost.feature`
```gherkin
Feature: Crear Post en JSONPlaceholder
  Como tester de APIs
  Quiero crear un post con Rest Assured
  Para validar que el servicio devuelve 201 y refleja los datos enviados

  @create_post
  Scenario: Crear un post exitosamente
    Given que preparo un payload de post válido para el userId 10
    When ejecuto envio la petición
    Then la respuesta debe ser 201 Created
    And el cuerpo debe reflejar title, body y userId del request
    And la respuesta debe incluir un id asignado
```

### `Comments.feature`
```gherkin
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
```