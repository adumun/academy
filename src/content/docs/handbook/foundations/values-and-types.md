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

## Qué cambia al asignar

![Una variable cantidad de tipo entero pasa de valor 3 a valor 5 al asignar el resultado de 3 más 2; el nombre y su tipo no cambian en este ejemplo.](/diagrams/variable-value-type.svg)

La declaración introduce un identificador (nombre escrito según las reglas del lenguaje). La inicialización establece el valor inicial. Al reasignar, el lenguaje evalúa primero la expresión de la derecha y almacena o vincula el resultado. En el ejemplo, sumar no modifica por sí solo la variable: la asignación es la operación que establece el nuevo valor.

| Expresión conceptual | Tipo de valor | Qué expresa |
|---|---|---|
| 7 | Entero | Cantidad discreta |
| 7.5 | Decimal o real aproximado | Magnitud con fracción |
| "7" | Texto | Un carácter, no una cantidad |
| verdadero | Booleano | Resultado lógico |

El tipo no es simplemente la apariencia del dato. El texto "7" puede representar un número para una persona, pero el programa debe convertirlo si quiere usarlo como cantidad. La conversión puede fallar (por ejemplo, "siete") o perder precisión. Un tipo estático declarado e inferido sigue siendo estático; la inferencia no equivale a tipado dinámico.

## Valor, referencia y mutabilidad

Algunos valores representan directamente información; otros permiten acceder a un objeto. Dos nombres pueden designar el mismo objeto y observar sus cambios. Un nombre no reasignable no impide modificar un objeto mutable. Una constante de valor y una referencia no reasignable son garantías distintas.

```text
A designa un contador con valor 0
B designa el mismo contador que A
incrementar mediante B
consultar mediante A → 1
```

## Autocomprobación

Distingue declaración, inicialización y asignación en el primer ejemplo. Explica por qué sumar dos cantidades no es lo mismo que unir dos textos, y por qué dos nombres pueden compartir un objeto. Consulta [objetos e identidad](/handbook/foundations/objects/) para profundizar.

## Conceptos relacionados

- [Programas y ejecución](/handbook/foundations/programs/)
- [Objetos, estado y comportamiento](/handbook/foundations/objects/)
- [Expresiones y control de flujo](/handbook/foundations/flow/)

## Aplicaciones e implementaciones

- [Variables en Java](/handbook/java/variables/)
- [Tipos en Java](/handbook/java/types/)
- [Referencias y null en Java](/handbook/java/references/)
