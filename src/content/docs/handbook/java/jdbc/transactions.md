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

## ACID y límites · complementario

Atomicidad: confirmar o revertir la unidad. Consistencia: preservar reglas de integridad. Aislamiento: controlar interferencias entre transacciones concurrentes. Durabilidad: conservar cambios confirmados según garantías del motor y configuración. JDBC configura la sesión, pero el motor aplica estas garantías.

| API de Connection | Propósito |
| --- | --- |
| getTransactionIsolation() | Nivel de aislamiento actual |
| setTransactionIsolation(int) | Solicitar otro nivel soportado |
| setSavepoint() | Punto intermedio para rollback parcial |
| rollback(Savepoint) | Revertir hasta el punto indicado |
| releaseSavepoint(Savepoint) | Liberar el punto |

Evita cambiar el nivel de aislamiento durante una transacción activa: el resultado puede depender del driver. En el ejemplo anterior, una sentencia sin coincidencias puede retornar cero sin lanzar excepción; si el negocio requiere que ambos libros existan, valida ese hecho y lanza un fallo antes del commit. Un error durante el commit o su respuesta de red puede dejar incertidumbre sobre el resultado; no reintentes escrituras automáticamente sin una estrategia de reconciliación o idempotencia.

Una transacción no debe permanecer abierta mientras el usuario piensa en un diálogo. Primero recoge y valida entradas, luego ejecuta una unidad de trabajo breve. Los [recursos y excepciones](/handbook/java/exceptions/) ayudan a delimitar la propiedad de conexión y rollback.

## Prueba y advertencias

En un esquema desechable, provoca un fallo controlado en la segunda modificación y verifica que la primera tampoco quede confirmada. No mezcles conexiones independientes esperando que formen una única transacción. En aplicaciones con pool, el ciclo de vida y el restablecimiento de estado dependen de su configuración.

Esta ficha es ampliación: no exige incorporar transacciones al laboratorio CRUD simple si no forman parte del enunciado.

## Conceptos relacionados

- [Connection](/handbook/java/jdbc/connection/)
- [CRUD](/handbook/java/jdbc/crud/)

## Documentación oficial

- [Connection, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/Connection.html)
