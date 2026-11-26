---
title: "Paradigmas de programación"
description: "Modelos para organizar soluciones: imperativo, procedural, orientado a objetos y funcional."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Qué es un paradigma

Un paradigma es una forma de organizar el razonamiento y las construcciones de un [programa](/handbook/foundations/programs/). No es un lenguaje ni una biblioteca. Un lenguaje puede admitir varios paradigmas.

| Enfoque | Idea central |
|---|---|
| Imperativo | Cambiar estado mediante instrucciones ordenadas |
| Procedural | Agrupar instrucciones en operaciones reutilizables |
| Orientado a objetos | Distribuir estado y comportamiento entre objetos que colaboran |
| Funcional | Componer funciones y transformar valores, favoreciendo funciones puras |
| Declarativo | Expresar el resultado o las restricciones deseadas sin indicar cada paso |

Un programa puede calcular un promedio mediante un acumulador, mediante una función que recibe una secuencia o mediante un objeto que administra mediciones. El resultado puede coincidir aunque la organización sea diferente.

## Cuándo elegir

Una operación puntual puede ser más clara como función. Un sistema con objetos que conservan estado, identidad y reglas puede beneficiarse de POO. No conviertas cada operación en una clase por obligación, ni concentres todas las responsabilidades en una única función. El diseño responde al problema.

## Comprobación

Describe el algoritmo para contar valores positivos, primero como instrucciones secuenciales y luego como operación reutilizable. Identifica qué datos entran, qué sale y qué estado cambia. Ninguna descripción requiere aún una sintaxis específica.

## Un mismo problema, cuatro enfoques

El objetivo es contar positivos en 2, -1 y 4. Todos los enfoques deben producir 2; cambia la organización del cálculo.

```text
imperativo:
    contador := 0
    para cada valor: si valor > 0: contador := contador + 1

procedural:
    resultado := contarPositivos(valores)

orientado a objetos:
    registro := crear RegistroDeMediciones(valores)
    resultado := registro.contarPositivos()

funcional:
    resultado := contar(filtrar(valores, esPositivo))
```

Son representaciones conceptuales. La versión procedural puede contener el mismo ciclo imperativo; la OO puede delegar a una función; la funcional puede utilizar una implementación interna imperativa. Los paradigmas no son categorías excluyentes ni definen una sintaxis universal.

Un enfoque declarativo expresaría la selección de valores positivos y su conteo sin indicar el procedimiento de recorrido. La ventaja es comunicar la intención; el costo es depender de las garantías y capacidades del motor que ejecuta esa declaración.

## Cómo comparar

Pregúntate quién controla el estado, dónde está la regla, cómo se prueba y qué cambia cuando cambia el requerimiento. Para un cálculo puro, no hace falta inventar una entidad con identidad. Para varios objetos con estado y reglas, encapsular responsabilidades puede reducir errores. La selección es una decisión de diseño, no una competencia entre lenguajes.

## Conceptos relacionados

- [Objetos, estado y comportamiento](/handbook/foundations/objects/)
- [Operaciones y contratos](/handbook/foundations/operations/)
- [Expresiones y control de flujo](/handbook/foundations/flow/)

## Aplicaciones

- [Programas en Java](/handbook/java/programming/). Un lenguaje con clases también permite escribir algoritmos procedurales.

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-01/CLASE-01/01-paradigma-y-forma-de-pensar.md); los ejemplos de esta ficha son independientes.
