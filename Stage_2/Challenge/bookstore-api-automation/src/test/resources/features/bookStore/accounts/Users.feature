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