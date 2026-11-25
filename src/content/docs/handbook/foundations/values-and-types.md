---
title: "Valores, variables y tipos"
description: "Diferencia entre dato, valor, nombre, variable, tipo y mutabilidad."
---

**Tipo:** explicación. **Nivel:** esencial. **Alcance:** independiente del lenguaje.

## Datos y valores

Un dato representa información. Un valor es una representación concreta, como 7, un texto o una fecha. Una variable vincula un nombre con un valor o una ubicación de almacenamiento, según las reglas del lenguaje. Asignar permite establecer o modificar ese valor cuando la construcción lo permite.

Un tipo caracteriza qué valores se admiten y qué operaciones tienen sentido sobre ellos. Por ejemplo, sumar dos cantidades numéricas y concatenar dos textos son operaciones diferentes.

## Ejemplo conceptual

```text
cantidad := 3
cantidad := cantidad + 2
nombre := "Sol"
```

Después de la segunda operación, cantidad representa 5. El símbolo := se utiliza aquí como pseudocódigo de asignación; no es una sintaxis universal.

| Concepto | Distinción |
| --- | --- |
| Declaración | Introduce un nombre y, según el lenguaje, su tipo |
| Inicialización | Establece su primer valor |
| Asignación | Establece otro valor cuando está permitido |
| Constante | Nombre o valor que no cambia según el contrato del lenguaje |
| Mutabilidad | Posibilidad de cambiar el estado de un valor u objeto |

Una variable y el objeto al que permite acceder son entidades diferentes. Impedir reasignar el nombre no garantiza que el objeto sea inmutable.

## Tipado y conversión

Algunos lenguajes comprueban tipos antes de ejecutar; otros comprueban operaciones durante la ejecución, o combinan ambos enfoques. Inferir un tipo significa deducirlo a partir del contexto. Convertir un valor significa obtener una representación compatible con otro tipo; puede perder información o fallar.

## Conceptos relacionados

- [Programas y ejecución](/handbook/foundations/programs/)
- [Objetos, estado y comportamiento](/handbook/foundations/objects/)
- [Expresiones y control de flujo](/handbook/foundations/flow/)

## Aplicaciones e implementaciones

- [Variables en Java](/handbook/java/variables/)
- [Tipos en Java](/handbook/java/types/)
- [Referencias y null en Java](/handbook/java/references/)
