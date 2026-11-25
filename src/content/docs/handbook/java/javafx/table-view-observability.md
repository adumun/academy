---
title: "Observabilidad en TableView"
description: "Diferencias entre colecciones observables, propiedades, getters y persistencia."
---

## Conocimientos previos recomendados

- [ObservableList](/handbook/java/javafx/observable-list/)
- [Patrón Observer](/handbook/architecture/observer/)

**Tipo:** explicación. **Nivel:** complementario. **Versión:** JavaFX 21.

## Tres cambios diferentes

El [estado](/handbook/java/classes-and-objects/) de una tabla incluye los elementos que muestra, sus valores y su selección. Una [propiedad observable](/handbook/java/javafx/properties-and-binding/) notifica cambios del valor; un getter es un método que devuelve un valor y un setter es un método que lo modifica si el contrato lo permite. Una tabla puede cambiar porque se añade un objeto a la [colección observable](/handbook/java/javafx/observable-list/), porque cambia una propiedad observable de un objeto o porque se solicita volver a representar sus celdas. Ninguno de esos cambios escribe por sí mismo en MariaDB.

| Cambio | Quién lo observa |
| --- | --- |
| Agregar o quitar un elemento de ObservableList | Consumidores de la colección |
| Cambiar una StringProperty vinculada a una celda | Consumidores de esa propiedad |
| Cambiar un campo Java con setter común | No hay notificación automática por ese setter |
| UPDATE confirmado en MariaDB | La UI debe recuperar o aplicar el nuevo estado |

## Modelo simple y modelo observable

Para un objeto inmutable como Libro, devolver una envoltura observable de solo lectura a una cell value factory (callback que extrae el valor de una celda; consulta [TableView](/handbook/java/javafx/table-view/)) es suficiente cuando sustituyes los objetos al recargar. Una propiedad JavaFX aporta otro comportamiento: permite observar cambios de valor mediante [listeners](/handbook/java/javafx/events/), aplicando un mecanismo relacionado con el [patrón Observer](/handbook/architecture/observer/).

```java
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LibroObservable {

    private final StringProperty titulo;

    public LibroObservable(String titulo) {
        this.titulo = new SimpleStringProperty(titulo);
    }

    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String valor) {
        titulo.set(valor);
    }

    public StringProperty tituloProperty() {
        return titulo;
    }
}
```

En una tabla de LibroObservable, configura la columna del mismo tipo:

```java
colTitulo.setCellValueFactory(
        celda -> celda.getValue().tituloProperty()
);
```

Si se invoca setTitulo en la instancia que presenta la fila, la celda puede recibir la notificación del cambio sin sustituir la lista. Realiza las actualizaciones asociadas a la UI en el hilo JavaFX.

## ¿Cuándo utilizar cada modelo?

- Modelo Java puro: adecuado para separar DAO y dominio de JavaFX, especialmente con objetos inmutables y recarga explícita.
- Modelo de presentación observable: útil para edición interactiva y bindings.
- No es necesario introducir propiedades JavaFX en entidades de persistencia. Una adaptación de presentación puede mantener esa frontera.

PropertyValueFactory intenta resolver una propiedad observable o un getter compatible. Si solo obtiene un getter, el valor envuelto no se convierte mágicamente en una propiedad enlazada al campo original.

## Comprobación mental

Si reemplazas un Libro por otra instancia mediante setAll sobre la lista fuente, la tabla refleja la nueva colección. Si modificas un atributo ordinario sin notificación, no presupongas que la celda se refrescará. Si ejecutas UPDATE en otra aplicación, ninguna de estas estrategias detecta automáticamente la modificación remota.

## Conceptos relacionados

- [Properties y binding](/handbook/java/javafx/properties-and-binding/)

- [TableView](/handbook/java/javafx/table-view/)
- [Filtrado y ordenamiento](/handbook/java/javafx/table-view-filter-sort/)
- [MVC y DAO](/handbook/architecture/)

## Documentación oficial

- [PropertyValueFactory](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/cell/PropertyValueFactory.html)
- [StringProperty](https://openjfx.io/javadoc/21/javafx.base/javafx/beans/property/StringProperty.html)
- [ObservableList](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/ObservableList.html)
