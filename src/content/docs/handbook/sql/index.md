---
title: "SQL y CRUD"
description: "Esquema relacional, tipos, restricciones, consultas y operaciones seguras."
---

## Modelo relacional

SQL (Structured Query Language) es un lenguaje para definir, consultar y modificar datos. Una [base de datos relacional](/handbook/sql/relational-model/) organiza datos en tablas con filas y columnas. Cada fila representa un registro; la clave primaria identifica una fila de manera única. Una restricción es una condición aplicada por el motor a los datos. Las restricciones permiten proteger [invariantes](/handbook/architecture/contracts/) (condiciones que deben mantenerse válidas) aunque una aplicación tenga errores. CRUD son las operaciones de crear, leer, actualizar y eliminar registros.

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

La nulabilidad indica si una columna admite NULL (ausencia de un valor); el rango indica qué valores puede representar el tipo. Deben ser compatibles con los [tipos Java](/handbook/java/types/) y el modelo.

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

En Java, usa [PreparedStatement](/handbook/java/jdbc/prepared-statement/) con marcadores ? y métodos de asignación de parámetros en lugar de concatenar valores. Nunca retires WHERE de una modificación por registro para «hacerla funcionar».

## Profundizar por necesidad

- [Consultas: SELECT, condiciones, JOIN y ordenamiento](/handbook/sql/queries/).
- [Restricciones y modificaciones SQL](/handbook/sql/constraints-and-writes/).
- [Tipos de fecha y conversión Java](/handbook/java/dates/).

No es necesario estudiar JOIN para ejecutar un CRUD de una sola tabla. Utiliza la ficha correspondiente cuando el problema incluya relaciones entre entidades.

## Observar el efecto de cada operación

El lenguaje SQL expresa operaciones sobre conjuntos de filas. El motor determina cómo ejecutarlas. CREATE TABLE define la estructura; INSERT agrega registros a esa estructura. SELECT permite observar los datos, mientras UPDATE y DELETE los modifican según un criterio.

| Operación sobre la tabla de práctica | Qué se espera observar |
|---|---|
| INSERT de un título nuevo | Una fila adicional con ID asignado |
| SELECT por el ID asignado | Una fila con sus valores |
| UPDATE por ese ID | El título actualizado; mismo ID |
| DELETE por ese ID | El registro deja de existir |

Antes de una escritura por criterio, ejecuta un SELECT con el mismo WHERE para revisar qué filas coinciden. En un esquema de práctica con motor transaccional, puedes envolver las modificaciones en una transacción y terminar con ROLLBACK para evitar conservarlas. No mezcles DDL con esa demostración: determinadas sentencias DDL producen commits implícitos en MariaDB. Consulta las fichas de restricciones y transacciones para conocer sus límites. Los resultados de esta tabla son expectativas, no ejecuciones certificadas.

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
