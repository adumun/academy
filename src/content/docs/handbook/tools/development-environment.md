---
title: "Entorno de desarrollo y herramientas"
description: "IDE, editor, compilador, depurador, dependencias y configuración mínima."
---

**Tipo:** explicación y guía práctica. **Nivel:** esencial a complementario.

Un IDE (entorno integrado de desarrollo) agrupa edición, ejecución, navegación y depuración. El editor modifica código fuente; el compilador traduce; el depurador permite detener e inspeccionar un programa. Las herramientas tienen responsabilidades distintas.

Para comenzar con Java basta un JDK compatible, un editor/IDE y un mecanismo de versionado. IntelliJ es una opción, no requisito del lenguaje. No instales servidores, bases de datos o frameworks antes de necesitarlos.

## Checkpoint de entorno

1. Identifica el JDK con java -version y javac -version.
2. Crea una carpeta de proyecto y un archivo Java con main.
3. Compila y ejecuta un programa mínimo.
4. Coloca un breakpoint, observa un valor y continúa.
5. Crea un repositorio y registra un commit coherente.

Si IDE y terminal usan JDK distintos, compara configuración antes de cambiar dependencias. Aprende a leer consola y errores. La [terminal](/handbook/tools/terminal/) ayuda a comprobar el entorno; [Maven](/handbook/tools/maven/) automatiza la construcción cuando el proyecto lo requiere.

## Qué ocurre entre escribir y ejecutar

```text
Editor → archivos fuente → compilador → artefactos → ejecución
                                                  │
                                              depurador
```

El editor no valida por sí solo todo el comportamiento. El compilador comprueba restricciones del lenguaje y genera una representación ejecutable por el entorno correspondiente. El depurador se conecta a la ejecución para observar su estado, sin sustituir las pruebas.

| Herramienta | Pregunta que responde |
|---|---|
| Editor | ¿Qué código estoy escribiendo? |
| Compilador | ¿Es aceptable para el lenguaje y su configuración? |
| Runtime | ¿Puede ejecutarse en este entorno? |
| Depurador | ¿Qué está ocurriendo durante la ejecución? |
| Gestor de build | ¿Cómo repetir la construcción del proyecto? |
| Control de versiones | ¿Qué cambió y cómo se conserva su historial? |

## Experimento: observar el estado

Guarda Main.java y ejecútalo con un JDK compatible:

```java
public class Main {

    public static void main(String[] args) {
        int cantidad = 2;
        int precio = 5;
        int total = cantidad * precio;
        System.out.println(total);
    }
}
```

Coloca un breakpoint en la impresión. Al llegar, comprueba que cantidad vale 2, precio 5 y total 10. Cambia el valor inicial a 3, recompila y repite. Si observas todavía 10, comprueba qué archivo, configuración y artefacto estás ejecutando. La práctica exige un depurador configurado; la salida indicada es esperada.

## Aplicaciones

- [Programas Java y ejecución](/handbook/java/programming/)
- [Diagnóstico](/handbook/troubleshooting/)

## Procedencia didáctica

Adaptación original de [material docente](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/herramientas-para-desarrolladores/README.md), versión fija; los ejemplos son independientes.
