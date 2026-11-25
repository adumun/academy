---
title: "Filtrar y ordenar una TableView"
description: "Guía práctica con ObservableList, FilteredList y SortedList."
---

**Tipo:** guía práctica. **Nivel:** complementario. **Versión:** JavaFX 21.

## Objetivo y requisitos

Crear una tabla que permita buscar títulos mientras el usuario escribe y ordenar pulsando sus encabezados, sin eliminar registros de la colección original.

Usa el proyecto Maven no modular descrito en [Maven y entorno Java](/handbook/tools/maven/). Este ejemplo completo utiliza datos en memoria para aislar el comportamiento de la UI. No requiere MariaDB ni FXML. Sustituye temporalmente app/Main.java en un proyecto de prueba.

## Implementación

```java
package app;

import java.util.Locale;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public record Libro(int id, String titulo) {
    }

    @Override
    public void start(Stage stage) {
        ObservableList<Libro> fuente =
                FXCollections.observableArrayList(
                        new Libro(1, "Java"),
                        new Libro(2, "SQL"),
                        new Libro(3, "JavaFX")
                );

        TableView<Libro> tabla = new TableView<>();
        TableColumn<Libro, Integer> colId = new TableColumn<>("ID");
        TableColumn<Libro, String> colTitulo =
                new TableColumn<>("Título");

        colId.setCellValueFactory(
                celda -> new ReadOnlyObjectWrapper<>(
                        celda.getValue().id()
                )
        );
        colTitulo.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().titulo()
                )
        );
        tabla.getColumns().add(colId);
        tabla.getColumns().add(colTitulo);

        FilteredList<Libro> filtrada =
                new FilteredList<>(fuente, libro -> true);
        SortedList<Libro> ordenada = new SortedList<>(filtrada);

        ordenada.comparatorProperty().bind(
                tabla.comparatorProperty()
        );
        tabla.setItems(ordenada);

        TextField buscador = new TextField();
        buscador.setPromptText("Buscar por título");

        buscador.textProperty().addListener(
                (observable, anterior, actual) -> {
                    String texto = actual.strip()
                            .toLowerCase(Locale.ROOT);

                    filtrada.setPredicate(libro ->
                            libro.titulo()
                                    .toLowerCase(Locale.ROOT)
                                    .contains(texto)
                    );
                }
        );

        VBox root = new VBox(12, buscador, tabla);
        stage.setScene(new Scene(root, 520, 360));
        stage.setTitle("Catálogo filtrable");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

El record Libro se usa exclusivamente en este ejemplo para mantenerlo autónomo. No sustituye el modelo de los laboratorios existentes.

## Ejecutar y comprobar

Ejecuta `mvn javafx:run`.

| Acción | Resultado esperado |
| --- | --- |
| Abrir aplicación | Tres filas |
| Escribir java | Java y JavaFX |
| Escribir SQL | Una fila |
| Escribir xyz | Cero filas |
| Limpiar búsqueda | Tres filas originales |
| Pulsar encabezado Título | Orden según comparador de tabla |

Los resultados son expectativas para validar en ejecución, no resultados ya observados.

## Cómo integrar un DAO

Mantén la ObservableList fuente como atributo del controlador. Carga con `fuente.setAll(dao.listarTodos())`, una vez adaptados los tipos de Libro. Conserva FilteredList y SortedList conectadas. No intentes modificar la lista derivada con setAll ni reemplazarla cada vez que el usuario escribe.

## Errores frecuentes

Si el filtro desaparece al recargar, comprueba que modificas la fuente y no reemplazas los items de la tabla. Si el orden no responde, revisa el binding del comparador. Si eliminas registros al buscar, probablemente estás modificando la fuente en lugar del predicado. Para volúmenes grandes, prefiere filtrar en la consulta SQL.

## Conceptos relacionados

- [TableView](/handbook/java/javafx/table-view/)
- [Observabilidad](/handbook/java/javafx/table-view-observability/)
- [CRUD JDBC](/handbook/java/jdbc/crud/)

## Documentación oficial

- [FilteredList](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/transformation/FilteredList.html)
- [SortedList](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/transformation/SortedList.html)
- [TableView](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TableView.html)
