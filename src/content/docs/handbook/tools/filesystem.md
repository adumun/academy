---
title: "Sistema de archivos y organización de proyectos"
description: "Archivos, rutas, carpetas, extensiones, ZIP y directorio de trabajo."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

Un proyecto existe como archivos y carpetas reales. Una extensión ayuda a identificar el formato; mostrar extensiones evita confundir Main.java con Main.java.txt. Un IDE abre esos archivos y no reemplaza su ubicación.

| Concepto | Significado |
|---|---|
| Ruta absoluta | Ubicación desde una raíz del sistema |
| Ruta relativa | Ubicación respecto al directorio actual |
| Raíz de proyecto | Carpeta donde empieza su estructura |
| Archivo oculto | Entrada que algunas interfaces no muestran por defecto |

Ejemplo de organización:

```text
proyecto/
    src/
    README.md
    .gitignore
```

Extrae un ZIP a una carpeta estable antes de editar. No borres .git para limpiar archivos: contiene historial y configuración. En equipos compartidos comprueba que los commits necesarios llegaron al remoto, cierra sesión y no dejes credenciales. Evita mantener un repositorio activo dentro de un sincronizador sin comprender sus conflictos.

## Comprobación

Abre el proyecto desde el explorador y desde tu IDE. Identifica el mismo archivo y su ruta absoluta. Utiliza la [terminal](/handbook/tools/terminal/) para confirmar el directorio; usa [Git](/handbook/tools/git/) para mantener versiones sin copias final-final.

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/uso-de-pc/README.md), versión fija; los ejemplos son independientes.
