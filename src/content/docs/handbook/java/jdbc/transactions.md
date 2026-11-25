---
title: "Transacciones JDBC"
description: "Auto-commit, commit, rollback y límites de una unidad de trabajo."
---

## Qué problema resuelven

Una transacción agrupa operaciones relacionadas que deben confirmarse juntas o revertirse. Ejemplo: crear un préstamo y marcar un libro como no disponible; no conviene confirmar solo una de las dos operaciones.

## API

| Método | Efecto |
| --- | --- |
| getAutoCommit() | Consulta modo automático |
| setAutoCommit(false) | Inicia control explícito de confirmación |
| commit() | Confirma cambios pendientes |
| rollback() | Revierte cambios pendientes |

En modo auto-commit, cada sentencia se confirma cuando se completa según el contrato JDBC. No confundas ejecutar una sentencia con completar una unidad de negocio.

## Ejemplo focalizado

Supone dos registros existentes en una tabla de práctica, un motor transaccional y una conexión exclusiva de esta operación. Actualiza ambos títulos o revierte ambos.

```java
public void actualizarPar(int idA, int idB) throws SQLException {
    String sql = "UPDATE libro SET titulo = ? WHERE id = ?";

    try (Connection con = DatabaseConnection.getConnection()) {
        con.setAutoCommit(false);

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "Primero");
            ps.setInt(2, idA);
            ps.executeUpdate();

            ps.setString(1, "Segundo");
            ps.setInt(2, idB);
            ps.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackError) {
                e.addSuppressed(rollbackError);
            }
            throw e;
        }
    }
}
```

El ejemplo muestra atomicidad ante errores SQL, no valida que los IDs existan. Una regla de negocio adicional debe comprobar los resultados antes del commit. Algunos DDL generan commits implícitos según el motor.

## Prueba y advertencias

En un esquema desechable, provoca un fallo controlado en la segunda modificación y verifica que la primera tampoco quede confirmada. No mezcles conexiones independientes esperando que formen una única transacción. En aplicaciones con pool, el ciclo de vida y el restablecimiento de estado dependen de su configuración.

Esta ficha es ampliación: no exige incorporar transacciones al laboratorio CRUD simple si no forman parte del enunciado.

## Conceptos relacionados

- [Connection](/handbook/java/jdbc/connection/)
- [CRUD](/handbook/java/jdbc/crud/)

## Documentación oficial

- [Connection, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/Connection.html)
