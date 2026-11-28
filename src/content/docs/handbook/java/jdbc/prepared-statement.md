---
title: "PreparedStatement"
description: "Parámetros SQL, firmas de métodos, consultas, DML y seguridad."
---

## Conocimientos previos recomendados

- [Conexión JDBC](/handbook/java/jdbc/connection/)
- [Excepciones y recursos](/handbook/java/exceptions/)
- [SQL](/handbook/sql/)

## Qué es y cuándo utilizarlo

PreparedStatement representa una sentencia [SQL](/handbook/sql/queries/) parametrizable asociada a una [Connection](/handbook/java/jdbc/connection/). Una sentencia es una instrucción para el motor, por ejemplo SELECT o UPDATE. Parametrizar significa mantener la estructura SQL y suministrar los valores por separado mediante marcadores ?. Un parámetro SQL representa un valor de entrada; los parámetros de un [método Java](/handbook/java/methods/) son variables declaradas en su firma. Los setters vinculan valores de [tipos Java](/handbook/java/types/) compatibles con tipos SQL. Úsala cuando una consulta recibe valores de un formulario, objeto, búsqueda o identificador. Un parámetro representa un valor SQL, no un nombre de tabla, columna ni palabra reservada.

## Anatomía de una sentencia

```sql
UPDATE libro SET titulo = ? WHERE id = ?;
```

El primer marcador recibe el título y el segundo el ID. Los índices son independientes de los índices de listas Java: comienzan en 1.

| Firma simplificada | Tipo o resultado |
| --- | --- |
| void setString(int index, String value) | VARCHAR y textos |
| void setInt(int index, int value) | INTEGER |
| void setLong(int index, long value) | BIGINT |
| void setBigDecimal(int index, BigDecimal value) | DECIMAL exacto |
| void setBoolean(int index, boolean value) | Booleano |
| void setDate(int index, java.sql.Date value) | DATE |
| void setNull(int index, int sqlType) | NULL tipado |
| ResultSet executeQuery() | Resultado de una consulta |
| int executeUpdate() | Filas afectadas por DML; cero para ciertas sentencias sin conteo |
| void clearParameters() | Limpia valores vinculados |
| void close() | Libera recursos |

Los métodos JDBC anteriores pueden lanzar SQLException. Los setters deben corresponder al tipo SQL de cada marcador.

## SELECT parametrizado

El siguiente método supone una Connection abierta recibida por parámetro y un modelo Libro.

<!-- example:jdbc-ps-find -->
```java
public Optional<Libro> buscarPorId(Connection con, int id)
        throws SQLException {

    String sql = "SELECT id, titulo FROM libro WHERE id = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo")
                );
                return Optional.of(libro);
            }
        }
    }

    return Optional.empty();
}
```

Optional permite representar la ausencia de un registro sin devolver null. Requiere java.util.Optional; no es obligatorio para el primer laboratorio, pero resulta útil como contrato.

## UPDATE parametrizado

<!-- example:jdbc-ps-update -->
```java
public int actualizar(Connection con, Libro libro) throws SQLException {
    String sql = "UPDATE libro SET titulo = ? WHERE id = ?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, libro.getTitulo());
        ps.setInt(2, libro.getId());

        return ps.executeUpdate();
    }
}
```

La cantidad afectada permite comprobar el resultado; su significado exacto para UPDATE depende de la semántica del servidor y conector (filas coincidentes o modificadas). No presupongas que cero significa necesariamente que el ID no existe.

## Ausencia de registros

El método buscarPorId utiliza [Optional](/handbook/java/optional/) para expresar ausencia y no para ocultar SQLException. La conversión de [LocalDate](/handbook/java/dates/) a DATE debe preservar la intención del dominio.

## Fechas y valores nulos

<!-- example:jdbc-ps-date -->
```java
if (fechaApertura == null) {
    ps.setNull(1, java.sql.Types.DATE);
} else {
    ps.setDate(1, java.sql.Date.valueOf(fechaApertura));
}
```

El ejemplo usa LocalDate y una columna que admite NULL. Si el dato es obligatorio, valídalo antes de ejecutar.

## Recuperar claves generadas · complementario

Cuando el servidor genera el ID al insertar, puedes solicitar las claves mediante `Statement.RETURN_GENERATED_KEYS`. El siguiente fragmento presupone una Connection abierta, un título validado y una tabla libro con ID autoincremental. Importa java.sql.PreparedStatement, java.sql.ResultSet, java.sql.Statement y java.sql.SQLException.

