# Challenge
- 📁 **Carpeta:** `Stage_1/Challenge/`
- 🎯 **Objetivo:** Breve ejercicio de escenarios de complejidad media con criterios claros. Forma parte del **Stage_1** de la mentoría.

___
## Challenge 1 - Testing APIs en Postman 📨
- 📁 **Carpeta:** `Stage_1/Challenge/Challenge1`
- 🎯 **Objetivo:** Poner en práctica la creación de casos de prueba en Gherkin y la realización de pruebas de APIs manuales con Postman.

## Introducción
En este challenge, he diseñado los casos de prueba usando la sintaxis de Gherkin de acuerdo a los criterios de aceptación definidos previamente.
### Instrucciones:
1. Realizar casos de prueba positivos y negativos que cubran todos los criterios de aceptación, usando lenguaje Gherkin en un archivo .feature
2. Crear una colección en postman para la ejecución de todos los casos de prueba
3. Exportar la colección del postman
4. Subir todo en el repositorio entregas

#### Como importar la colección de Postman:
1. Descarga la colección [Notes API.postman_collection.json](https://github.com/daniel-bobes/qax-automation-apis/blob/43a4e61418b13e1d5b0cd77c864030c14b7f2513/Stage_1/Challenge/Challenge1/Notes%20API.postman_collection.json) desde GitHub.
2. Abre Postman en tu máquina o en el cliente web.
3. Haz clic en Import (arriba a la izquierda).
4. Selecciona Upload Files y carga la colección descargada en el paso 1.
5. Verifica que la colección Notes API aparece en tu panel izquierdo.
6. Ejecuta al menos una petición de la colección para confirmar que la importación fue exitosa.

---
## Challenge 2 - Automatizando mis primeras APIs con Rest Assured y Java ☕
- 📁 **Carpeta:** `Stage_1/Challenge/Challenge2`
- 🎯 **Objetivo:** Poner en práctica la automatización de pruebas de APIs con Rest Assured. Convirtiendo pruebas manuales en pruebas automatizadas, validando tanto el código de estado como el contenido de la respuesta de la API.

## Introducción
En este challenge, he automatizado los casos de prueba definidos previamente en el [ejercicio 2](https://github.com/Training-Ninja-For-Testing/qax-automation-apis-rest-assured/blob/e4762f35ad515934929f7b7802b5227c359bcb0a/Assets/01_Stage_1/02_Training/01_Exercise_1/Test_Cases.feature) y los casos de prueba que diseñe en el [Challenge 1](./Challenge1/Notes_API.feature) con Rest Assured en Java.

### Instrucciones
1. Crear un proyecto Maven con Java agregando las dependencias de Rest Assured y JUnit en el `pom.xml`.
2. Completar la automatización de los casos de prueba definidos previamente en el archivo de casos de prueba del ejercicio 2.
3. Crear un archivo nuevo de test
   * Define una clase de test, por ejemplo NotesApiTests.java.
   * Automatizar los casos de prueba diseñados en el [Challenge 1](./Challenge1/Notes_API.feature).

### Ejecución de las pruebas
1. Descargar el proyecto [Challenge2](./Challenge2).
2. Abrir la terminal, situarse dentro del directorio del proyecto y ejecutar:
    ```bash
    mvn test
    mvn surefire-report:report
    ```
3. Maven ejecutará todas las pruebas automáticas que haya en el directorio `src/test/java` y subdirectorios.
4. Maven nos mostrará los resultados de la ejecución en la terminal y también podremos consultar el reporte HTML generado en `target\reports\surefire.html`.