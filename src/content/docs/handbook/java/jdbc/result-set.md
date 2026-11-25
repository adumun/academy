---
title: "ResultSet"
description: "Cursor, lectura tipada, NULL, mapeo y colecciones Java."
---

## Modelo mental

ResultSet permite recorrer filas obtenidas por una consulta. Un cursor forward-only (habitual por defecto) avanza en un solo sentido. No es una List ni contiene automáticamente objetos del dominio.

| Estado | ¿Se pueden leer columnas? |
| --- | --- |
| Antes de la primera fila | No |
| Sobre una fila válida | Sí |
| Después de la última fila | No |

Si hay dos filas, las llamadas a next() devuelven true, true y false. Con cero filas, la primera llamada devuelve false.

## API de referencia

| Firma simplificada | Retorno |
| --- | --- |
| boolean next() | Si avanzó a una fila válida |
| int getInt(String label) | Entero; cero ante NULL SQL |
| String getString(String label) | Texto o null |
| java.sql.Date getDate(String label) | Fecha SQL o null |
| BigDecimal getBigDecimal(String label) | Decimal exacto o null |
| boolean wasNull() | Si la última lectura obtuvo NULL SQL |
| T getObject(String label, Class<T> type) | Conversión tipada si el driver la admite |
| void close() | Libera recursos |

Los índices de columna también comienzan en 1; preferimos nombres o alias explícitos para reducir errores de mantenimiento.

## Mapeo fila por fila

```java
public List<Libro> mapear(ResultSet rs) throws SQLException {
    List<Libro> libros = new ArrayList<>();

    while (rs.next()) {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");

        Libro libro = new Libro(id, titulo);
        libros.add(libro);
    }

    return libros;
}
```

El método recibe un ResultSet abierto posicionado antes de la primera fila. Quien lo creó conserva la responsabilidad de cerrarlo. Las instancias Libro no dependen del cursor después de crearse.

## Una fila frente a muchas filas

Para buscar por una clave primaria, if (rs.next()) es suficiente cuando esperas como máximo una fila. Para listar, usa while. No llames next() otra vez dentro del bucle salvo que quieras avanzar deliberadamente y omitir una fila.

## NULL y fechas

Un entero primitivo no puede representar null. getInt devuelve cero si la columna es NULL SQL; consulta wasNull inmediatamente después de leer esa columna.

```java
int cantidad = rs.getInt("cantidad");
Integer cantidadOpcional = rs.wasNull() ? null : cantidad;

java.sql.Date fechaSql = rs.getDate("fecha_apertura");
LocalDate fecha = fechaSql == null ? null : fechaSql.toLocalDate();
```

Si la columna es NOT NULL, la validación del esquema simplifica el mapeo. No uses cero como sustituto de ausencia sin definir esa regla.

## Alias y proyecciones

```sql
SELECT id, titulo AS nombre_libro FROM libro ORDER BY id;
```

Lee rs.getString("nombre_libro") porque el alias es la etiqueta de columna. Seleccionar columnas explícitamente evita dependencias accidentales de SELECT *.

## Diagnóstico

- Antes de next(): no existe fila actual.
- Column not found: revisa nombres y alias del SELECT.
- Lista con el mismo objeto repetido: crea una instancia por fila.
- Fecha nula: valida antes de toLocalDate().
- ResultSet cerrado: revisa el alcance de recursos y el orden de lectura.

## Comprobación

Ejecuta consultas con cero, uno y dos registros. Compara IDs y títulos contra el SELECT directo. Un resultado vacío es una lista vacía, no un objeto ficticio.

## Conceptos relacionados

- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [Clases y colecciones](/handbook/java/fundamentals/)
- [DAO y MVC](/handbook/architecture/)
- [JavaFX TableView](/handbook/java/javafx/table-view/)

## Documentación oficial

- [ResultSet, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/ResultSet.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
