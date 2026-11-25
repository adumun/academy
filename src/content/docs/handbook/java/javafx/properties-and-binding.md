---
title: "Properties y binding en JavaFX"
description: "Propiedades observables, enlaces unidireccionales y bidireccionales, ciclo de vida y límites."
---

**Tipo:** explicación y referencia. **Nivel:** complementario a avanzado. **Versión:** JavaFX 21.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Eventos y listeners](/handbook/java/javafx/events/)
- [Patrón Observer](/handbook/architecture/observer/)

## Diferencias entre valores, propiedades y bindings

Un [atributo](/handbook/java/variables/) ordinario almacena un valor de un objeto. Una propiedad JavaFX encapsula el valor y permite observarlo; puede ser modificable o de solo lectura. Un [listener](/handbook/java/javafx/events/) recibe cambios. Un binding (enlace) expresa una dependencia entre valores observables: cuando cambia la fuente, el valor dependiente puede recalcularse o sincronizarse. Unidireccional indica que el destino sigue a la fuente; bidireccional permite actualizar en ambos sentidos. No es una consulta SQL ni una regla de persistencia.

| API | Significado |
| --- | --- |
| StringProperty | Propiedad de texto modificable y observable |
| IntegerProperty | Propiedad de entero |
| BooleanProperty | Propiedad booleana |
| ObservableValue | Contrato de observación de un valor |
| bind(observable) | Enlace unidireccional; el destino sigue la fuente |
| unbind() | Retira el enlace unidireccional |
| bindBidirectional(property) | Sincroniza valores en ambas direcciones |
| unbindBidirectional(property) | Retira el enlace bidireccional |

## Ejemplo autónomo sin ventana

Necesita javafx-base en el classpath, incluido en el proyecto Maven JavaFX de Academy. Archivo BindingDemo.java.

```java
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BindingDemo {

    public static void main(String[] args) {
        StringProperty origen =
                new SimpleStringProperty("Java");
        StringProperty destino =
                new SimpleStringProperty();

        destino.bind(origen);
        System.out.println(destino.get());

        origen.set("JavaFX");
        System.out.println(destino.get());

        destino.unbind();
        destino.set("SQL");
        System.out.println(destino.get());
    }
}
```

Salida esperada: Java, JavaFX y SQL. Mientras destino está enlazado unidireccionalmente, no se debe intentar asignarle directamente otro valor. Tras unbind se puede escribir de nuevo.

## Binding en formularios

```java
btnGuardar.disableProperty().bind(
        txtTitulo.textProperty().isEmpty()
);
```

Fragmento de initialize() de un controlador FXML. El botón sigue el estado de entrada. El binding no valida espacios ni longitud y no reemplaza la validación de dominio. Para reglas más complejas usa Bindings.createBooleanBinding con dependencias explícitas o una lógica de validación clara.

## Propiedades en una tabla

Una propiedad de fila permite a la cell value factory retornar un valor observable. Eso no implica que el DAO deba almacenar objetos JavaFX. Puedes usar un modelo puro y un adaptador de presentación, o recargar objetos inmutables. Consulta [observabilidad en TableView](/handbook/java/javafx/table-view-observability/).

## Límites y diagnóstico

- Evita enlazar el mismo destino a fuentes incompatibles; define una sola dirección de autoridad.
- Un binding no persiste datos y no sustituye la validación.
- Retira listeners/bindings al destruir una vista cuando el ciclo de vida lo requiera.
- Actualiza propiedades asociadas a controles desde el hilo JavaFX.
- Si el valor no cambia, comprueba que modificas la misma propiedad observada, no un atributo distinto.

## Comprobación

Cambia la fuente, comprueba el destino, retira el binding y vuelve a cambiar la fuente. La propagación debe detenerse tras unbind. En un formulario, comprueba también que la acción valida los datos aunque el botón esté habilitado.

## Conceptos relacionados

- [ObservableList](/handbook/java/javafx/observable-list/)
- [Eventos](/handbook/java/javafx/events/)
- [Validación](/handbook/java/javafx/validation/)
- [TableView](/handbook/java/javafx/table-view/)

## Documentación oficial

- [Property](https://openjfx.io/javadoc/21/javafx.base/javafx/beans/property/Property.html)
- [StringProperty](https://openjfx.io/javadoc/21/javafx.base/javafx/beans/property/StringProperty.html)
- [Bindings](https://openjfx.io/javadoc/21/javafx.base/javafx/beans/binding/Bindings.html)
