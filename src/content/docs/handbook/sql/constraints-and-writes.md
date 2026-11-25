---
title: "Restricciones y modificaciones SQL"
description: "Integridad, INSERT, UPDATE, DELETE, claves y verificación de cambios."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

## Conocimientos previos recomendados

- [SQL y CRUD](/handbook/sql/)
- [Consultas SQL](/handbook/sql/queries/)

## Integridad relacional

| Restricción | Qué protege |
| --- | --- |
| PRIMARY KEY | Identidad única y no nula de una fila |
| FOREIGN KEY | Existencia de una referencia válida |
| UNIQUE | Unicidad según las reglas del motor |
| NOT NULL | Ausencia prohibida |
| CHECK | Predicado sobre los datos |
| DEFAULT | Valor predeterminado cuando se omite la columna |

Una validación en JavaFX mejora la interacción, pero el motor debe proteger las reglas que deben cumplirse aunque otro cliente escriba. Un CHECK de longitud, por ejemplo, no reemplaza las reglas de negocio sobre contenido.

## Escrituras seguras

Ejecuta únicamente sobre preparacion_db desechable. Usa un motor transaccional como InnoDB.

```sql
INSERT INTO libro (titulo)
VALUES ('Manual de Java');

SELECT id, titulo
FROM libro
WHERE titulo = 'Manual de Java';
```

Comprueba qué ID generó la base. En Java, la ficha [PreparedStatement](/handbook/java/jdbc/prepared-statement/) explica la recuperación de claves. Si el título no es único, no lo uses como identificador.

## Actualizar y eliminar

Antes de modificar, ejecuta un SELECT con exactamente el mismo WHERE. El ID 1 es ilustrativo: sustitúyelo por el registro de prueba que elegiste.

```sql
START TRANSACTION;

SELECT id, titulo FROM libro WHERE id = 1;

UPDATE libro
SET titulo = 'Java actualizado'
WHERE id = 1;

SELECT ROW_COUNT() AS filas_afectadas;
SELECT id, titulo FROM libro WHERE id = 1;

ROLLBACK;
```

Comprueba que la modificación se revierte. ROW_COUNT debe consultarse inmediatamente después de la escritura. En un flujo real, el usuario autorizado decide si confirma con COMMIT.

DELETE elimina filas, DROP elimina objetos del esquema y TRUNCATE vacía una tabla con semántica particular del motor. No los intercambies.

## Claves y relaciones

Una clave foránea puede impedir eliminar un libro con préstamos. ON DELETE CASCADE o SET NULL tienen consecuencias de negocio diferentes; no las actives solo para evitar un error de integridad. Revisa el contrato y el ciclo de vida de los registros.

## Diagnóstico

| Síntoma | Qué revisar |
| --- | --- |
| Duplicate entry | Clave primaria o única existente |
| Column cannot be null | Restricción NOT NULL y valor enviado |
| Cannot delete or update a parent row | Referencias dependientes |
| Cero filas afectadas | WHERE, identidad y semántica de conteo |
| Varias filas inesperadas | Detener operación, revisar condición y transacción |

La aplicación debe conservar la diferencia entre ausencia, rechazo de integridad y error de acceso. Consulta [CRUD JDBC](/handbook/java/jdbc/crud/) y [transacciones JDBC](/handbook/java/jdbc/transactions/).

## Documentación oficial

- [Constraints](https://mariadb.com/kb/en/constraint/)
- [UPDATE](https://mariadb.com/kb/en/update/)
- [DELETE](https://mariadb.com/kb/en/delete/)
- [START TRANSACTION](https://mariadb.com/kb/en/start-transaction/)
