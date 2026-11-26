---
title: "Objetos, estado y comportamiento"
description: "Conceptos de POO independientes de clases y sintaxis específicas."
---

**Tipo:** explicación. **Nivel:** esencial. **Alcance:** independiente del lenguaje.

## Objeto y estado

En programación orientada a objetos, un objeto reúne información y operaciones. Su estado es el conjunto de valores que lo caracterizan en un momento determinado. Su comportamiento es el conjunto de operaciones y respuestas que ofrece.

Un contador puede tener estado 0 y comportamiento incrementar y consultar. Tras incrementar, su estado pasa a 1. No necesita conocer cómo se muestra o almacena ese valor.

## Clase, instancia e identidad

En lenguajes orientados a clases, una clase define la estructura y el comportamiento de sus instancias. Una instancia es un objeto concreto. No todos los modelos de objetos necesitan clases: también existen modelos basados en prototipos.

Dos objetos pueden tener el mismo estado sin ser el mismo objeto. La identidad distingue instancias; la igualdad lógica depende de las reglas que se definan.

## Encapsulamiento e invariantes

Encapsular significa proteger los detalles internos y exponer operaciones que conservan un contrato. Una invariante es una condición que debe mantenerse válida. Un contador que no puede ser negativo debe rechazar o prevenir operaciones que lo dejarían bajo cero.

| Concepto | Ejemplo |
| --- | --- |
| Estado | Valor actual del contador |
| Comportamiento | Incrementar, consultar |
| Identidad | Un contador concreto, distinto de otro |
| Encapsulamiento | Cambiar su valor solo mediante operaciones válidas |

El encapsulamiento no consiste en agregar getters y setters indiscriminadamente; depende de qué cambios deben permitirse.

## Cómo observar un objeto

![Dos contadores distintos comienzan con valor cero. Incrementar solo el contador A cambia su valor a uno y conserva el estado del contador B.](/diagrams/object-state-identity.svg)

El estado puede incluir valores propios y relaciones con otros objetos. Una consulta devuelve información sin necesariamente modificar el estado; una operación de cambio establece un nuevo estado. En el ejemplo hay dos identidades: cambiar A no debe modificar B solo por tener inicialmente el mismo valor. Si dos referencias acceden a A, ambas pueden observar el cambio de A.

## Contrato y protección del estado

```text
contador:
    estado privado: valor = 0
    operación incrementar(): valor := valor + 1
    operación decrementar():
        si valor = 0: rechazar sin cambiar el estado
        si no: valor := valor - 1
    consulta obtenerValor(): devolver valor
```

Es pseudocódigo, no una sintaxis de clase de un lenguaje concreto. El contrato define el resultado de cada operación. La invariante valor >= 0 debe cumplirse después de construir el objeto y después de cada operación pública; no basta comprobarla en la pantalla. La política de rechazo puede expresarse mediante un resultado de error o mecanismo equivalente del lenguaje.

| Prueba | Estado antes | Estado después |
|---|---|---|
| Incrementar | 0 | 1 |
| Decrementar | 1 | 0 |
| Decrementar de nuevo | 0 | 0, rechazo |

## Diferencias que importan

Abstracción decide qué detalles exponer para un propósito; encapsulamiento organiza el acceso a esos detalles para mantener el contrato. Un objeto no es necesariamente una fila de base de datos ni una entidad con identificador de negocio. Un objeto inmutable puede ofrecer comportamiento sin cambiar su estado, por ejemplo una operación que produce un nuevo valor.

## Autocomprobación

Diseña el contrato de un interruptor: estado, consultas, acciones y una regla válida. ¿Necesita un setter arbitrario? ¿Dos interruptores encendidos son el mismo objeto?

## Conceptos relacionados

- [Valores, variables y tipos](/handbook/foundations/values-and-types/)
- [Operaciones y contratos](/handbook/foundations/operations/)

## Aplicaciones e implementaciones

- [Clases y objetos en Java](/handbook/java/classes-and-objects/)
- [Interfaces y polimorfismo en Java](/handbook/java/interfaces/)
