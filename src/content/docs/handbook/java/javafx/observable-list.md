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

`ObservableList<E>` extiende [`List<E>`](/handbook/java/collections/) y permite observar cambios de colección. El parámetro E es un [genérico](/handbook/java/generics/) que fija el tipo de elemento. Observable significa que otros componentes pueden suscribirse a notificaciones de cambios, no que la lista se guarde automáticamente. Un [listener](/handbook/java/javafx/events/) recibe esas notificaciones, según el mecanismo de suscripción del [patrón Observer](/handbook/architecture/observer/). [TableView](/handbook/java/javafx/table-view/) y [ComboBox](/handbook/java/javafx/controls/) pueden utilizarla para responder a cambios de sus elementos.

| API | Uso |
| --- | --- |
| FXCollections.observableArrayList() | Crear colección observable modificable |
| FXCollections.observableArrayList(Collection) | Crear colección a partir de elementos |
| boolean setAll(Collection) | Sustituir los elementos; retorna si cambió |
| addListener(ListChangeListener) | Suscribir cambios de lista |
| removeListener(ListChangeListener) | Retirar una suscripción |
| get(int), add(E), remove(int) | Operaciones heredadas de List |

## Ejemplo autónomo sin ventana

Requiere javafx-base en el [classpath](/handbook/java/packages-and-imports/), las ubicaciones donde se buscan clases y recursos; el proyecto [Maven](/handbook/tools/maven/) ya incluye esta dependencia transitivamente (a través de otra dependencia).

<!-- example:fx-observable-demo -->
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

<!-- example:fx-list-source -->
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

## Qué observa una colección y qué no

![Al agregar un elemento a la lista observable se notifica al listener y al control que consume la misma lista. Cambiar un campo ordinario de un elemento no genera necesariamente un cambio estructural.](/diagrams/javafx-observable-list.svg)

ObservableList notifica cambios estructurales: incorporación, eliminación, reemplazo o permutación según la operación. Una modificación de un campo ordinario dentro de un objeto puede no alterar la lista. Un extractor de observables puede hacer que determinadas propiedades de los elementos produzcan notificaciones de actualización; no convierte automáticamente todos los campos en observables.

| Operación | Resultado observable esperado |
|---|---|
| fuente.add(elemento) | Se incorpora un elemento |
| fuente.remove(elemento) | Se elimina una coincidencia, si existe |
| fuente.setAll(otraLista) | Se sustituyen los elementos; permanece la lista fuente |
| fuente = otraObservableList | Cambia la referencia de la variable, no la referencia ya entregada al control |
| elemento.setCampo(...) ordinario | No implica una notificación estructural |

### Comprobar la referencia compartida

<!-- example:fx-list-alias -->
```java
ObservableList<String> fuente = FXCollections.observableArrayList("A");
ObservableList<String> consumidor = fuente;
fuente.setAll("B", "C");
System.out.println(consumidor); // [B, C]
fuente = FXCollections.observableArrayList("D");
System.out.println(consumidor); // [B, C]
```

Fragmento dentro de un método con javafx-base e imports correspondientes. La segunda asignación no modifica la lista anterior. Si una TableView consume esa lista, mantiene la referencia original hasta que se le entregue otra mediante setItems. Consulta [referencias](/handbook/java/references/) y distingue actualización de datos de reasignación.

## Conceptos relacionados

- [TableView](/handbook/java/javafx/table-view/)
- [Observabilidad](/handbook/java/javafx/table-view-observability/)
- [Filtrar y ordenar](/handbook/java/javafx/table-view-filter-sort/)

## Verificación ejecutable

DAO simulado en memoria; no certifica MariaDB. Proyecto Maven: `examples/java/handbook/maven/javafx/`. En Linux con pantalla virtual, ejecuta `xvfb-run -a mvn -B -ntp clean test` desde esa carpeta. Consulta resultados Surefire del SHA utilizado; no equivale a revisión visual manual.

## Documentación oficial

- [ObservableList](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/ObservableList.html)
- [FXCollections](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/FXCollections.html)
- [ListChangeListener](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/ListChangeListener.html)
