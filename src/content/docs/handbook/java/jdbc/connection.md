---
title: "Connection y DriverManager"
description: "Configurar la conexión, administrar su ciclo de vida y diagnosticar errores."
---

## Qué representa una conexión

Una Connection es una sesión con una base de datos. Puede preparar sentencias y controlar transacciones. Cerrarla libera los recursos asociados; no cierra el servidor.

## Requisitos previos

- Servidor MariaDB iniciado, puerto conocido y esquema existente.
- Usuario con permisos sobre el esquema de práctica.
- Dependencia org.mariadb.jdbc:mariadb-java-client en Maven.
- Variables de entorno DB_USER y DB_PASSWORD en el proceso Java.

### URL JDBC

```text
jdbc:mariadb://localhost:3306/preparacion_db
```

| Parte | Significado |
| --- | --- |
| jdbc:mariadb: | Protocolo JDBC del conector |
| localhost | Host del servidor |
| 3306 | Puerto habitual de MariaDB; puede cambiar |
| preparacion_db | Base de datos seleccionada |

En un contenedor, localhost identifica ese contenedor, no necesariamente el host ni otro servicio.

## API principal

| Firma | Retorno o efecto |
| --- | --- |
| DriverManager.getConnection(String, String, String) | Connection abierta o SQLException |
| prepareStatement(String) | PreparedStatement asociado a la conexión |
| isValid(int) | Comprueba validez con timeout en segundos |
| setAutoCommit(boolean) | Configura confirmación automática |
| close() | Libera la conexión |

## Clase reutilizable

Archivo database/DatabaseConnection.java, sin credenciales fijas.

```java
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/preparacion_db";
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (user == null || user.isBlank() || password == null) {
            throw new SQLException("Configura DB_USER y DB_PASSWORD");
        }

        return DriverManager.getConnection(url, user, password);
    }
}
```

## Prueba independiente

```java
package app;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) throws SQLException {
        try (Connection con = DatabaseConnection.getConnection()) {
            System.out.println("Conexión válida: " + con.isValid(3));
        }
    }
}
```

Salida esperada: Conexión válida: true, si servidor, usuario y esquema están disponibles.

## Diagnóstico por síntoma

| Síntoma | Primera comprobación |
| --- | --- |
| No suitable driver | Dependencia en classpath y prefijo JDBC |
| Connection refused | Host, puerto, servicio iniciado |
| Access denied | Usuario, contraseña, permisos y host permitido |
| Unknown database | Nombre exacto del esquema |
| Conexión cerrada | Alcance del try-with-resources |

No publiques contraseñas ni logs que las contengan. En sistemas productivos configura TLS, límites de tiempo y DataSource según el entorno.

## Conceptos relacionados

- [Maven](/handbook/tools/maven/)
- [JDBC](/handbook/java/jdbc/)
- [Transacciones](/handbook/java/jdbc/transactions/)
- [Diagnóstico](/handbook/troubleshooting/)

## Documentación oficial

- [Connection](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/Connection.html)
- [DriverManager](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/DriverManager.html)
- [MariaDB Connector/J](https://mariadb.com/docs/connectors/mariadb-connector-j/about-mariadb-connector-j)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
