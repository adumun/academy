---
title: "SQL y CRUD"
description: "Esquema relacional, tipos, restricciones, consultas y operaciones seguras."
---

## Modelo relacional

Una tabla contiene filas y columnas. Cada fila representa un registro; la clave primaria identifica una fila de manera única. Las restricciones permiten que la base de datos proteja invariantes aunque una aplicación tenga errores.

| Categoría | Sentencias |
| --- | --- |
| DDL | CREATE, ALTER, DROP |
| Consultas | SELECT |
| DML de modificación | INSERT, UPDATE, DELETE |
| Control transaccional | COMMIT, ROLLBACK |

## Esquema de práctica

Ejecuta en MariaDB local con una cuenta autorizada para crear el esquema. No lo ejecutes sobre bases de datos de terceros.

```sql
CREATE DATABASE IF NOT EXISTS preparacion_db;
USE preparacion_db;

CREATE TABLE libro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL
);

INSERT INTO libro (titulo)
VALUES ('Java esencial'), ('Bases de datos');
```

Ejecuta la creación de tabla e inserciones iniciales una sola vez. AUTO_INCREMENT genera el ID; NOT NULL rechaza NULL, pero no rechaza necesariamente una cadena vacía. La aplicación debe validar el título.

## Tipos frecuentes

| SQL | Java habitual |
| --- | --- |
| INT | int / Integer |
| BIGINT | long / Long |
| VARCHAR | String |
| DECIMAL | BigDecimal |
| DATE | LocalDate con conversión JDBC |
| BOOLEAN | boolean / Boolean |

La nulabilidad y el rango real deben ser compatibles con el modelo.

## Consultar

```sql
SELECT id, titulo
FROM libro
WHERE id = 1;

SELECT id, titulo
FROM libro
ORDER BY titulo, id;
```

WHERE filtra filas; ORDER BY define su orden. El orden físico de las filas no está garantizado.

## Modificar

```sql
INSERT INTO libro (titulo)
VALUES ('POO');

UPDATE libro
SET titulo = 'POO aplicada'
WHERE id = 1;

DELETE FROM libro
WHERE id = 2;
```

Los IDs concretos son ilustrativos; comprueba primero los existentes en tu entorno. Ejecuta modificaciones solamente sobre el esquema desechable.

## Pruebas de aceptación

- Una inserción válida incrementa la cantidad de registros.
- Una actualización por clave afecta al registro objetivo.
- Un DELETE por ID inexistente no elimina otras filas.
- Un título NULL se rechaza por la restricción.
- Una lectura con WHERE no modifica la tabla.

En Java, usa marcadores ? y setters en lugar de concatenar valores. Nunca retires WHERE de una modificación por registro para «hacerla funcionar».

## Conceptos relacionados

- [JDBC](/handbook/java/jdbc/)
- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [CRUD desde Java](/handbook/java/jdbc/crud/)
- [ResultSet](/handbook/java/jdbc/result-set/)

## Documentación oficial

- [SQL MariaDB](https://mariadb.com/docs/server/reference/sql-statements/)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
