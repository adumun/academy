---
title: "Git y GitHub"
description: "Repositorio, staging, commits, remotos, ramas y sincronización."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

Git mantiene historial distribuido de archivos. GitHub aloja repositorios y facilita colaboración; no son sinónimos. Un commit registra un estado coherente; staging selecciona qué se incluye; una rama identifica una línea de trabajo; un remoto es una referencia a otro repositorio.

## Flujo de trabajo

```bash
git status
git diff
git add README.md
git diff --cached
git commit -m "docs: explicar el proyecto"
```

Ejecuta en una copia de práctica con README.md creado y autor configurado. No agregues indiscriminadamente todos los archivos: revisa los cambios y no subas secretos, binarios innecesarios ni datos privados. Para un remoto configurado, git push publica commits; git fetch descarga referencias sin integrar; git pull integra según configuración. Antes de trabajar desde otro equipo, comprueba estado y cambios remotos.

## Ramas y conflictos

```bash
git switch -c feature/documentacion
git status
```

Una rama permite aislar cambios. Un merge integra historias; un conflicto exige decidir qué conservar. Revisa ambas versiones, resuelve marcas, ejecuta pruebas y agrega archivos resueltos antes de completar. No uses force push para solucionar un rechazo que no entiendes.

.gitignore evita que archivos no rastreados coincidentes se añadan por accidente; no elimina del historial archivos ya versionados. Configura identidad por repositorio si trabajas en un equipo compartido. Verifica autor y cuenta remota; cierra sesión al terminar.

## Conceptos relacionados

- [Estrategias de ramas](/handbook/tools/git-branching/)
- [Markdown](/handbook/tools/markdown/)

## Documentación oficial

- [Git](https://git-scm.com/docs)
- [GitHub Docs](https://docs.github.com/)

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/git-github/README.md), versión fija; los ejemplos son independientes.
