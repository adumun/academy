---
title: "JSON: formato de datos"
description: "Objetos, arreglos, valores, sintaxis, estructura y validación."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Qué representa JSON

JSON (JavaScript Object Notation) es un formato textual de intercambio de datos. No es un lenguaje de programación; no necesitas conocer JavaScript para utilizarlo. Un documento representa un valor que puede ser objeto, arreglo, número, texto, booleano o null.

```json
{
  "sensor": "A",
  "valor": 21.5,
  "activo": true,
  "unidad": null,
  "mediciones": [20, 21, 22],
  "origen": {"zona": "norte"}
}
```

Un objeto contiene pares nombre–valor; un arreglo contiene valores ordenados. Los nombres de propiedades y cadenas usan comillas dobles. Las claves deben ser únicas para interoperabilidad: la especificación recomienda unicidad y los procesadores pueden tratar duplicados de manera distinta. JSON estándar no admite comentarios, comas finales, NaN ni Infinity.

## Tres niveles de validación

| Pregunta | Ejemplo |
|---|---|
| ¿Sintaxis bien formada? | Una coma final es inválida |
| ¿Estructura esperada? | Un arreglo donde se espera un objeto no cumple el contrato |
| ¿Datos válidos? | Una medición negativa puede incumplir una regla aunque sea JSON válido |

No confundas ausencia explícita (null), propiedad ausente, arreglo vacío y documento corrupto. Serializar es transformar un valor en una representación; deserializar es reconstruir datos desde esa representación. Guardar texto en disco es otra operación.

## Comprobación

Evalúa `[]`, `{"valor":-1}` y `{"valor":1,}`. Los dos primeros son JSON bien formado; su validez depende del contrato y las reglas. El tercero tiene un error sintáctico.

## De la estructura al texto y de vuelta

![Un registro con sensor A y valor 21 se serializa a un documento JSON; al deserializar se reconstruyen valores que después deben validarse.](/diagrams/json-representation.svg)

JSON representa datos mediante una gramática de texto. El objeto JSON no es una instancia ejecutable con métodos; sus nombres son cadenas. Un arreglo puede contener objetos, otros arreglos o valores escalares, aunque el contrato de una aplicación puede restringirlos a un tipo uniforme.

### Lectura por niveles

```json
{
  "mediciones": [
    {"sensor": "A", "valor": 21},
    {"sensor": "B", "valor": null}
  ]
}
```

La raíz es un objeto con una propiedad mediciones. Su valor es un arreglo de dos objetos; el segundo declara ausencia explícita de valor. La propiedad omitida puede tener otra semántica, por ejemplo «no se recibió». No presupongas que todos los parsers interpretan números con igual precisión. Las cadenas pueden contener comillas escapadas como \\"; los separadores y espacios fuera de cadenas no cambian su estructura.

| Documento | ¿Bien formado? | Observación |
|---|---|---|
| {"valor":21} | Sí | Valor numérico |
| {"valor":"21"} | Sí | Valor textual; requiere conversión si se espera número |
| {"valor":null} | Sí | Ausencia explícita |
| {} | Sí | Propiedad ausente |
| {"valor":21,} | No | Coma final |

## Autocomprobación

Diseña una representación de dos puntos con coordenadas y un campo opcional. Indica cuál estructura se espera, cómo tratar un campo ausente y qué comprobar después de analizar la sintaxis.

## Conceptos relacionados

- [Valores y tipos](/handbook/foundations/values-and-types/)
- [Estructuras de datos](/handbook/foundations/data-structures/)
- [Persistencia](/handbook/foundations/persistence/)

## Aplicaciones

- [JSON en Java con Jackson y Gson](/handbook/java/json-serialization/)
- [Detective JSON](https://academic-labs.adumun.cl/activities/detective-json/)

## Fuente primaria

- [RFC 8259 — JSON](https://www.rfc-editor.org/rfc/rfc8259)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/puentes-unidad-02/02-json-desde-cero.md). Los ejemplos no requieren un proyecto de negocio específico.
