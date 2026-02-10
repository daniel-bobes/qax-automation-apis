# Simpsons API Automation 🍩

Framework de automatización de pruebas para la API de **[The Simpsons](https://thesimpsonsapi.com/)**, desarrollado con un enfoque profesional y mantenible.
Este framework es más eficiente que Homer en un buffet libre 🍩

## 🛠️ Tecnologías Usadas
* **Java 11** ☕: lenguaje de programación principal utilizado para el desarrollo del framework de automatización.
* **REST Assured** 🧪: biblioteca de Java de código abierto utilizada para simplificar las pruebas automatizadas y la validación de servicios web RESTFul.
* **JUnit 5** ✅: framework de Java utilizado para simplificar las pruebas unitarias.
* **Maven** 📦: Gestor de dependencias y automatización de la construcción del proyecto.

## 📂 Estructura del Proyecto
El framework sigue una organización modular basada en Maven para separar la lógica de las pruebas de la configuración.

```text
simpsons-api-automation
├── src
│   └── test
│       ├── java
│       │   └── com.danielbobes.api.test
│       │       └── TheSimpsonsApiTests.java            # Clase principal de pruebas ☕
│       └── resources
│           └── features
│               └── TheSimpsons_Test_Cases.feature      # Escenarios en Gherkin 🥒
├── pom.xml                                             # Configuración y dependencias 📦
└── README.md                                           # Documentación del proyecto 🍩
```

## 🚀 Instalación
1. Clona este repositorio.
2. Abre el proyecto en tu IDE favorito como un **Proyecto Maven**.
3. Asegurarte de tener configurado el **JDK 11**.

## 🧪 Ejecución
Para ejecutar las pruebas desde la terminal, sitúate en la carpeta raíz del proyecto y ejecuta los siguientes comandos:
    
```bash
mvn clean test
mvn surefire-report:report
```

Podrás visualizar el reporte de la ejecución, situándote en el directorio `target/reports` y abriendo el archivo `surefire.html` en tu navegador favorito 🌐.