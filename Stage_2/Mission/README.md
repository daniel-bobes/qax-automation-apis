# 🚀 Misión: Proyecto de Automatización de APIs con Token – Go Rest API
- 📁 **Carpeta:** `Stage_2/Mission/go-rest-api-automation`
___
# Introducción
En esta misión, he aprendido a crear un proyecto de automatización para testing de APIs robusto.

Los pilares de mi implementación han sido:
* Arquitectura modular: organización del proyecto para separar la lógica de las pruebas de la configuración.
* Manejo de modelos (POJO): implementación de objetos Java para la serialización y deserialización de las peticiones y respuestas de la API.
* BDD con Cucumber: implementación de los escenarios de prueba en Gherkin, permitiendo que las pruebas sean legibles y estén alineadas con los requisitos.
* Gestión del contexto: gestión del contexto entre pasos de un escenario de prueba.
* Gestión de datos dinámicos: uso de JavaFaker para la generación de datos para las pruebas.
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
    cd Stage_2/Mission/go-rest-api-automation
    ```
4. Ejecuta el siguiente comando para ejecutar todas las suites de pruebas:
    ```bash
    mvn test
    ```
5. Cucumber habrá generado el reporte en el directorio `target/cucumber-reports`.
6. Accede al directorio anterior y abre el archivo `main-report.html` en tu navegador.

## 📊️ Evidencias
Puedes encontrar el reporte de la ejecución exitosa en la carpeta: 📁[ Ver Carpeta de Evidencias](./evidencias/)

- Reporte HTML con la ejecución de todos los escenarios de prueba: [Go Rest Api Cucumber Main Report](./evidencias/NombreReporte.pdf)

## 🗒️ Historia de usuario:  Flujos de Prueba API Go Rest
**Como** tester de APIs<br>
**Quiero** automatizar y validar la creación de usuarios, publicaciones y comentarios, <br>
**Para** que pueda asegurarme de que la API funciona correctamente y cumple los contratos esperados.

## ✅ Escenarios de prueba
* 📁[Usuarios](./go-rest-api-automation/src/test/resources/features/gorest/users)
* 📁[Publicaciones](./go-rest-api-automation/src/test/resources/features/gorest/posts)
* 📁[Comentarios](./go-rest-api-automation/src/test/resources/features/gorest/comments)
