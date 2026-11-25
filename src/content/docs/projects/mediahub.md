---
title: "MediaHub"
description: "Proyecto de consola para integrar abstracción, herencia, interfaces y colecciones."
---

**Tipo:** proyecto integrador autónomo. **Entorno:** Java de consola; no requiere JavaFX, Maven ni base de datos. **Resultado:** biblioteca multimedia ejecutable.

## Requerimiento

MediaHub administra libros físicos, películas, ebooks y audiolibros. Todos tienen código, título y creador; cada tipo posee una política de préstamo distinta. Los digitales también pueden descargarse. La aplicación permite registrar, listar, buscar por código, consultar días de préstamo, prestar, devolver, descargar cuando corresponde, controlar entradas inválidas y salir del menú.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Interfaces y polimorfismo](/handbook/java/interfaces/)
- [Colecciones](/handbook/java/collections/)
- [Excepciones](/handbook/java/exceptions/)
- [Paquetes e imports](/handbook/java/packages-and-imports/)

## Responsabilidades

```text
Main → LecturaEntrada (entrada validada)
Main → MediaHub → List<Recurso>
                     ├─ LibroFisico
                     ├─ Pelicula
                     ├─ Ebook (Descargable)
                     └─ Audiolibro (Descargable)
```

MediaHub administra y crea los recursos. El modelo expresa estado y comportamiento. Main solicita operaciones a MediaHub, sin decidir cómo construir cada subtipo. LecturaEntrada se ocupa de Scanner y del reintento de una entrada incorrecta.

## Incremento M01 — Modelar el problema

**Trabajo:** Identifica código, título y creador comunes; diferencia familias de recursos y capacidades. Dibuja dependencias de modelo, MediaHub, LecturaEntrada y Main.

**Checkpoint:** Justifica por qué el menú no crea directamente los objetos concretos.

[Guía original y desarrollo detallado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/01-requerimiento-y-modelado.md).

## Incremento M02 — Construir jerarquía

**Trabajo:** Declara Recurso abstracto y las clases LibroFisico, Pelicula, Ebook y Audiolibro; cada tipo define sus días de préstamo.

**Checkpoint:** La clase base no se instancia; se invoca la política de préstamo mediante Recurso.

[Guía original y desarrollo detallado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/02-clases-abstractas.md).

## Incremento M03 — Expresar capacidad

**Trabajo:** Define Descargable e impleméntala solo en Ebook y Audiolibro. Distingue capacidad de familia.

**Checkpoint:** Los recursos digitales permiten descargar; los físicos no ofrecen esa capacidad.

[Guía original y desarrollo detallado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/03-interfaces.md).

## Incremento M04 — Administrar recursos

**Trabajo:** MediaHub crea objetos concretos y los administra en List<Recurso>; incorpora registro, listado, búsqueda, préstamo y devolución.

**Checkpoint:** Registra tipos distintos, recupera por código y verifica cambios válidos de estado.

[Guía original y desarrollo detallado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/04-listas-y-polimorfismo.md).

## Incremento M05 — Integrar interacción

**Trabajo:** LecturaEntrada concentra Scanner y entradas inválidas; Main coordina el menú hasta salir y captura errores en el límite de interacción.

**Checkpoint:** Una entrada incorrecta no cierra el programa; el usuario puede corregirla.

[Guía original y desarrollo detallado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/05-main-menu-y-excepciones.md).

## Incremento M06 — Comprobar solución

**Trabajo:** Ejecuta registro, búsqueda, días de préstamo, prestar, devolver y descargar. Revisa dependencias y errores.

**Checkpoint:** Compila, permite completar el flujo y rechaza operaciones inválidas sin terminar inesperadamente.

[Guía original y desarrollo detallado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/06-integracion-y-prueba-final.md).

## Pruebas de aceptación

| Escenario | Resultado esperado |
|---|---|
| Registrar cada tipo | Todos se listan por el mismo contrato Recurso |
| Buscar código | Se recupera el recurso que corresponde; ausencia tratada |
| Consultar préstamo | Cada subtipo devuelve su política |
| Prestar y devolver | Cambia el estado solo mediante operaciones válidas |
| Descargar recurso físico | No se ofrece una capacidad que no existe |
| Entrada inválida | Se informa el problema y continúa el menú |

## Implementación de referencia

Consulta el [proyecto completo versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/tree/a28dbca877a9ef6e0105777cae48ba4fa252f10c/ejemplos/semana-07/proyecto-integracion-ea1/) solo después de diseñar tu solución. Importa las fuentes Java del directorio src y ejecuta cl.duoc.dsy1102.mediahub.Main. El [README de referencia](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/ejemplos/semana-07/proyecto-integracion-ea1/README.md) describe los paquetes. Estas pruebas son criterios de aceptación, no evidencia de ejecución nueva.

## Procedencia

Adaptación de la [guía de modelado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/01-requerimiento-y-modelado.md) y la [prueba final](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/06-integracion-y-prueba-final.md) del proyecto integrador EA1. No se presenta como evaluación oficial vigente.
