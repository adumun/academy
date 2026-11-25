---
title: "Datos, persistencia y modelo relacional"
description: "Base de datos, servidor, esquema, tabla, filas, columnas y relaciones."
---

**Tipo:** explicación. **Nivel:** esencial.

## Datos en memoria y persistencia

Un dato es una representación de información. Una aplicación puede mantener datos en variables y objetos en memoria; normalmente se pierden al terminar el proceso. Persistencia significa conservar datos más allá de ese proceso, por ejemplo en archivos o una base de datos.

Una base de datos organiza información para consultarla y modificarla. Un sistema gestor de bases de datos (DBMS), como MariaDB, administra almacenamiento, concurrencia, permisos y consultas. Un servidor MariaDB es un proceso que atiende solicitudes de clientes, entre ellos aplicaciones Java.

## Cómo se organiza una base relacional

| Concepto | Ejemplo |
| --- | --- |
| Esquema o base de datos en MariaDB | preparacion_db |
| Tabla | libro |
| Columna | id, titulo |
| Fila o registro | 1, Java |
| Tipo de columna | INT, VARCHAR(120) |
| Clave primaria | id, identifica de forma única una fila |
| Clave foránea | libro_id, referencia otra clave válida |

Una tabla representa una relación de datos estructurados en filas y columnas. Cada columna tiene un nombre y un tipo; las restricciones limitan los valores y relaciones permitidos. El modelo relacional no requiere que las filas tengan un orden físico significativo.

## Correspondencia con Java

Una fila de libro puede representarse mediante un [objeto Libro](/handbook/java/classes-and-objects/). Una columna se lee como valor de un [tipo Java](/handbook/java/types/) compatible. Una consulta puede devolver varias filas que se convierten en una [colección](/handbook/java/collections/). No se trata de una conversión automática: el código de mapeo establece la correspondencia.

SQL es un lenguaje para definir, consultar y modificar datos. JDBC permite enviar operaciones SQL desde Java y leer resultados. Cambiar un objeto en memoria no actualiza automáticamente la fila.

## Conceptos relacionados

- [SQL y CRUD](/handbook/sql/)
- [Consultas](/handbook/sql/queries/)
- [Restricciones](/handbook/sql/constraints-and-writes/)
- [JDBC](/handbook/java/jdbc/)
- [DAO y MVC](/handbook/architecture/)

## Documentación oficial

- [MariaDB: SQL Statements](https://mariadb.com/docs/server/reference/sql-statements/)
