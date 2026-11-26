---
title: "Terminal y shell"
description: "Comandos básicos, rutas y ejecución de herramientas en Bash y PowerShell."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

Una terminal permite interactuar con un shell, que interpreta comandos. El directorio actual determina cómo se resuelven rutas relativas. Los comandos y sintaxis pueden variar según el shell.

| Necesidad | Bash | PowerShell |
|---|---|---|
| Directorio actual | pwd | Get-Location |
| Listar | ls | Get-ChildItem |
| Entrar a carpeta | cd proyecto | Set-Location proyecto |
| Ir al padre | cd .. | Set-Location .. |
| Crear carpeta | mkdir prueba | New-Item -ItemType Directory prueba |

Las rutas con espacios se citan. Antes de ejecutar acciones destructivas, verifica ubicación, archivos y permisos. No copies comandos con elevación o eliminación recursiva que no entiendas.

## Prueba local

Crea una carpeta de práctica, entra, lista su contenido y vuelve al padre. Si ya instalaste JDK y Git, prueba:

```bash
java -version
javac -version
git --version
```

PATH contiene ubicaciones donde el sistema busca comandos. Un comando no encontrado puede significar herramienta ausente o configuración de PATH incorrecta. Consulta [sistema de archivos](/handbook/tools/filesystem/) y [entorno de desarrollo](/handbook/tools/development-environment/).

## Comando, argumento y proceso

Un comando identifica un programa o una operación del shell; sus argumentos aportan opciones o valores. El shell interpreta comillas, espacios y otras construcciones antes de ejecutar. El programa recibe argumentos y un entorno de ejecución.

```text
Terminal → shell → programa + argumentos
                      │
                salida y código de retorno
```

### Comparar dos shells

En Bash:

```bash
pwd
mkdir -p "carpeta de prueba"
cd "carpeta de prueba"
ls
```

En PowerShell:

```powershell
Get-Location
New-Item -ItemType Directory -Path "carpeta de prueba"
Set-Location "carpeta de prueba"
Get-ChildItem
```

Ejecuta solo en un directorio de práctica donde esa carpeta no exista previamente. Las comillas conservan el nombre con espacios como argumento. Verifica la ruta resultante antes de realizar otra operación.

En Bash, command -v java muestra cómo se resuelve el comando; en PowerShell, Get-Command java muestra la resolución correspondiente. Un programa puede imprimir mensajes y retornar un código de error; en Bash se consulta con $? y en PowerShell, para ejecutables externos, con $LASTEXITCODE. Un resultado textual no sustituye la comprobación del comportamiento.

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/terminal/README.md), versión fija; los ejemplos son independientes.
