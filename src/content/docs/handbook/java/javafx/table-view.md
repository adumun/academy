---
title: "TableView y ObservableList"
description: "Columnas, mapeo de propiedades, selección, carga y refresco desde DAO."
---

## Modelo mental

TableView<T> presenta una colección de objetos. TableColumn<S,T> extrae un valor de cada objeto de fila. ObservableList comunica cambios de la colección a la interfaz; no escribe nada en la base de datos.

| Tipo | Significado |
| --- | --- |
| TableView<Libro> | Tabla de objetos Libro |
| TableColumn<Libro, Integer> | Columna de ID |
| TableColumn<Libro, String> | Columna de título |
| ObservableList<Libro> | Colección observable |
| SelectionModel | Selección actual |

## Vista

Fragmento dentro del FXML, con imports TableView y TableColumn. El controlador debe contener los campos con los mismos fx:id.

```xml
<TableView fx:id="tablaLibros">
    <columns>
        <TableColumn fx:id="colId" text="ID"/>
        <TableColumn fx:id="colTitulo" text="Título"/>
    </columns>
</TableView>
```

## Controlador

Fragmentos para una clase con los imports de JavaFX, modelo Libro, DAO y SQLException. El modelo expone getId() y getTitulo().

```java
@FXML
private TableView<Libro> tablaLibros;

@FXML
private TableColumn<Libro, Integer> colId;

@FXML
private TableColumn<Libro, String> colTitulo;

@FXML
private void initialize() {
    colId.setCellValueFactory(new PropertyValueFactory<>("id"));
    colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

    cargar();
}

private void cargar() {
    try {
        tablaLibros.getItems().setAll(dao.listarTodos());
    } catch (SQLException e) {
        mostrarError("No se pudo cargar el listado.");
    }
}
```

PropertyValueFactory busca propiedades o getters compatibles mediante reflexión. El nombre titulo corresponde a getTitulo(). En un proyecto modular puede ser necesario abrir el paquete del modelo a javafx.base. Con getters normales, editar el objeto no garantiza una notificación automática de la celda.

### Alternativa sin reflexión

```java
colTitulo.setCellValueFactory(
        celda -> new ReadOnlyStringWrapper(
                celda.getValue().getTitulo()
        )
);
```

Requiere javafx.beans.property.ReadOnlyStringWrapper. Resulta útil para detectar errores de nombres en compilación.

## Selección y refresco

```java
Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();

if (seleccionado == null) {
    mostrarError("Selecciona un libro.");
    return;
}

int id = seleccionado.getId();
```

Usa el ID real, no getSelectedIndex(), que solo indica una posición visual. Después de un INSERT, UPDATE o DELETE confirmado, vuelve a consultar el DAO. setAll sustituye los elementos de la lista, conservando su identidad. refresh() solicita redibujar las celdas; no consulta la base.

## Métodos frecuentes

| Método | Efecto |
| --- | --- |
| getItems() | Obtiene la ObservableList |
| setItems(lista) | Reemplaza la colección asociada |
| getItems().setAll(lista) | Sustituye sus elementos |
| getSelectionModel().getSelectedItem() | Objeto seleccionado o null |
| clearSelection() | Quita la selección |
| refresh() | Solicita actualizar la presentación |

## Prueba y diagnóstico

Si SQL devuelve dos filas, la tabla debe mostrar dos objetos. Si la tabla contiene filas con celdas vacías, revisa getters y nombres de propiedad. Si no contiene filas, comprueba el tamaño de dao.listarTodos(), el método cargar y las excepciones. Tras escribir, compara TableView con SELECT y reinicia para verificar persistencia.

## Conceptos relacionados

- [FXML y Controller](/handbook/java/javafx/fxml-controller/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [CRUD](/handbook/java/jdbc/crud/)
- [Validaciones](/handbook/java/javafx/validation/)

## Documentación oficial

- [TableView](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TableView.html)
- [PropertyValueFactory](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/cell/PropertyValueFactory.html)
- [ObservableList](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/ObservableList.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
