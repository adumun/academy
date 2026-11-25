---
title: "Programas, código fuente y ejecución"
description: "Instrucciones, compilador, JVM, JDK, API y bibliotecas."
---

**Tipo:** explicación. **Nivel:** esencial.

## Del problema al programa

Un programa es una secuencia y estructura de instrucciones que un computador puede ejecutar para realizar una tarea. El código fuente es su representación escrita en un lenguaje de programación. Un algoritmo describe un procedimiento para resolver un problema; un programa lo implementa con datos, instrucciones y mecanismos de entrada y salida.

En Java, un archivo .java contiene código fuente. javac es el compilador: comprueba la sintaxis y los tipos y genera archivos .class con bytecode. La JVM (Java Virtual Machine) ejecuta ese bytecode. El JDK (Java Development Kit) proporciona herramientas de desarrollo, incluido javac, además del entorno de ejecución.

```java
public class Hola {

    public static void main(String[] args) {
        System.out.println("Hola");
    }
}
```

Guarda Hola.java, ejecuta javac Hola.java y después java Hola desde la misma carpeta con JDK 21. Salida esperada: Hola. main es el punto de entrada de este programa; System.out.println escribe una línea de texto.

## API, biblioteca, framework y dependencia

| Término | Significado |
| --- | --- |
| API | Contrato de operaciones que otro código puede utilizar |
| Biblioteca | Código reutilizable que un programa invoca |
| Framework | Estructura que proporciona mecanismos y convenciones para construir aplicaciones; puede controlar parte de su ciclo de vida |
| Dependencia | Componente que el proyecto necesita para compilar o ejecutar |
| Runtime | Entorno y recursos que intervienen durante la ejecución |

La API puede existir en una biblioteca local o a través de una comunicación remota. No son sinónimos. [JavaFX](/handbook/java/javafx/) aporta una biblioteca/framework de interfaz gráfica; [JDBC](/handbook/java/jdbc/) define una API de acceso a datos; un driver implementa la comunicación con un motor concreto. [Maven](/handbook/tools/maven/) automatiza tareas de construcción y resolución de dependencias.

## Errores y diagnóstico

Un error de compilación impide generar código válido; un error en ejecución ocurre al ejecutar el programa. Un resultado incorrecto también puede ser un defecto aunque compile y no lance excepciones. Comprueba entrada, salida esperada y salida observada.

## Siguientes conocimientos

- [Variables](/handbook/java/variables/)
- [Tipos](/handbook/java/types/)
- [Expresiones](/handbook/java/expressions/)
- [Control de flujo](/handbook/java/control-flow/)
- [Paquetes y classpath](/handbook/java/packages-and-imports/)

## Documentación oficial

- [Dev.java: Getting Started](https://dev.java/learn/getting-started/)
- [javac, Java 21](https://docs.oracle.com/en/java/javase/21/docs/specs/man/javac.html)