<!-- example:jdbc-ps-keys -->
```java
String sql = "INSERT INTO libro (titulo) VALUES (?)";

try (PreparedStatement ps = con.prepareStatement(
        sql, Statement.RETURN_GENERATED_KEYS)) {

    ps.setString(1, titulo);
    int filas = ps.executeUpdate();

    if (filas != 1) {
        throw new SQLException("Inserción no confirmada");
    }

    try (ResultSet claves = ps.getGeneratedKeys()) {
        if (!claves.next()) {
            throw new SQLException("Clave no disponible");
        }

        int idGenerado = claves.getInt(1);
        System.out.println(idGenerado);
    }
}
```

La disponibilidad y forma de las claves dependen del driver y la sentencia. El conteo no es la clave. Si la inserción ya se confirmó por auto-commit, fallar al recuperar la clave no la revierte automáticamente. Para una unidad atómica, revisa [transacciones JDBC](/handbook/java/jdbc/transactions/).

## Seguridad: qué resuelve y qué no

La inyección SQL ocurre cuando datos externos alteran la estructura o intención de una sentencia. Concatenar texto del usuario dentro de SQL mezcla datos e instrucciones y puede romper la sintaxis o introducir inyección SQL. Vincular parámetros separa los valores de la sentencia. Si debes seleccionar dinámicamente una columna para ordenar, usa una lista cerrada de nombres permitidos, no un valor arbitrario.

## Errores habituales

| Error | Causa posible | Corrección |
| --- | --- | --- |
| Parameter index out of range | Índice 0 o mayor que número de marcadores | Contar los signos ? |
| No value specified for parameter | Falta asignación | Vincular todos antes de ejecutar |
| Tipo incompatible | setInt para un texto, o viceversa | Revisar esquema y orden |
| executeQuery en DELETE | API incorrecta | Usar executeUpdate |
| SQL syntax error | SQL incorrecto | Revisar sentencia; parametrizar no corrige sintaxis |

## Prueba observable

Busca un título que contenga un apóstrofo, como O'Connor, utilizando setString. Debe tratarse como texto. Busca un ID existente e inexistente y comprueba ambos resultados. Trabaja solo sobre una base desechable.

## Qué significa vincular valores

![En UPDATE, el marcador 1 corresponde a titulo y recibe setString; el marcador 2 corresponde a id y recibe setInt. El driver ejecuta la estructura SQL con los valores vinculados.](/diagrams/jdbc-parameters.svg)

Los marcadores se cuentan en el orden de la sentencia, empezando en 1. El driver recibe la estructura SQL y sus valores por medio de la API. El protocolo concreto (preparación en servidor o procesamiento en cliente) depende del conector y configuración; lo importante es no construir la estructura mezclando entrada arbitraria. No encierres el marcador entre comillas, porque se convertiría en un literal de texto.

| Intención | Forma correcta |
|---|---|
| Buscar ID | WHERE id = ? y setInt(1, id) |
| Buscar texto | WHERE titulo = ? y setString(1, titulo) |
| Buscar ausencia SQL | WHERE columna IS NULL (no = NULL) |
| Cambiar columna de orden | Selección mediante lista cerrada de columnas válidas |

## Ejemplo de lectura y propiedad de recursos

Fragmento Java dentro de un DAO con una Connection abierta y los imports de java.sql. No requiere una clase de modelo para comprobar parámetros.

<!-- example:jdbc-ps-read -->
```java
String consulta = "SELECT titulo FROM libro WHERE id = ?";
try (PreparedStatement sentencia = conexion.prepareStatement(consulta)) {
    sentencia.setInt(1, 7);
    try (ResultSet resultado = sentencia.executeQuery()) {
        if (resultado.next()) {
            System.out.println(resultado.getString("titulo"));
        } else {
            System.out.println("Sin coincidencias");
        }
    }
}
```

El fragmento necesita una tabla libro con id y titulo. El resultado depende de los datos reales; el código cierra resultado y sentencia, no la conexión recibida. Prueba ID existente, ausente y un fallo de conexión por separado.

## Conceptos relacionados

- [Connection](/handbook/java/jdbc/connection/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [CRUD desde Java](/handbook/java/jdbc/crud/)
- [SQL](/handbook/sql/)

## Documentación oficial

- [PreparedStatement, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/PreparedStatement.html)
- [Statement](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/Statement.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
