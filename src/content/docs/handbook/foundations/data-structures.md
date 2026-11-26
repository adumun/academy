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

## Modelo visual

![La secuencia conserva tres posiciones con valores 10, 10 y 12. El conjunto contiene solo 10 y 12. El mapa asocia A con 10, B con 10 y C con 12.](/diagrams/data-structures.svg)

| Operación | Secuencia | Conjunto | Asociación |
|---|---|---|---|
| Agregar un valor ya presente | Puede conservar otra ocurrencia | No genera otro elemento igual | Requiere decidir qué hacer con la clave |
| Recuperar posición | Según el contrato | No forma parte del contrato general | Se recupera por clave |
| Recorrer | Orden de la secuencia | Orden depende de implementación | Orden depende de implementación |

La estructura abstracta describe las operaciones admitidas; la implementación decide la representación interna (por ejemplo, almacenamiento contiguo, nodos enlazados o una tabla hash). Las estructuras tienen distintos costos de acceso, inserción y búsqueda. No se deduce rendimiento de su nombre solamente. En una tabla hash, una función hash ayuda a ubicar candidatos; colisiones pueden ocurrir y se necesita comprobar igualdad.

## Ejemplo de decisión

Para un registro de llegada, importa el orden y una secuencia conserva todas las observaciones. Para comprobar qué etiquetas aparecieron, un conjunto elimina duplicados. Para obtener la última medición de cada etiqueta, una asociación permite actualizar el valor vinculado a cada clave. Una clave repetida no equivale a un valor repetido.

## Autocomprobación

Elige una estructura para una cola de atención, un conjunto de códigos autorizados y un inventario consultado por código. Explica qué operación es esencial, cómo tratarías duplicados y qué orden necesitas.

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
