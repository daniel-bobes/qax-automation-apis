# 📋 Reporte de Defectos (Bugs) - API The Simpsons
___
## BUG 1 - Fallo en la validación del parámetro indicador de página
- **ID:** BUG-001
- **Descripción del error:** Al consultar la lista de personajes indicando el parámetro `page` con un valor no entero o entero menor a 1 comprobamos que la API no realiza ninguna validación.
- **Resultado esperado:** Código de estado `400 Bad Request` y JSON de respuesta:
    ```json
    {
      "error":  "Invalid page parameter"
    }
    ```
- **Resultado obtenido:** Código de estado `200 OK` y devuelve la lista de personajes.
- **Impacto**: Permite realizar peticiones mal formadas que deberían ser rechazadas por el servidor.
- **Casos de prueba afectados:**
  - CP17 - Consultar listado de personajes con paginación indicando un valor de página no entero
  - CP18 - Consultar listado de personajes con paginación indicando un valor de página entero menor a 1
___

## BUG 2 - Estructura de error inconsistente al consultar un personaje inexistente
- **ID:** BUG-002
- **Descripción del error:** Al consultar el detalle de un personaje indicando el identificador de un personaje que no existe, comprobamos que en la respuesta no se incluye el campo id y que el campo error tiene un valor diferente al esperado, además se incluyen otros campos adicionales no esperados.
- **Resultado esperado:** JSON que contenga las llaves error e id.
    ```json
    {
      "error":  "Character not found", 
      "id":  "<id enviado>"
    }
    ```
- **Resultado obtenido:** JSON de respuesta diferente al esperado.
  ```json
  {
    "message": "Character not found",
    "error": "Not Found",
    "statusCode": 404
  } 
  ```
- **Casos de prueba afectados:**
  - CP19 - Consultar detalle de un personaje inexistente, identificador numérico
___

## BUG 3 - Estructura de error inconsistente al validar el tipo de dato del identificador del personaje
- **ID:** BUG-003
- **Descripción del error:** Al consultar el detalle de un personaje indicando un identificador de personaje con un valor de tipo no numérico, comprobamos que el código de la respuesta no es correcto y que en la respuesta no se incluye el campo id y que el campo error tiene un valor diferente al esperado, además se incluyen otros campos adicionales no esperados.
- **Resultado esperado:** Código de estado `404 Not Found` y JSON de respuesta:
    ```json
    {
      "error":  "Character not found", 
      "id":  "<id enviado>"
    }
    ```
- **Resultado obtenido:** Código de estado `400 Bad Request` y JSON de respuesta diferente al esperado.
  ```json
  {
    "message": "Character not found",
    "error": "Not Found",
    "statusCode": 404
  } 
  ```
- **Casos de prueba afectados:**
  - CP19 - Consultar detalle de un personaje inexistente, identificador numérico
___

## BUG 4 - Formato de Content-Type Incompleto al consultar el listado de personajes
- **ID:** BUG-004
- **Descripción del error:** Al consultar el listado de personajes, comprobamos que en el contenido del tipo de la respuesta "Content-Type" no se indica la especificación del charset.
- **Resultado esperado:** application/json; charset=utf-8.
- **Resultado obtenido:** application/json.
- **Impacto:** Riesgo de visualización incorrecta de caracteres especiales (acentos, ñ) en distintos idiomas.
- **Casos de prueba afectados:**
  - CP02 - Consultar listado de personajes paginado, validar tipo de contenido de la respuesta