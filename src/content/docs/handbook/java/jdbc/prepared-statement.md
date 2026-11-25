---
title: PreparedStatement
description: Consultas parametrizadas, índices, ejecución y errores frecuentes.
---

## Para qué sirve

Representa una sentencia SQL preparada. Los marcadores `?` representan valores, no identificadores SQL como nombres de tablas.

## Ejemplo de lectura

Este fragmento presupone una Connection abierta y un modelo Libro con constructor (int, String).

```java
String sql = "SELECT id, titulo FROM libro WHERE id = ?";

try (PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setInt(1, id);

    try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            Libro libro = new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo")
            );
            System.out.println(libro.getTitulo());
        }
    }
}
```

| Método | Uso |
| --- | --- |
| setInt(int, int) | Vincular un entero |
| setString(int, String) | Vincular texto |
| executeQuery() | Obtener ResultSet |
| executeUpdate() | Obtener el número de filas afectadas por DML |

Los índices empiezan en 1. Evita concatenar valores del formulario dentro del SQL. Los métodos pueden lanzar SQLException.

## Diagnóstico

Parameter index out of range: compara el número de marcadores con las posiciones usadas por los setters.

[API Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/PreparedStatement.html)
