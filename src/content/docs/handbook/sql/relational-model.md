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

## Identidad, relaciones y cardinalidad

![Una categoría con id 1 puede estar relacionada con dos productos cuyos categoria_id son 1. Cada producto tiene su propia clave primaria.](/diagrams/relational-keys.svg)

Una clave primaria puede contener una o varias columnas; su valor identifica de forma única una fila y no admite NULL. Una clave foránea declara una referencia a una clave válida de otra tabla (o de la misma). La cardinalidad describe cuántas entidades pueden relacionarse: uno a uno, uno a muchos o muchos a muchos. Una relación muchos a muchos suele representarse mediante una tabla intermedia con las claves de ambos extremos y restricciones adecuadas.

### Ejemplo independiente de Java

Ejecuta en un esquema nuevo desechable de MariaDB:

```sql
CREATE TABLE categoria_demo (
    id INT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL
);
CREATE TABLE producto_demo (
    id INT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria_demo(id)
);
INSERT INTO categoria_demo VALUES (1, 'Audio');
INSERT INTO producto_demo VALUES (10, 'Parlante', 1), (11, 'Microfono', 1);
SELECT producto_demo.nombre, categoria_demo.nombre AS categoria
FROM producto_demo
JOIN categoria_demo ON producto_demo.categoria_id = categoria_demo.id
ORDER BY producto_demo.id;
```

Resultado esperado: Parlante y Microfono, ambos de categoría Audio. Intentar insertar un producto con categoria_id 99 debe incumplir la clave foránea si no existe esa categoría. Es una prueba del motor, no de la interfaz. El ejemplo presupone tablas inexistentes y un motor que aplica las restricciones. Consulta [restricciones](/handbook/sql/constraints-and-writes/) y [consultas](/handbook/sql/queries/).

## Conceptos relacionados

- [SQL y CRUD](/handbook/sql/)
- [Consultas](/handbook/sql/queries/)
- [Restricciones](/handbook/sql/constraints-and-writes/)
- [JDBC](/handbook/java/jdbc/)
- [DAO y MVC](/handbook/architecture/)

## Documentación oficial

- [MariaDB: SQL Statements](https://mariadb.com/docs/server/reference/sql-statements/)
