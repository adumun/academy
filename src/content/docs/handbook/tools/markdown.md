---
title: "Markdown y documentación de código"
description: "Títulos, listas, enlaces, bloques, tablas y README reproducible."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

Markdown es un lenguaje de marcado ligero para estructurar texto. No ejecuta el código que contiene. Algunos dialectos, como GitHub Flavored Markdown, añaden tablas y listas de tareas.

````markdown
# Proyecto
## Objetivo
Explica el problema.
- Requisito 1
- Requisito 2

[Documentación](https://docs.oracle.com/)

```java
System.out.println("Hola");
```
````

Un README útil declara propósito, requisitos, pasos para ejecutar, resultado esperado y pruebas. Utiliza títulos descriptivos, rutas relativas cuando corresponda y lenguaje claro. El código debe tener un bloque identificado por lenguaje; una captura complementa, no reemplaza una explicación.

## Comprobación

Crea README.md, añade un título, una lista, enlace y bloque de código. Previsualiza el documento y abre el enlace. Comprueba que otra persona puede seguir los pasos. Consulta [Git](/handbook/tools/git/) para versionarlo.

## Estructura y significado del marcado

Markdown permite que un lector interprete convenciones de texto como títulos, listas, enlaces y énfasis. El archivo fuente sigue siendo texto plano. El renderizador transforma esa estructura en una presentación; distintas plataformas pueden soportar extensiones diferentes.

| Sintaxis | Resultado |
|---|---|
| # Título | Encabezado principal |
| ## Sección | Encabezado subordinado |
| `**texto**` | Énfasis fuerte |
| `codigo` | Código en línea |
| `[texto](ruta)` | Enlace |
| `![descripción](ruta)` | Imagen con texto alternativo |

### Documentar una ejecución

````markdown
## Ejecutar

1. Verificar los requisitos.
2. Compilar el programa.
3. Ejecutar con la entrada de ejemplo.

```text
Entrada: 2 y 3
Salida esperada: 5
```

## Validación
- [ ] Ejecutar el caso normal.
- [ ] Comprobar el caso inválido.
````

La salida esperada expresa un criterio, no una prueba realizada. Una imagen necesita descripción significativa, y el texto debe explicar su propósito. Comprueba enlaces relativos desde el lugar donde está el documento, no desde cualquier carpeta. No uses títulos solo para hacer texto más grande.

## Referencias

- [CommonMark](https://commonmark.org/)
- [GitHub Flavored Markdown](https://github.github.com/gfm/)

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/markdown/README.md), versión fija; los ejemplos son independientes.
