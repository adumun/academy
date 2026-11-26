---
title: "Abstracción, relaciones y polimorfismo"
description: "Generalización, especialización, asociación, composición y capacidades."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Abstracción

Abstraer es elegir los detalles relevantes para un propósito e ignorar los que no intervienen. Una abstracción de sensor podría definir leer una magnitud, sin indicar el circuito interno. Un contrato expresa lo que un consumidor puede esperar.

## Relaciones entre objetos y tipos

| Relación | Modelo mental |
|---|---|
| Generalización/especialización | Una categoría específica satisface el contrato de una general |
| Asociación | Un objeto conoce o colabora con otro |
| Composición | Un todo posee partes cuyo ciclo de vida está ligado al todo |
| Agregación | Un todo agrupa partes que pueden existir independientemente |
| Capacidad | Un objeto ofrece una operación, sin exigir una familia única |

Una referencia a otro objeto no implica composición fuerte automáticamente: importa la propiedad y el ciclo de vida. Un motor puede pertenecer a un dispositivo; una persona que lo utiliza existe de forma independiente.

## Polimorfismo y sustitución

Polimorfismo permite tratar alternativas bajo un mismo contrato y obtener el comportamiento correspondiente. Dos tipos de sensor pueden implementar la misma operación de lectura de modos diferentes. Sustituir un tipo no debe romper lo que espera el consumidor; compartir un nombre o firma no garantiza igualdad semántica.

## Comprobación

Modela un dibujo con figuras y colores. ¿Una figura es un color o tiene un color? ¿Qué comportamiento podría generalizarse para todas las figuras? ¿Qué operación requiere un tipo concreto? Justifica cada relación por su significado, no por ahorrar código.

## Visualizar las relaciones

![Sensor térmico y sensor de luz son tipos especializados de Sensor; un dispositivo usa un sensor por colaboración, sin implicar necesariamente propiedad exclusiva.](/diagrams/object-relationships.svg)

La relación «es un» expresa sustitución de tipos, no simplemente coincidencia de atributos. «Usa» o «tiene un» expresa colaboración y no impone automáticamente el ciclo de vida. En composición fuerte las partes pertenecen al todo según el modelo; en agregación o asociación pueden existir independientemente. No toda relación necesita representarse mediante una clase base.

## Ejemplo de sustitución

```text
contrato Sensor: leer() devuelve una magnitud numérica
SensorTérmico: leer() obtiene una temperatura
SensorLuz: leer() obtiene intensidad luminosa
```

Si el consumidor necesita comparar temperaturas, el contrato genérico es insuficiente: debe incorporar unidad o limitar el tipo admitido. La misma firma no garantiza compatibilidad semántica. Si solo necesita registrar una lectura con magnitud y unidad explícitas, ambos pueden satisfacer un contrato común. Una capacidad como calibrar puede existir en familias diferentes sin exigir herencia entre ellas.

## Decisión de diseño

| Pregunta | Señal |
|---|---|
| ¿Un tipo puede sustituir al otro sin romper el contrato? | Especialización |
| ¿Un objeto delega una tarea a otro? | Colaboración |
| ¿La parte pertenece exclusivamente al todo y depende de su ciclo de vida? | Composición |
| ¿Tipos no relacionados comparten una operación? | Capacidad/contrato |

No elijas herencia solo para ahorrar código. Es más importante proteger el significado de los tipos y las reglas del problema.

## Conceptos relacionados

- [Objetos](/handbook/foundations/objects/)
- [Operaciones y contratos](/handbook/foundations/operations/)

## Aplicaciones

- [Herencia y despacho dinámico en Java](/handbook/java/inheritance/)
- [Colaboración entre objetos en Java](/handbook/java/composition/)
- [Clases abstractas en Java](/handbook/java/abstract-classes/)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-04/02-composicion-introductoria.md); los ejemplos de esta ficha son independientes.
