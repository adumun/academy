---
title: "Preparar MariaDB para JDBC"
description: "Configurar esquema desechable, permisos mínimos, credenciales y prueba de conexión."
---

**Tipo:** guía práctica. **Nivel:** esencial. **Motor:** MariaDB local.

## Conocimientos previos recomendados

- [Maven y entorno Java](/handbook/tools/maven/)
- [SQL y CRUD](/handbook/sql/)
- [Connection](/handbook/java/jdbc/connection/)

## Objetivo y requisitos

Preparar un esquema de práctica y una cuenta de aplicación sin permisos administrativos. Se presupone MariaDB instalado y en ejecución; no es una guía de instalación para todos los sistemas operativos. Trabaja en una instancia local de desarrollo que administras. No reutilices las credenciales ni los datos de producción.

## Crear esquema y cuenta

Con una cuenta administradora autorizada, ejecuta el siguiente ejemplo. Sustituye el marcador de contraseña por una contraseña local propia antes de ejecutar. Evita guardar ese valor en Git.

```sql
CREATE DATABASE IF NOT EXISTS preparacion_db;

CREATE USER 'academy_lab'@'localhost'
IDENTIFIED BY 'REEMPLAZAR_CON_CONTRASENA_LOCAL';

GRANT SELECT, INSERT, UPDATE, DELETE
ON preparacion_db.*
TO 'academy_lab'@'localhost';

USE preparacion_db;

CREATE TABLE IF NOT EXISTS libro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL
);
```

Si la cuenta ya existe, revisa su configuración y permisos en vez de crear otra o modificar credenciales sin entender el estado. El host de la cuenta debe coincidir con la conexión permitida por el servidor; una aplicación en Docker puede requerir una configuración distinta. No otorgues permisos a cualquier host por defecto.

## Verificar desde el cliente

Conecta utilizando la cuenta de aplicación. El cliente solicitará la contraseña sin incluirla en el comando.

```bash
mariadb -h localhost -P 3306 -u academy_lab -p preparacion_db
```

```sql
SELECT DATABASE();
SELECT CURRENT_USER();
SHOW GRANTS;
SELECT id, titulo FROM libro ORDER BY id;
```

Debe aparecer el esquema correcto, la cuenta efectiva y sus permisos. Una tabla vacía es un resultado válido. Si el cliente no está disponible en PATH, usa un cliente SQL configurado con los mismos valores.

## Configurar el proceso Java

Establece DB_USER y DB_PASSWORD en la configuración de ejecución de tu IDE. En Bash puedes evitar que la contraseña quede en el historial:

```bash
export DB_USER=academy_lab
read -rs -p "Contraseña MariaDB: " DB_PASSWORD
echo
export DB_PASSWORD
```

La variable debe existir en el proceso que inicia Java. En PowerShell o el IDE, utiliza su propio mecanismo de configuración de entorno. No publiques el valor ni lo escribas en pom.xml. La URL del ejemplo es jdbc:mariadb://localhost:3306/preparacion_db.

## Checkpoint Java

Ejecuta TestConnection de la ficha [Connection](/handbook/java/jdbc/connection/) con la dependencia MariaDB Connector/J del [POM de referencia](/handbook/tools/maven/). El resultado esperado es Conexión válida: true. Después ejecuta un SELECT con el DAO antes de intentar JavaFX.

## Errores habituales

- Connection refused: confirma que el servidor escucha en el host y puerto configurados.
- Access denied: revisa contraseña, cuenta efectiva, host permitido y grants.
- Unknown database: verifica el esquema.
- No suitable driver: revisa classpath, dependencia y prefijo JDBC.
- Permission denied al crear tablas con academy_lab: la cuenta está deliberadamente limitada a CRUD; realiza cambios de esquema con la cuenta administradora.

No es necesario conceder ALL PRIVILEGES, usar root en Java ni desactivar controles de seguridad para completar el laboratorio.

## Documentación oficial

- [CREATE USER](https://mariadb.com/kb/en/create-user/)
- [GRANT](https://mariadb.com/kb/en/grant/)
- [MariaDB Connector/J](https://mariadb.com/docs/connectors/mariadb-connector-j)

## Conceptos relacionados

- [Connection](/handbook/java/jdbc/connection/)
- [Restricciones y escrituras SQL](/handbook/sql/constraints-and-writes/)
- [Diagnóstico](/handbook/troubleshooting/)
