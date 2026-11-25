---
title: "Del requerimiento al algoritmo"
description: "Datos, resultado, reglas, descomposición y casos verificables."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

## Antes de escribir código

Un requerimiento describe una necesidad. Un algoritmo explica cómo transformar entradas en resultados respetando reglas. Identifica datos disponibles, salida, restricciones y pasos antes de escoger una sintaxis.

Ejemplo: contar mediciones dentro de un rango. Entradas: secuencia y límites; salida: cantidad; regla: incluir ambos límites; algoritmo: iniciar contador, recorrer, decidir y aumentar cuando corresponda. Prueba secuencia vacía, exactamente el mínimo, exactamente el máximo y fuera de rango.

## Distribuir responsabilidades

Si un bloque reúne demasiadas acciones, identifica operaciones con nombre y contratos. Si el problema requiere objetos, pregunta quién posee el estado y quién protege cada invariante. No agregues clases, capas o patrones si no resuelven una necesidad. Cambia una cosa a la vez al diagnosticar un fallo.

## Comprobación

Escribe casos esperados antes de implementar, resuelve un caso con papel y lápiz y compara con el resultado del programa. Explica por qué existe cada paso y qué alternativa descartaste.

## Conceptos relacionados

- [Programas y algoritmos](/handbook/foundations/programs/)
- [Operaciones y contratos](/handbook/foundations/operations/)
- [Objetos y estado](/handbook/foundations/objects/)
- [Pruebas y evidencia](/handbook/foundations/testing/)
- [Lógica proposicional](/handbook/foundations/boolean-logic/)

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/tips-de-programacion/README.md), versión fija; los ejemplos son independientes.
