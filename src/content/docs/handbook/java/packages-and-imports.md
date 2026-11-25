---
title: "Paquetes, imports y classpath"
description: "Organizar clases, resolver nombres, dependencias y recursos."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

## Paquete e import

Un paquete agrupa tipos relacionados y ayuda a evitar colisiones de nombres. Por ejemplo, java.util.List identifica una interfaz del paquete java.util. import permite usar un nombre simple en lugar de repetir el nombre cualificado; no descarga ni instala una biblioteca.

```java
package app;

import java.util.List;

public class PaquetesDemo {

    public static void main(String[] args) {
        List<String> datos = List.of("Java");
        System.out.println(datos);
    }
}
```

En un proyecto Maven, guarda el archivo como src/main/java/app/PaquetesDemo.java. Salida esperada: [Java]. java.lang se importa implícitamente. Cada archivo debe declarar el paquete correspondiente a su organización.

## Classpath, módulo y dependencia

El classpath es el conjunto de ubicaciones desde las que las herramientas Java buscan clases y recursos. Un JAR empaqueta clases y recursos; una dependencia aporta un artefacto que el proyecto necesita. El sistema de módulos Java añade límites explícitos de lectura y acceso; el ejemplo de Academy utiliza un proyecto no modular.

[Maven](/handbook/tools/maven/) resuelve dependencias y configura compilación/ejecución. import javafx.scene.control.TableView no basta si JavaFX no está en las dependencias.

## Recursos

Un recurso como view/libro.fxml no es una clase Java. Maven copia src/main/resources a las salidas del proyecto. La ruta absoluta /view/libro.fxml se resuelve desde el classpath mediante Class.getResource; si no existe, retorna null.

## Diagnóstico

- package does not exist: revisa dependencia, nombre e import.
- cannot find symbol: revisa visibilidad, declaración y dependencias.
- ClassNotFoundException: revisa disponibilidad en ejecución.
- Recurso null: revisa ubicación, ruta y mayúsculas.

## Conceptos relacionados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Maven](/handbook/tools/maven/)
- [FXML y controlador](/handbook/java/javafx/fxml-controller/)
- [Diagnóstico](/handbook/troubleshooting/)

## Documentación oficial

- [JLS 21: Packages and Modules](https://docs.oracle.com/javase/specs/jls/se21/html/jls-7.html)
- [Class.getResource](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Class.html#getResource(java.lang.String))
