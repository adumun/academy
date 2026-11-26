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

## Una secuencia verificable de decisiones

![Se transforma una necesidad en contrato, algoritmo y prueba. Un resultado distinto del esperado obliga a revisar la hipótesis o la implementación.](/diagrams/problem-solving-cycle.svg)

Antes de elegir clases o estructuras, explicita qué datos llegan, qué representan, cuáles son válidos y qué debe ocurrir ante ausencia o fallo. El algoritmo es una estrategia; el contrato es lo que esa estrategia debe cumplir. Una optimización posterior no debería alterar el resultado esperado.

### Ejemplo trabajado: contar valores dentro de un rango

Contrato: recibe una secuencia de números y límites mínimo y máximo con mínimo menor o igual que máximo; devuelve cuántos elementos están dentro del intervalo inclusivo. Si los límites son incoherentes, rechaza la operación.

```text
si mínimo > máximo:
    rechazar límites inválidos
contador := 0
para cada valor en la secuencia:
    si mínimo <= valor Y valor <= máximo:
        contador := contador + 1
devolver contador
```

Con límites 10 y 20 y secuencia 9, 10, 15, 20, 21, el contador evoluciona 0, 1, 2, 3, 3. Resultado 3.

| Caso | Resultado esperado |
|---|---|
| Secuencia vacía | 0 |
| Solo el mínimo | 1 |
| Solo el máximo | 1 |
| Solo fuera de rango | 0 |
| Mínimo mayor que máximo | Rechazo |

Si la solución devuelve 1 al encontrar la primera coincidencia, resuelve búsqueda y no conteo. Esta diferencia se detecta con el caso de varias coincidencias. Divide en operaciones cuando puedas nombrar responsabilidades sin repetir reglas.

## Conceptos relacionados

- [Programas y algoritmos](/handbook/foundations/programs/)
- [Operaciones y contratos](/handbook/foundations/operations/)
- [Objetos y estado](/handbook/foundations/objects/)
- [Pruebas y evidencia](/handbook/foundations/testing/)
- [Lógica proposicional](/handbook/foundations/boolean-logic/)

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/tips-de-programacion/README.md), versión fija; los ejemplos son independientes.
