---
title: "Operaciones, funciones y contratos"
description: "Entradas, resultados, efectos, responsabilidades y condiciones."
---

**Tipo:** explicación. **Nivel:** esencial. **Alcance:** independiente del lenguaje.

## Qué es una operación

Una operación describe una acción que puede realizarse. Una función transforma entradas en un resultado según su definición. En programación, una operación puede además modificar estado, producir efectos externos o no devolver un valor. Los lenguajes distinguen funciones, procedimientos y métodos de diferentes maneras.

Los parámetros representan entradas declaradas; los argumentos son los valores suministrados al invocar la operación. Un retorno entrega el resultado al llamador.

```text
operación sumar(primero, segundo):
    devolver primero + segundo
```

Con argumentos 3 y 4, el resultado es 7. Este ejemplo no necesita conocer dónde se obtuvieron ni dónde se mostrarán los números.

## Contrato y efectos

Un contrato especifica qué acepta una operación, qué resultado produce y qué condiciones debe respetar. Una precondición debe cumplirse antes de ejecutar; una postcondición describe lo que se garantiza al finalizar correctamente. Un efecto secundario cambia algo fuera del resultado retornado, como el estado compartido o un archivo.

Una operación pura no altera estado externo observable y produce el mismo resultado para las mismas entradas, según su modelo. No toda operación necesita ser pura; las escrituras requieren efectos deliberados.

## Conceptos relacionados

- [Valores y tipos](/handbook/foundations/values-and-types/)
- [Objetos y comportamiento](/handbook/foundations/objects/)
- [Expresiones y control de flujo](/handbook/foundations/flow/)
- [Contratos y responsabilidades](/handbook/architecture/contracts/)

## Aplicaciones e implementaciones

- [Métodos en Java](/handbook/java/methods/)
- [Interfaces en Java](/handbook/java/interfaces/)
- [Lambdas en Java](/handbook/java/lambdas/)
