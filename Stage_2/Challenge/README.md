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
---

# Reto 2 - Escalando mi proyecto de automatización
- 📁 **Carpeta:** `Stage_2/Challenge/bookstore-api-automation`
- 🎯 **Objetivo:** Aprender a automatizar flujos con autenticación basada en tokens y a gestionar el contexto entre escenarios de prueba.
___
## Introducción
En este reto he aprendido a:
* Automatizar flujos de pruebas con autenticación basada en tokens.
* Generar datos dinámicos en las peticiones.
* Gestionar datos compartidos entre diferentes steps y escenarios de prueba..

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
    cd Stage_2/Challenge/bookstore-api-automation
    ```
4. Ejecuta el siguiente comando para ejecutar todas las suites de pruebas:
    ```bash
    mvn test
    ```
5. Cucumber habrá generado el reporte en el directorio `target/cucumber-reports`.
6. Accede al directorio anterior y abre el archivo `main-report.html` en tu navegador.

___

## 📊️ Evidencias
Puedes encontrar el reporte de la ejecución exitosa en la carpeta: 📁[ Ver Carpeta de Evidencias](./evidencias/challenge2)

- Reporte HTML con la ejecución de todos los escenarios de prueba: [BookStore Cucumber Main Report](./evidencias/challenge2/BookStore%20Cucumber%20Main%20Report.pdf)

## 🗒️ Historia de usuario: Registro y acceso seguro a la Book Store API
Como usuario/automatizador de pruebas, <br>
Quiero poder registrar un usuario, obtener un token de autenticación y consumir endpoints protegidos con ese token, <br>
Para verificar que la API maneja correctamente el alta de usuarios, la emisión de tokens y el acceso autorizado a información sensible.


## ✅ Escenarios de prueba
### `Users.feature`
```gherkin
@NewContext
Feature: Creación de usuario, generación de token y validación de información

   Scenario: CP-1 — Crear usuario exitosamente
      Given el usuario prepara un request body válido para crear usuario
      When realizo una petición para crear el usuario
      Then la API debe responder con un código 201
      And la respuesta contiene un userId

   Scenario: CP-2 — Generar token válido
      Given el usuario tiene credenciales válidas
      When realiza una petición para generar el token
      Then la API debe responder con un código 200
      And la respuesta contiene un token

   Scenario: CP-3 — Obtener info del usuario con token válido
      Given el usuario tiene un token válido y un userId existente
      When realiza una petición GET a obtener información del usuario
      Then la API debe responder con un código 200
      And el userId corresponde al usuario creado

   Scenario Outline: CP-4 — Crear usuario con datos inválidos
      Given que tengo un usuario "<usuario>" y contraseña "<password>" inválidos
      When realizo una petición para crear el usuario
      Then la API debe responder con un código <status_code>
      And el cuerpo debe contener el mensaje de error "<error_esperado>"

      Examples:
         | motivo                  | usuario   | password  | status_code | error_esperado                                  |
         | Usuario vacío           |           | 12345Az@  | 400         | UserName and Password required.                 |
         | Password vacío          | user1234  |           | 400         | UserName and Password required.                 |
         | Password sin mayúsculas | user1234  | 12345az@  | 400         | Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.                  |
         | Password sin minúsculas | user1234  | 12345AZ@  | 400         | Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.                  |
         | Password sin simbolos   | user1234  | 12345Az6  | 400         | Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.                  |
         | Password sin números    | user1234  | AAZZ@Az@  | 400         | Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.                  |
         | Password corta          | user1234  | 1234Az@   | 400         | Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer.                  |

   Scenario: CP-5 — Generar token fallido por credenciales inválidas
      Given que utilizo credenciales de acceso incorrectas
      When realiza una petición para generar el token
      Then la API debe responder con un código 200
      And el campo result debe mostrar el mensaje "User authorization failed."
      And el campo status de la respuesta debe ser "Failed"
      And el token no debe estar presente en la respuesta

   Scenario Outline: CP-6 — Acceso no autorizado a recursos protegidos
      Given que el usuario prepara una petición "<tipo_token>"
      When realiza una petición GET a obtener información del usuario
      Then la API debe responder con un código 401
      And el cuerpo debe contener el código de error "1200"
      And el cuerpo debe contener el mensaje de error "User not authorized!"

      Examples:
         | tipo_token            | comentario                       |
         | sin token             | No se envía el header de Auth    |
         | con token inválido    | Se envía un Bearer token corrupto|
```

### `Books.feature`
```gherkin
@NewContext
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
```
---

