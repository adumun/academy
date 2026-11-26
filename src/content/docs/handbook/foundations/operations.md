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

## De la definición a la invocación

![El llamador proporciona los argumentos 3 y 4 a una operación sumar; el contrato especifica parámetros y resultado, y la operación retorna 7.](/diagrams/operation-contract.svg)

Una firma permite identificar la operación mediante los elementos que establece el lenguaje (por ejemplo nombre y parámetros). El contrato es más amplio: incluye el significado de los datos, condiciones válidas, resultado, errores y efectos. Dos operaciones con la misma firma pueden incumplir el mismo contrato de formas distintas.

### Ejemplo de contrato: dividir

| Parte | Especificación |
|---|---|
| Parámetros | dividendo y divisor, números |
| Precondición | divisor distinto de cero |
| Resultado | cociente según la representación numérica acordada |
| Error | informar divisor cero según la política definida |
| Efectos | ninguno |

```text
operación dividir(dividendo, divisor):
    si divisor = 0: devolver error
    devolver dividendo / divisor
```

No es correcto devolver cero para ocultar el fallo: cero puede ser un resultado válido. Una operación que registra datos tiene otro contrato, porque además modifica estado. Una función pura se puede razonar por sus entradas y salida; una operación con efectos también requiere observar el estado previo y posterior.

## Autocomprobación

Define el contrato de buscar un elemento por clave. Distingue resultado encontrado, ausencia y fallo de acceso. ¿Qué prueba permitiría verificar que una consulta no modifica la colección?

## Conceptos relacionados

- [Valores y tipos](/handbook/foundations/values-and-types/)
- [Objetos y comportamiento](/handbook/foundations/objects/)
- [Expresiones y control de flujo](/handbook/foundations/flow/)
- [Contratos y responsabilidades](/handbook/architecture/contracts/)

## Aplicaciones e implementaciones

- [Métodos en Java](/handbook/java/methods/)
- [Interfaces en Java](/handbook/java/interfaces/)
- [Lambdas en Java](/handbook/java/lambdas/)
