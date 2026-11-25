---
title: "PreparedStatement"
description: "Parámetros SQL, firmas de métodos, consultas, DML y seguridad."
---

## Qué es y cuándo utilizarlo

PreparedStatement representa una sentencia SQL parametrizable asociada a una Connection. Úsala cuando una consulta recibe valores de un formulario, objeto, búsqueda o identificador. Un parámetro representa un valor SQL, no un nombre de tabla, columna ni palabra reservada.

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

## Fechas y valores nulos

```java
if (fechaApertura == null) {
    ps.setNull(1, java.sql.Types.DATE);
} else {
    ps.setDate(1, java.sql.Date.valueOf(fechaApertura));
}
```

El ejemplo usa LocalDate y una columna que admite NULL. Si el dato es obligatorio, valídalo antes de ejecutar.

## Seguridad: qué resuelve y qué no

Concatenar texto del usuario dentro de SQL mezcla datos e instrucciones y puede romper la sintaxis o introducir inyección SQL. Vincular parámetros separa los valores de la sentencia. Si debes seleccionar dinámicamente una columna para ordenar, usa una lista cerrada de nombres permitidos, no un valor arbitrario.

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
