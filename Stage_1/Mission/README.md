# Mission 1 - Automatización de APISs - The Simpsons

- 📁 **Carpeta:** `Stage_1/Mission/`
- 🎯 **Objetivo:** Poner en práctica la transformación de una historia de usuario y sus criterios de aceptación en casos de prueba claros utilizando la sintaxis de Gherkin para luego poner en práctica la automatizació de los casos de prueba definidos con Rest Assured y Java.
___ 
## Introducción
En esta `Mission`, he puesto en práctica el diseño de casos de prueba utilizando Gherking y la automaticazión de los casos de prueba diseñados con Rest Assured y Java.

### Instrucciones
1. Diseñar los casos de prueba en lenguaje Gherkin que cubran todos los criterios de aceptacion .feature
2. Craer una colección en Postman y validar manualmente que los endpoints respondan correctamente.
3. Crear un nuevo proyecto Maven
4. Implementar los casos en Rest Assured siguiendo buenas prácticas:
   - Validar códigos HTTP.
   - Verificar campos obligatorios en las respuestas.

### Ejecución de las pruebas
1. Descargar el proyecto [Mission1](./Mission1).
2. Abrir la terminal, situarse dentro del directorio del proyecto y ejecutar:
    ```bash
    mvn test
    mvn surefire-report:report
    ```
3. Maven ejecutará todas las pruebas automáticas que haya en el directorio `src/test/java` y subdirectorios.
4. Maven nos mostrará los resultados de la ejecución en la terminal y también podremos consultar el reporte HTML generado en `target\reports\surefire.html`

### Resultados de la ejecución
En el archivo [Reporte de la ejecución](./Reporte%20de%20la%20ejecución.pdf) se pueden visualizar los resultados de la ejecución de las pruebas. Cabe destacar que aunque hay tests fallidos esto se debe a discrepancias entre los criterios de aceptación y la API `The Simpsons`, que en algunos casos no cumple los criterios de aceptación definidos en la `Mission`.

Se puede encontrar el listado de bugs encontrados en el archivo detallado: [Reporte_Bugs.md](Reporte_Bugs.md)
