---
title: "Programas, algoritmos y ejecución"
description: "Conceptos generales de instrucciones, datos, algoritmos y ejecución."
---

**Tipo:** explicación. **Nivel:** esencial. **Alcance:** independiente del lenguaje.

## Qué es un programa

Un programa expresa instrucciones y estructuras de datos mediante un lenguaje de programación para realizar una tarea. Un algoritmo describe un procedimiento para resolver un problema; puede expresarse sin elegir todavía un lenguaje. Una implementación convierte ese procedimiento en código que un entorno puede ejecutar.

Por ejemplo, calcular el promedio de tres mediciones requiere recibir los valores, sumarlos, dividir por tres y entregar el resultado. Ese procedimiento no depende de una interfaz gráfica ni de una base de datos.

## Código, ejecución y herramientas

| Término | Significado |
| --- | --- |
| Código fuente | Representación del programa escrita en un lenguaje |
| Sintaxis | Reglas de forma del lenguaje |
| Semántica | Significado de las construcciones |
| Compilador | Traduce código a otra representación, verificando las reglas que correspondan |
| Intérprete | Ejecuta o evalúa instrucciones según su entorno |
| Runtime | Entorno que permite ejecutar el programa |

Un mismo sistema puede combinar compilación e interpretación. Un error de sintaxis, un error durante la ejecución y un resultado incorrecto son problemas distintos.

## Ejemplo conceptual

```text
recibir tres mediciones
calcular su suma
dividir la suma por tres
mostrar el promedio
```

Es pseudocódigo: expresa el procedimiento sin exigir una sintaxis ejecutable concreta.

## De un requerimiento a una ejecución

![El problema se expresa como algoritmo; el código implementa el algoritmo y el entorno lo ejecuta con entradas para producir resultados.](/diagrams/program-execution.svg)

Un **requerimiento** describe el comportamiento esperado, no necesariamente los pasos para lograrlo. El algoritmo define los pasos y su condición de término. El código fuente expresa esos pasos con las reglas de un lenguaje; el entorno de ejecución materializa las operaciones. Un mismo algoritmo puede tener varias implementaciones y un programa puede utilizar muchos algoritmos.

### Ejemplo razonado: máximo de una secuencia

Entrada: una secuencia no vacía. Salida: su mayor valor. Restricción: si no hay elementos, informar que no hay máximo; no inventar cero.

```text
si la secuencia está vacía:
    informar ausencia de máximo
si no:
    máximo := primer elemento
    para cada elemento restante:
        si elemento > máximo:
            máximo := elemento
    devolver máximo
```

| Paso | Elemento | Máximo |
|---|---|---|
| Inicial | -5 | -5 |
| Comparar | -2 | -2 |
| Comparar | -8 | -2 |

El resultado es -2. Inicializar el máximo en cero daría un valor incorrecto para esta entrada. La traza permite comprobar el algoritmo sin depender del lenguaje.

## Cómo distinguir errores

Una instrucción escrita con sintaxis inválida puede rechazarse antes de ejecutar. Dividir por cero puede provocar un fallo o un resultado especial, dependiendo del tipo y lenguaje. Calcular un máximo incorrecto es un error lógico aunque el programa ejecute. Para reproducir un fallo conserva entrada, resultado esperado y observado.

## Autocomprobación

Escribe el algoritmo para encontrar el mínimo y trázalo con 8, 3, 6. ¿Qué ocurre con una secuencia vacía? ¿Qué parte cambia al usar un lenguaje distinto?

## Conceptos relacionados

- [Valores, variables y tipos](/handbook/foundations/values-and-types/)
- [Expresiones y control de flujo](/handbook/foundations/flow/)
- [Operaciones y contratos](/handbook/foundations/operations/)

## Aplicaciones e implementaciones

- [Programas y ejecución en Java](/handbook/java/programming/)
