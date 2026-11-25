---
title: "Secuencias, conjuntos y asociaciones"
description: "Estructuras de datos, índice, unicidad, clave y criterio de elección."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Qué es una estructura de datos

Una estructura de datos organiza valores para poder realizar operaciones como guardar, recorrer, encontrar o eliminar. La elección depende de las operaciones y restricciones, no del dominio del ejemplo.

| Estructura abstracta | Característica |
|---|---|
| Secuencia | Elementos ordenados por posición, admite repeticiones según contrato |
| Conjunto | Elementos únicos según una relación de igualdad |
| Asociación clave–valor | Cada clave identifica como máximo un valor asociado |

Un índice indica una posición; una clave identifica una asociación. La identidad y la igualdad determinan qué significa «el mismo» elemento. No confundas el orden de recorrido de una implementación con el contrato abstracto.

## Ejemplo conceptual

Una secuencia de mediciones puede contener 10, 10, 12. Un conjunto de mediciones distintas contiene 10 y 12. Una asociación etiqueta–medición puede tener A → 10 y B → 10; los valores se repiten, las claves no.

## Elegir y comprobar

Si buscas conservar el orden de llegada, una secuencia es apropiada. Si necesitas evitar repeticiones, considera un conjunto. Si consultas por identificador, usa una asociación. Las implementaciones tienen costes y reglas distintas; antes de duplicar estructuras, define quién mantiene su coherencia.

## Conceptos relacionados

- [Valores y tipos](/handbook/foundations/values-and-types/)
- [Objetos e identidad](/handbook/foundations/objects/)

## Aplicaciones

- [Arrays en Java](/handbook/java/arrays/)
- [List en Java](/handbook/java/collections/)
- [Set en Java](/handbook/java/sets/)
- [Map en Java](/handbook/java/maps/)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-08/03-elegir-list-set-map.md); los ejemplos de esta ficha son independientes.
