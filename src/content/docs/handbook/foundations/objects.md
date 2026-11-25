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

## Conceptos relacionados

- [Valores, variables y tipos](/handbook/foundations/values-and-types/)
- [Operaciones y contratos](/handbook/foundations/operations/)

## Aplicaciones e implementaciones

- [Clases y objetos en Java](/handbook/java/classes-and-objects/)
- [Interfaces y polimorfismo en Java](/handbook/java/interfaces/)
