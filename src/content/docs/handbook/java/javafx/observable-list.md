---
title: "ObservableList"
description: "Colecciones observables, creación, listeners y sincronización con controles."
---

**Tipo:** referencia y explicación. **Nivel:** esencial a complementario. **Versión:** Java 21, JavaFX 21.

## Conocimientos previos recomendados

- [Genéricos](/handbook/java/generics/)
- [List y colecciones](/handbook/java/collections/)
- [Lambdas](/handbook/java/lambdas/)

## Qué añade a List

ObservableList<E> extiende [List<E>](/handbook/java/collections/) y permite observar cambios de colección. El parámetro E es un [genérico](/handbook/java/generics/) que fija el tipo de elemento. TableView y ComboBox pueden utilizarla para responder a cambios de sus elementos.

| API | Uso |
| --- | --- |
| FXCollections.observableArrayList() | Crear colección observable modificable |
| FXCollections.observableArrayList(Collection) | Crear colección a partir de elementos |
| boolean setAll(Collection) | Sustituir los elementos; retorna si cambió |
| addListener(ListChangeListener) | Suscribir cambios de lista |
| removeListener(ListChangeListener) | Retirar una suscripción |
| get(int), add(E), remove(int) | Operaciones heredadas de List |

## Ejemplo autónomo sin ventana

Requiere javafx-base en el classpath; el proyecto Maven JavaFX del Handbook ya incluye esta dependencia transitivamente.

```java
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class ListaObservableDemo {

    public static void main(String[] args) {
        ObservableList<String> titulos =
                FXCollections.observableArrayList();

        ListChangeListener<String> listener = cambio -> {
            while (cambio.next()) {
                if (cambio.wasAdded()) {
                    System.out.println(cambio.getAddedSubList());
                }
            }
        };

        titulos.addListener(listener);
        titulos.add("Java");
        titulos.removeListener(listener);
    }
}
```

Salida esperada: [Java]. El Change puede contener varios cambios parciales; recórrelo mediante next(). No modifiques la misma lista dentro del listener sin analizar reentrancia.

## Lista fuente y lista de la vista

```java
private final ObservableList<Libro> fuente =
        FXCollections.observableArrayList();

@FXML
private void initialize() {
    tablaLibros.setItems(fuente);
}

private void cargar() throws SQLException {
    fuente.setAll(dao.listarTodos());
}
```

Fragmentos de un controlador con Libro, dao y tablaLibros existentes. El DAO devuelve List; la capa de presentación mantiene la colección observable. No confundir cambios de lista con notificación de propiedades de cada objeto: consulta [Observabilidad en TableView](/handbook/java/javafx/table-view-observability/).

## Filtrado, orden y límites

FilteredList y SortedList son vistas derivadas. Actualiza la colección fuente y cambia el predicado/comparador, no intentes sustituir sus elementos directamente. Trabaja en el hilo JavaFX cuando la colección esté vinculada a controles gráficos. ObservableList no es un mecanismo de persistencia ni garantiza seguridad de hilos.

## Diagnóstico y comprobación

Agrega, elimina y reemplaza elementos. Comprueba notificaciones y representación en el control. Si un cambio en un campo del objeto no aparece, revisa propiedades observables y cell value factory. Si el dato no persiste después de reiniciar, revisa DAO/SQL, no la lista.

## Conceptos relacionados

- [TableView](/handbook/java/javafx/table-view/)
- [Observabilidad](/handbook/java/javafx/table-view-observability/)
- [Filtrar y ordenar](/handbook/java/javafx/table-view-filter-sort/)

## Documentación oficial

- [ObservableList](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/ObservableList.html)
- [FXCollections](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/FXCollections.html)
- [ListChangeListener](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/ListChangeListener.html)
