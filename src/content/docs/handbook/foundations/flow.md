---
title: "Expresiones, decisiones y repetición"
description: "Evaluación, condiciones, bloques, flujo y algoritmos."
---

**Tipo:** explicación. **Nivel:** esencial. **Alcance:** independiente del lenguaje.

## Evaluación y ejecución

Una expresión representa un cálculo o evaluación que produce un valor según las reglas del lenguaje. Una instrucción ordena una acción. Algunos lenguajes permiten que una expresión también provoque efectos o que una construcción de control produzca un valor.

El flujo de ejecución es el orden en que se evalúan y ejecutan las construcciones. Una condición permite elegir si una acción debe ocurrir.

## Secuencia, decisión y repetición

```text
total := 0
para cada número en una secuencia:
    si número es positivo:
        total := total + número
mostrar total
```

Con la secuencia 2, -1, 4, el total es 6. El algoritmo utiliza una variable acumuladora, una decisión y una repetición.

Una condición de salida impide repetir indefinidamente. El orden de evaluación importa cuando una operación tiene efectos o necesita que otra condición se cumpla primero.

## Cómo se recorre el algoritmo

![El algoritmo lee cada valor, suma solo los positivos y vuelve a revisar si quedan elementos; cuando no quedan, muestra el total.](/diagrams/control-flow.svg)

| Iteración | Número | ¿Positivo? | Total al terminar |
|---|---|---|---|
| Inicial | — | — | 0 |
| 1 | 2 | Sí | 2 |
| 2 | -1 | No | 2 |
| 3 | 4 | Sí | 6 |

Una **expresión** puede calcular un valor sin decidir qué instrucción se ejecutará después. Una **condición** se interpreta para decidir una rama. Un **bloque** agrupa instrucciones bajo una estructura de control. Una **iteración** es una ejecución del cuerpo de una repetición. Las condiciones pueden combinarse mediante [lógica proposicional](/handbook/foundations/boolean-logic/).

## Elegir una repetición

Si se conoce la secuencia, recorre sus elementos. Si la cantidad de repeticiones depende de una condición (por ejemplo, esperar una entrada válida), utiliza un ciclo condicionado. En este segundo caso define cómo se avanza hacia la salida y cómo manejar el fin de entrada. Un ciclo que no modifica ni comprueba lo necesario puede no terminar.

## Autocomprobación

Traza el mismo algoritmo con una secuencia vacía, una de negativos y otra con cero. El cero no satisface la condición «positivo». ¿Qué cambiarías para contar números en vez de sumarlos?

## Conceptos relacionados

- [Programas y algoritmos](/handbook/foundations/programs/)
- [Valores y variables](/handbook/foundations/values-and-types/)
- [Operaciones](/handbook/foundations/operations/)

## Aplicaciones e implementaciones

- [Expresiones y operadores Java](/handbook/java/expressions/)
- [Control de flujo Java](/handbook/java/control-flow/)
