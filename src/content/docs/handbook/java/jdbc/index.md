---
title: "JDBC"
description: "Modelo mental, componentes, ciclo de vida y acceso relacional desde Java."
---

JDBC (Java Database Connectivity) es una [API](/handbook/java/programming/) que define contratos estándar de Java para comunicarse con una [base de datos relacional](/handbook/sql/relational-model/), es decir, datos estructurados en tablas, filas y columnas. El driver (conector) es una biblioteca que implementa esos contratos y traduce las operaciones al protocolo del servidor, las reglas de comunicación con el gestor de datos. Un ORM mapea objetos y relaciones a almacenamiento relacional con un nivel de automatización superior; JDBC no es una base de datos ni un ORM.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Genéricos](/handbook/java/generics/)
- [Colecciones](/handbook/java/collections/)
- [Excepciones y try-with-resources](/handbook/java/exceptions/)
- [SQL](/handbook/sql/)

## Arquitectura mental

| Componente | Responsabilidad | No confundir con |
| --- | --- | --- |
| Driver JDBC | Implementar la comunicación con el motor | Servidor MariaDB |
| DriverManager | Obtener conexiones mediante drivers disponibles | Pool de conexiones |
| Connection | Representar una sesión con la base | Una consulta |
| PreparedStatement | Preparar SQL y vincular valores | Una fila del resultado |
| ResultSet | Representar resultados y cursor | Una List de objetos |
| SQLException | Informar fallos de acceso SQL | Validación de negocio |

El flujo habitual es aplicación → API JDBC → driver → servidor. El resultado vuelve por el camino inverso. Una colección Java no queda vinculada automáticamente a las filas del servidor.

## Ejemplo de extremo a extremo

Supone una tabla libro con columnas id y titulo, un driver en Maven y variables de entorno DB_USER y DB_PASSWORD configuradas. Es un [método](/handbook/java/methods/) para listar registros dentro de una clase que importa java.sql y java.util; el modelo Libro se explica en la ficha de POO.

```java
public List<Libro> listarTodos() throws SQLException {
    String sql = "SELECT id, titulo FROM libro ORDER BY id";
    List<Libro> libros = new ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Libro libro = new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo")
            );
            libros.add(libro);
        }
    }

    return libros;
}
```

## Ciclo de vida de recursos

La conexión permanece abierta mientras se utilizan sentencia y resultado. [Try-with-resources](/handbook/java/exceptions/) cierra en orden inverso: ResultSet, sentencia, conexión. La lista ya construida permanece disponible después de cerrarlos.

| Necesidad | API habitual |
| --- | --- |
| Leer registros | executeQuery() |
| Insertar, actualizar o eliminar | executeUpdate() |
| Agrupar varias modificaciones | setAutoCommit(false), commit(), rollback() |
| Diagnosticar un fallo | SQLException, SQLState y código del proveedor |

## Comprobación

Con una tabla que contiene dos registros, el método devuelve dos objetos. Una tabla vacía devuelve una lista vacía; una conexión inválida lanza una excepción. No conviertas indiscriminadamente los errores en listas vacías: ocultarías la diferencia entre ausencia de datos y fallo del sistema.

## Buenas prácticas

Abre recursos cerca de la operación que los usa, parametriza valores, selecciona columnas explícitas y separa acceso a datos de la interfaz. Para aplicaciones grandes, estudia DataSource y pools; no son prerrequisitos del primer ejemplo.

## Una operación de lectura de principio a fin

```text
Aplicación
   │ solicita conexión y prepara SELECT
   ▼
API JDBC / Driver ── consulta ──► Motor relacional
   ▲                              │
   └──────── filas ────────────────┘
   │ next() + getters tipados
   ▼
Objetos Java → List → consumidor
```

La conexión representa la sesión; la sentencia contiene la operación y sus parámetros; el resultado ofrece un cursor sobre las filas. El mapeo crea objetos Java independientes del ciclo de vida del cursor. Si la aplicación modifica uno de esos objetos posteriormente, no modifica automáticamente el registro original.

| Estado | Interpretación |
|---|---|
| Conexión abierta, SELECT válido | Se puede intentar leer |
| next() devuelve true | Existe una fila actual |
| next() devuelve false desde el inicio | Consulta sin filas |
| SQLException | Fallo que debe propagarse o traducirse |

## Elegir la ficha según la necesidad

Para configurar credenciales y permisos, comienza por la preparación del motor. Para valores aportados por el usuario, estudia PreparedStatement; para transformar filas, ResultSet. Cuando varias escrituras deben confirmarse juntas, revisa transacciones antes de introducir un servicio que coordine DAOs. Estas responsabilidades son distintas: JDBC permite ejecutarlas, pero no define por sí solo la arquitectura del dominio.

## Conceptos relacionados

- [Preparar MariaDB](/handbook/java/jdbc/setup-mariadb/)

- [Conexión JDBC](/handbook/java/jdbc/connection/)
- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [Transacciones](/handbook/java/jdbc/transactions/)
- [DAO y MVC](/handbook/architecture/)
- [SQL y CRUD](/handbook/sql/)

## Documentación oficial

- [Paquete java.sql, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/package-summary.html)
- [MariaDB Connector/J](https://mariadb.com/docs/connectors/mariadb-connector-j)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
