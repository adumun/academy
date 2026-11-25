---
title: "Pruebas, criterios de aceptación y evidencia"
description: "Preparar, actuar y comprobar; casos positivos, negativos y límites."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Qué significa probar

Una prueba confronta el comportamiento observado de una operación con una expectativa definida. Un criterio de aceptación expresa un resultado observable requerido. Una prueba manual la realiza una persona; una automatizada ejecuta comprobaciones programadas.

## Preparar, actuar, comprobar

1. Prepara entradas y estado inicial conocidos.
2. Ejecuta una operación identificable.
3. Comprueba el resultado y los cambios de estado esperados.

Por ejemplo, una operación que suma cantidades no negativas debe retornar 5 para 2 y 3, y rechazar una entrada negativa si ese es su contrato. Incluye caso normal, límite y error. Un caso de prueba debe poder repetirse sin depender accidentalmente del resultado de otro.

## Evidencia y límites

| Evidencia | Qué permite afirmar |
|---|---|
| Resultado de una prueba | Qué comportamiento se observó bajo las condiciones definidas |
| Captura de interfaz | Qué mostró la interfaz en ese momento |
| Archivo generado | Qué representación existe en disco |
| Build exitoso | Que el proceso de construcción terminó; revisar cuáles pruebas ejecutó |

Una captura de la interfaz no prueba que un dato sobreviva al reinicio. Un test verde no garantiza ausencia de todos los defectos. Conserva datos de prueba, versión, ejecución y resultado; evita datos personales y secretos.

## Conceptos relacionados

- [Operaciones y contratos](/handbook/foundations/operations/)
- [Persistencia](/handbook/foundations/persistence/)

## Aplicaciones

- [Pruebas Java con JUnit](/handbook/java/junit/)
- [Diagnóstico de errores](/handbook/troubleshooting/)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/puentes-unidad-02/10-pruebas-evidencia-y-junit.md). Los ejemplos no requieren un proyecto de negocio específico.
