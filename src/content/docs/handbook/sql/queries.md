---
title: "Consultas SQL: SELECT, filtros y ordenamiento"
description: "Proyección, condiciones, NULL, JOIN y agregaciones."
---

**Tipo:** referencia y guía práctica. **Nivel:** esencial a complementario. **Motor de ejemplo:** MariaDB.

## Conocimientos previos recomendados

- [SQL y CRUD](/handbook/sql/)
- [Modelo relacional](/handbook/sql/relational-model/)

## Modelo mental

Si luego implementas el mapeo en Java, consulta [clases y objetos](/handbook/java/classes-and-objects/). Ese conocimiento no es un requisito para consultar SQL.

SELECT produce un resultado de [filas y columnas](/handbook/sql/relational-model/); en una consulta ordinaria de lectura no modifica los datos. Una condición es una expresión lógica que evalúa qué filas cumplen una regla; SQL admite TRUE, FALSE y UNKNOWN (por ejemplo, al comparar con NULL), a diferencia de los [booleanos Java](/handbook/java/expressions/) de dos valores. La proyección elige columnas, WHERE filtra filas y ORDER BY determina el orden. La consulta no es una colección Java hasta que se transforma mediante [ResultSet](/handbook/java/jdbc/result-set/).

```sql
SELECT id, titulo
FROM libro
WHERE titulo LIKE 'Java%'
ORDER BY titulo ASC, id ASC;
```

Usa el esquema desechable de SQL y CRUD. La consulta devuelve los títulos cuyo prefijo coincide; las reglas de comparación dependen de la intercalación (collation), que determina cómo se comparan y ordenan cadenas, incluyendo mayúsculas y acentos. El ID desempata el orden.

| Expresión | Propósito |
| --- | --- |
| WHERE | Filtrar filas según condición |
| AND / OR / NOT | Combinar o negar condiciones |
| IN (...) | Coincidir con alguno de los valores |
| BETWEEN a AND b | Intervalo inclusivo |
| LIKE | Patrón; % representa cero o más caracteres, _ uno |
| IS NULL / IS NOT NULL | Comprobar ausencia |
| ORDER BY | Orden explícito, ASC/DESC |
| LIMIT | Limitar resultados en MariaDB |
| AS | Alias de columna o tabla |

## NULL no es una cadena vacía

Una comparación con NULL mediante = produce UNKNOWN, no TRUE. WHERE conserva solo filas que satisfacen la condición. Utiliza IS NULL. La distinción importa al leer columnas mediante getInt o getString y wasNull.

## Relaciones y JOIN · complementario

Ejemplo autónomo en un esquema de práctica: crea una tabla prestamo que referencia libro.

```sql
CREATE TABLE prestamo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libro_id INT NOT NULL,
    fecha DATE NOT NULL,
    FOREIGN KEY (libro_id) REFERENCES libro(id)
);

SELECT l.id, l.titulo, p.fecha
FROM libro AS l
INNER JOIN prestamo AS p
    ON p.libro_id = l.id
ORDER BY l.id;
```

JOIN combina filas de dos orígenes de datos según una condición de unión. La cardinalidad describe cuántas filas de un origen pueden relacionarse con una fila del otro. INNER JOIN devuelve coincidencias; LEFT JOIN conserva las filas de la tabla izquierda incluso sin coincidencia (las columnas derechas pueden quedar NULL). Si existen varios préstamos por libro, la consulta puede devolver varias filas del mismo libro: no es automáticamente un error.

## Agregar y contar · complementario

```sql
SELECT l.id, l.titulo, COUNT(p.id) AS cantidad
FROM libro AS l
LEFT JOIN prestamo AS p
    ON p.libro_id = l.id
GROUP BY l.id, l.titulo
ORDER BY cantidad DESC, l.id;
```

Una agregación resume varios valores mediante una operación como COUNT (contar), SUM (sumar) o AVG (promediar). GROUP BY organiza filas en grupos para calcular esos resúmenes. COUNT(p.id) ignora los NULL de la parte derecha; COUNT(*) contaría también la fila resultante del LEFT JOIN sin préstamo. HAVING filtra grupos después de agregar.

## SQL parametrizado y paginación

En Java, los valores de búsqueda pertenecen a un [PreparedStatement](/handbook/java/jdbc/prepared-statement/). Los nombres de columnas y dirección de orden deben elegirse de una lista permitida, no concatenarse desde entrada arbitraria. LIMIT con ORDER BY determinista permite una página de resultados; OFFSET puede ser costoso y sufrir desplazamientos si cambian los datos entre consultas.

## Diagnóstico y comprobación

- Cero resultados: verifica los valores, los filtros, NULL y las coincidencias de JOIN.
- Duplicados aparentes: comprueba cardinalidad y la clave de cada fila.
- Orden variable: añade ORDER BY con desempate.
- Alias desconocido en Java: compara las etiquetas del SELECT con getString/getInt.

Comprueba consultas sin filtro, con filtro inexistente y con dos préstamos del mismo libro. No declares correctos resultados sin compararlos con datos de prueba.

## Conceptos relacionados

- [Restricciones y modificaciones](/handbook/sql/constraints-and-writes/)
- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [TableView](/handbook/java/javafx/table-view/)

## Documentación oficial

- [SELECT en MariaDB](https://mariadb.com/kb/en/select/)
- [JOIN syntax](https://mariadb.com/kb/en/join-syntax/)
- [NULL values](https://mariadb.com/kb/en/null-values/)
