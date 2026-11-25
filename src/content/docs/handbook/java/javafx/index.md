---
title: "JavaFX"
description: "Arquitectura de una aplicación de escritorio, ciclo de vida, controles y eventos."
---

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Genéricos](/handbook/java/generics/)
- [Colecciones](/handbook/java/collections/)
- [Lambdas](/handbook/java/lambdas/)

## Modelo mental

JavaFX es una biblioteca y framework de interfaz gráfica (consulta [programas, API y frameworks](/handbook/java/programming/)). Una interfaz gráfica (UI) es la parte de una aplicación mediante la que el usuario interactúa con botones, campos y otros elementos. Un nodo es un objeto visual del árbol; un árbol organiza elementos padre e hijos, de modo que cada contenedor puede agrupar otros nodos. JavaFX construye interfaces gráficas mediante ese árbol. Application gestiona el ciclo de vida; Stage es una ventana; Scene contiene el árbol de controles; un contenedor como VBox organiza sus hijos.

| Concepto | Responsabilidad |
| --- | --- |
| Application | Inicializar y ejecutar la aplicación |
| Stage | Representar una ventana |
| Scene | Contener el árbol de nodos |
| Parent | Agrupar nodos |
| Control | Recibir entrada o presentar datos |
| FXML | Declarar estructura visual |
| Controller | Coordinar interacción y comportamiento |

## Aplicación mínima completa

Archivo app/Main.java. Requiere el proyecto Maven no modular de la ficha Maven.

```java
package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label saludo = new Label("Hola, JavaFX");
        VBox root = new VBox(12, saludo);
        Scene scene = new Scene(root, 480, 320);

        stage.setTitle("Primera ventana");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Ejecuta mvn javafx:run. Debe aparecer una ventana de 480 × 320 con el texto. Application.launch inicia el runtime; start se ejecuta en el hilo de aplicación JavaFX.

## FXML frente a Java

Ambos permiten construir el árbol visual. FXML facilita separar la estructura de los eventos. Scene Builder es un editor opcional de FXML, no un requisito para ejecutar la aplicación.

## Flujo de una aplicación de datos

1. El usuario introduce datos o selecciona una fila.
2. El controlador recibe un evento y valida entradas.
3. El DAO realiza la operación JDBC.
4. El controlador comunica el resultado y refresca los datos.
5. La base conserva los cambios confirmados.

## Hilo de interfaz

Un hilo de ejecución es una secuencia de instrucciones que puede avanzar independientemente de otras. El hilo JavaFX procesa eventos y actualiza la interfaz; una operación bloqueante espera un resultado antes de continuar. Un acceso JDBC lento dentro de un [evento](/handbook/java/javafx/events/) puede congelar temporalmente la ventana. Para comenzar se pueden usar operaciones locales y rápidas. Como ampliación, Task o Service permiten hacer trabajo bloqueante fuera del hilo JavaFX; los controles deben actualizarse desde el hilo de interfaz. No es necesario agregar concurrencia al primer laboratorio si no se solicita.

## Diagnóstico inicial

- La aplicación no abre: verifica Main, dependencias y plugin Maven.
- Ventana vacía: comprueba Scene, raíz y show().
- FXML no carga: revisa ruta, controlador e imports XML.
- Interfaz congelada: mide duración de la tarea en el evento.

## Conceptos relacionados

- [Controles de formulario](/handbook/java/javafx/controls/)
- [Properties y binding](/handbook/java/javafx/properties-and-binding/)

- [ObservableList](/handbook/java/javafx/observable-list/)
- [Eventos y listeners](/handbook/java/javafx/events/)
- [Patrón Observer](/handbook/architecture/observer/)

- [Maven](/handbook/tools/maven/)
- [FXML y Controller](/handbook/java/javafx/fxml-controller/)
- [TableView](/handbook/java/javafx/table-view/)
- [Validaciones](/handbook/java/javafx/validation/)
- [MVC y DAO](/handbook/architecture/)

## Documentación oficial

- [Guía OpenJFX](https://openjfx.io/openjfx-docs/)
- [Application](https://openjfx.io/javadoc/21/javafx.graphics/javafx/application/Application.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
