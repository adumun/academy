---
title: "Contratos, responsabilidades y dependencias"
description: "Separación de responsabilidades, acoplamiento, invariantes y consumidores."
---

**Tipo:** explicación. **Nivel:** complementario.

## Responsabilidad y contrato

Los conceptos generales de [operaciones y contratos](/handbook/foundations/operations/) y [encapsulamiento](/handbook/foundations/objects/) son el punto de partida. Una responsabilidad no depende de un lenguaje ni de un tipo de almacenamiento.

Una responsabilidad es una obligación concreta de una parte del software. Una clase, módulo o servicio debe poseer responsabilidades claras. Un contrato describe qué operaciones ofrece, qué entradas admite, qué resultados produce y cómo comunica fallos. Un consumidor utiliza ese contrato; una implementación realiza el trabajo.

Por ejemplo, un servicio de cálculo ofrece convertir una unidad de medida. El consumidor no necesita conocer el algoritmo interno. Un resultado inexistente y un fallo al realizar la operación deben tener significados distintos.

## Invariantes y límites

Una invariante es una condición que debe conservarse para que un objeto o proceso permanezca válido. Una temperatura absoluta no puede ser menor que cero kelvin; el componente responsable debe proteger esa condición. Un límite de responsabilidad establece quién protege una regla y quién consume el resultado.

## Acoplamiento y cohesión

El acoplamiento describe cuánto depende una parte de detalles de otra; la cohesión, cuánto se relacionan las responsabilidades dentro de una parte. Depender de una [interfaz](/handbook/java/interfaces/) puede reducir el acoplamiento a una implementación. No elimina todas las dependencias: el consumidor sigue dependiendo de su semántica.

En MVC, la vista presenta datos, el controlador coordina la interacción y el modelo representa información y reglas. DAO concentra acceso a datos. No conviertas estas etiquetas en capas sin propósito ni dupliques las reglas de negocio por cada pantalla.

## Comprobación

Imagina sustituir el algoritmo de conversión de unidades por otro. Identifica qué código debería cambiar, qué contrato debe permanecer y qué casos deben verificarse. Si el cambio obliga a reescribir todos los consumidores, revisa el acoplamiento.

## Aplicaciones e implementaciones

- [Interfaces Java](/handbook/java/interfaces/): contratos expresados en un lenguaje concreto.
- [MVC y DAO](/handbook/architecture/): aplicación al diseño de interfaces y persistencia.

## Conceptos relacionados

- [Interfaces y polimorfismo](/handbook/java/interfaces/)
- [Clases y objetos](/handbook/java/classes-and-objects/)
- [MVC y DAO](/handbook/architecture/)
- [Observer](/handbook/architecture/observer/)

## Referencias

- [Interfaces en Java](https://dev.java/learn/interfaces/)
