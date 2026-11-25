---
title: SQL y CRUD
description: Operaciones de lectura y modificación sobre una tabla relacional.
---

| Operación | Sentencia | Resultado habitual en JDBC |
| --- | --- | --- |
| Crear registro | INSERT | Filas afectadas |
| Leer | SELECT | ResultSet |
| Actualizar | UPDATE | Filas afectadas |
| Eliminar | DELETE | Filas afectadas |

```sql
SELECT id, titulo FROM libro WHERE id = 1;
```

Usa condiciones WHERE apropiadas en modificaciones por registro. En Java, vincula los valores con [PreparedStatement](/handbook/java/jdbc/prepared-statement/).

## Documentación oficial

[Referencia SQL MariaDB](https://mariadb.com/kb/en/sql-statements/)

## Conceptos relacionados

- [JDBC](/handbook/java/jdbc/)
- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [ResultSet](/handbook/java/jdbc/result-set/)

## Practicar en Academic Labs

- [Microdesafíos JDBC y JavaFX](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
