# Challenge
- 📁 **Carpeta:** `Stage_1/Challenge/`
- 🎯 **Objetivo:** Breve ejercicio de escenarios de complejidad media con criterios claros. Forma parte del **Stage_1** de la mentoría.

___
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
4. Maven nos mostrará los resultados de la ejecución en la terminal y también podremos consultar el reporte HTML generado en `target\reports\surefire.html`