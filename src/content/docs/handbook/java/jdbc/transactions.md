---
title: "Transacciones JDBC"
description: "Auto-commit, commit, rollback y límites de una unidad de trabajo."
---

## Qué problema resuelven

Una transacción agrupa operaciones relacionadas que deben confirmarse juntas o revertirse. Confirmar (commit) aplica los cambios pendientes según las garantías del motor; revertir (rollback) descarta los cambios no confirmados de esa unidad. Una unidad de trabajo representa una operación completa del negocio, que puede requerir varias sentencias SQL. El motor es el componente que administra almacenamiento e integridad de los datos (consulta [modelo relacional](/handbook/sql/relational-model/)). Ejemplo: crear un préstamo y marcar un libro como no disponible; no conviene confirmar solo una de las dos operaciones.

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

El ejemplo muestra atomicidad ante errores SQL, no valida que los IDs existan. Una regla de negocio adicional debe comprobar los resultados antes del commit. Algunas sentencias DDL (instrucciones que definen estructuras, como CREATE o ALTER) generan commits implícitos según el motor. Consulta [SQL y CRUD](/handbook/sql/).

## ACID y límites · complementario

Atomicidad: confirmar o revertir la unidad. Consistencia: preservar reglas de integridad. Aislamiento: controlar interferencias entre transacciones concurrentes. Durabilidad: conservar cambios confirmados según garantías del motor y configuración. JDBC configura la sesión, pero el motor aplica estas garantías.

| API de Connection | Propósito |
| --- | --- |
| getTransactionIsolation() | Nivel de aislamiento actual |
| setTransactionIsolation(int) | Solicitar otro nivel soportado |
| setSavepoint() | Punto intermedio para rollback parcial |
| rollback(Savepoint) | Revertir hasta el punto indicado |
| releaseSavepoint(Savepoint) | Liberar el punto |

Evita cambiar el nivel de aislamiento durante una transacción activa: el resultado puede depender del driver. En el ejemplo anterior, una sentencia sin coincidencias puede retornar cero sin lanzar excepción; si el negocio requiere que ambos libros existan, valida ese hecho y lanza un fallo antes del commit. Un error durante el commit o su respuesta de red puede dejar incertidumbre sobre el resultado; no reintentes escrituras automáticamente sin una estrategia de reconciliación (comprobar qué ocurrió antes de decidir) o idempotencia (repetir la misma operación lógica sin duplicar su efecto).

Una transacción no debe permanecer abierta mientras el usuario piensa en un diálogo. Primero recoge y valida entradas, luego ejecuta una unidad de trabajo breve. Los [recursos y excepciones](/handbook/java/exceptions/) ayudan a delimitar la propiedad de conexión y rollback.

## Prueba y advertencias

En un esquema desechable, provoca un fallo controlado en la segunda modificación y verifica que la primera tampoco quede confirmada. No mezcles conexiones independientes esperando que formen una única transacción. En aplicaciones con pool, el ciclo de vida y el restablecimiento de estado dependen de su configuración.

Esta ficha es ampliación: no exige incorporar transacciones al laboratorio CRUD simple si no forman parte del enunciado.

## Confirmación explícita y trayectoria de error

![Con auto-commit desactivado, dos cambios forman una unidad. Si ambos completan correctamente se confirma; si falla una operación, se revierte lo pendiente.](/diagrams/jdbc-transaction.svg)

| Momento | Sesión propia | Otras sesiones |
|---|---|---|
| Antes de los cambios | Estado original | Estado original |
| Cambios ejecutados sin commit | Puede leer sus propios cambios | Visibilidad según aislamiento y motor |
| Commit exitoso | Estado confirmado | Visible según aislamiento y momento de lectura |
| Rollback exitoso antes de commit | Cambios pendientes revertidos | No se consolidan esos cambios |

La tabla supone un motor transaccional y operaciones DML. El aislamiento regula interacciones con otras transacciones; no significa que todos los motores tengan idénticas garantías. Commit puede fallar o perderse la respuesta: un error de comunicación no siempre permite saber si el motor confirmó. No prometas rollback sobre una transacción que ya se confirmó.

## Prueba de integración independiente

Crea dos registros desechables con valores iniciales conocidos. En una misma conexión desactiva auto-commit, ejecuta la primera modificación y provoca una violación de restricción en la segunda. Captura el fallo, revierte y verifica ambos valores usando otra conexión después del rollback. Repite con dos modificaciones válidas y commit. No utilices CREATE/ALTER para simular un fallo dentro de esa unidad: algunos motores confirman implícitamente el DDL.

El ejemplo anterior necesita definir también qué ocurre con excepciones no SQL producidas en medio de la unidad, y verificar el restablecimiento de estado cuando se utiliza un pool. Una aplicación que captura un fallo no debe anunciar una confirmación que no verificó.

## Conceptos relacionados

- [Connection](/handbook/java/jdbc/connection/)
- [CRUD](/handbook/java/jdbc/crud/)

## Documentación oficial

- [Connection, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/Connection.html)
