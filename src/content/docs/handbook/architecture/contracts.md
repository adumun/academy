---
title: "Contratos, responsabilidades y dependencias"
description: "Separación de responsabilidades, acoplamiento, invariantes y consumidores."
---

**Tipo:** explicación. **Nivel:** complementario.

## Responsabilidad y contrato

Una responsabilidad es una obligación concreta de una parte del software. Una clase, módulo o servicio debe poseer responsabilidades claras. Un contrato describe qué operaciones ofrece, qué entradas admite, qué resultados produce y cómo comunica fallos. Un consumidor utiliza ese contrato; una implementación realiza el trabajo.

Por ejemplo, un catálogo ofrece buscar por ID. El consumidor no necesita saber si los datos se obtienen de memoria o MariaDB. Si el resultado es [Optional vacío](/handbook/java/optional/), el contrato debe distinguirlo de una [excepción](/handbook/java/exceptions/) de acceso.

## Invariantes y límites

Una invariante es una condición que debe conservarse para que un objeto o proceso permanezca válido. Un título obligatorio puede validarse al crear un Libro y respaldarse con restricciones de base de datos cuando corresponda. Un límite de responsabilidad establece quién protege una regla y quién consume el resultado.

## Acoplamiento y cohesión

El acoplamiento describe cuánto depende una parte de detalles de otra; la cohesión, cuánto se relacionan las responsabilidades dentro de una parte. Depender de una [interfaz](/handbook/java/interfaces/) puede reducir el acoplamiento a una implementación. No elimina todas las dependencias: el consumidor sigue dependiendo de su semántica.

En MVC, la vista presenta datos, el controlador coordina la interacción y el modelo representa información y reglas. DAO concentra acceso a datos. No conviertas estas etiquetas en capas sin propósito ni dupliques las reglas de negocio por cada pantalla.

## Comprobación

Imagina cambiar de almacenamiento en memoria a MariaDB. Identifica qué código debería cambiar, qué contrato debe permanecer y qué casos deben verificarse. Si un cambio de almacenamiento obliga a reescribir todos los controladores, revisa el acoplamiento.

## Conceptos relacionados

- [Interfaces y polimorfismo](/handbook/java/interfaces/)
- [Clases y objetos](/handbook/java/classes-and-objects/)
- [MVC y DAO](/handbook/architecture/)
- [Observer](/handbook/architecture/observer/)

## Referencias

- [Interfaces en Java](https://dev.java/learn/interfaces/)
