---
title: "Lógica proposicional y condiciones"
description: "Proposiciones, conectores lógicos, tablas de verdad y negaciones."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

Una proposición expresa algo que puede ser verdadero o falso en un contexto. Una condición combina proposiciones para decidir una acción. AND exige ambas, OR exige al menos una y NOT invierte el resultado.

| A | B | A AND B | A OR B |
|---|---|---|---|
| falso | falso | falso | falso |
| falso | verdadero | falso | verdadero |
| verdadero | falso | falso | verdadero |
| verdadero | verdadero | verdadero | verdadero |

## Negación y rangos

Las leyes de De Morgan establecen NOT(A AND B) = NOT A OR NOT B, y NOT(A OR B) = NOT A AND NOT B. Para una medida entre 10 y 20 inclusive, la condición válida exige valor >= 10 AND valor <= 20; su negación es valor < 10 OR valor > 20.

Antes de codificar, redacta la regla, divide proposiciones, agrupa con paréntesis, revisa negación y prueba valores 9, 10, 20 y 21. Distingue un OR inclusivo de una regla que exige exactamente una alternativa.

## Conceptos relacionados

- [Expresiones y control de flujo](/handbook/foundations/flow/)

## Aplicaciones

- [Operadores y cortocircuito Java](/handbook/java/expressions/)

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/logica-proposicional/README.md), versión fija; los ejemplos son independientes.
