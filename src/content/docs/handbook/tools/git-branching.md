---
title: "Estrategias de ramas y GitFlow"
description: "Rama principal, integración, features, releases y elección del flujo."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

Una estrategia de ramas define cómo aislar e integrar cambios. No es una obligación del lenguaje ni de Git. Una rama principal estable y ramas breves de trabajo pueden ser suficientes. GitFlow es una estrategia específica que distingue integración en develop, ramas de feature, releases y hotfixes; una variante simplificada omite ramas que no necesita.

| Rama | Propósito típico |
|---|---|
| main / master | Versión estable |
| develop | Integración previa a release (si existe) |
| feature/* | Cambio aislado |
| release/* | Preparar versión (si se usa) |
| hotfix/* | Corrección urgente de versión estable (si se usa) |

## Ejercicio seguro

En un repositorio local de práctica, confirma rama y estado; crea feature/documentacion desde la base acordada, modifica README, haz commit y vuelve a la rama de integración. Revisa el diff y fusiona según el flujo autorizado. En colaboración real, utiliza Pull Request y controles acordados. No asumas que todas las organizaciones usan develop o GitFlow.

## Conceptos relacionados

- [Git y GitHub](/handbook/tools/git/)

## Referencias

- [Git branches](https://git-scm.com/book/en/v2/Git-Branching-Branches-in-a-Nutshell)

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/gitflow/README.md), versión fija; los ejemplos son independientes.
