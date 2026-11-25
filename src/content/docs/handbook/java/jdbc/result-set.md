---
title: ResultSet
description: Cursor, lectura de columnas y mapeo a objetos.
---

Un ResultSet representa el resultado de una consulta JDBC. El cursor comienza antes de la primera fila.

## next()

```java
boolean next() throws SQLException
```

Devuelve true si avanzó a una fila válida y false cuando no quedan filas. No recibe parámetros.

```java
List<Libro> libros = new ArrayList<>();

while (rs.next()) {
    int id = rs.getInt("id");
    String titulo = rs.getString("titulo");

    Libro libro = new Libro(id, titulo);
    libros.add(libro);
}
```

El fragmento supone un ResultSet abierto con columnas id y titulo, y un modelo Libro compatible. Con cero filas, devuelve una colección vacía si el método retorna libros.

No leas columnas antes de avanzar el cursor ni después de agotarlo. Cierra el ResultSet junto con la sentencia y conexión mediante try-with-resources.

## Documentación oficial

[API Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/ResultSet.html)

## Conceptos relacionados

- [JDBC](/handbook/java/jdbc/)
- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [MVC y DAO](/handbook/architecture/)

## Practicar en Academic Labs

- [Microdesafíos JDBC y JavaFX](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
