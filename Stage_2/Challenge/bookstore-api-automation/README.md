# BookStore API Automation 🚀

Framework de automatización de pruebas para la API de **[BookStore API](https://demoqa.com/swagger/)**, desarrollado con un enfoque profesional y mantenible.

## 🛠️ Tecnologías Usadas
* **Java 11+** ☕: lenguaje de programación principal utilizado para el desarrollo del framework de automatización.
* **REST Assured** 🧪: Biblioteca de Java de código abierto utilizada para simplificar las pruebas automatizadas y la validación de servicios web RESTFul.
* **JUnit 5** ✅: Motor de ejecución de pruebas.
* **Cucumber**: herramienta que permite escribir pruebas automatizadas en un lenguaje sencillo llamado Gherkin, facilitando su comprensión por cualquier persona.
* **Maven** 📦: Gestor de dependencias y automatización de la construcción del proyecto.

## 📂 Estructura del Proyecto
El framework sigue una organización modular basada en Maven para separar la lógica de las pruebas de la configuración.

```text
jsonplaceholder-api-automation
├── src
│   └── test
│       ├── java
│       │   └── com.danielbobes.api.challenges.bookStore
│       │       └── config                              # URLs y configuración y de Endpoints
│       │       └── models                              # POJOs para el mapeo de peticiones y respuestas de la API
│       │       └── runners                             # Orquestadores de la ejecución de pruebas con JUnit 5 y Cucumber
│       │       └── steps                               # Implementación de la lógica de los pasos definidos en Gherkin
│       │       └── utils                               # Implementación de utilidades
│       └── resources
│           └── features                                # Escenarios de prueba en Gherkin 🥒
│               └── bookStore
│                   └── accounts
│                   └── books                  
├── pom.xml                                             # Configuración y dependencias 📦
└── README.md                                           # Documentación del proyecto
```

## 🚀 Instalación
1. Clona este repositorio.
2. Abre el proyecto en tu IDE favorito como un **Proyecto Maven**.
3. Asegurarte de tener configurado el **JDK 11**.

## 🧪 Ejecución
Para ejecutar las pruebas desde la terminal, primero sitúate en la carpeta raíz del proyecto.

Después, si quieres ejecutar todas las suites de pruebas ejecuta en la terminal:
```bash
mvn clean test
mvn surefire-report:report
```
Por el contrario, si deseas ejecutar una suite de pruebas concreta:
* `mvn test -Dtest=<nombre de la suite>`

Por ejemplo:
```bash
mvn test -Dtest=MainRunner
```

## 📊 Reportes
Trás la ejecución, se generan reportes detallados en la ruta `target/cucumber-reports`.

Para visualizar el reporte solo tienes que abrir el reporte `main-report.html` en tu navegador favorito 🌐.