---
title: "TableView y ObservableList"
description: "Columnas, mapeo de propiedades, selección, carga y refresco desde DAO."
---

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Genéricos](/handbook/java/generics/)
- [Colecciones y List](/handbook/java/collections/)
- [Lambdas](/handbook/java/lambdas/)

**Tipo:** referencia técnica con explicación introductoria. **Versión:** JavaFX 21. **Paquete:** `javafx.scene.control`, módulo `javafx.controls`.

## Modelo mental

La clase `TableView<T>` presenta una [colección de objetos](/handbook/java/collections/). Sus [genéricos](/handbook/java/generics/) determinan el tipo de fila: `TableView<Libro>` solo presenta objetos Libro. Una fila representa un objeto; una columna organiza el mismo tipo de dato para todas las filas y una celda es la intersección de fila y columna. `TableColumn<S,T>` extrae un valor de cada fila: S indica el tipo de objeto de fila y T el tipo del valor extraído. Un [tipo](/handbook/java/types/) define los valores y operaciones válidos. Integer, por ejemplo, es un tipo de referencia que envuelve un entero. Una [ObservableList](/handbook/java/javafx/observable-list/) comunica cambios de la colección a la interfaz; no escribe nada en la base de datos.

| Tipo | Significado |
| --- | --- |
| `TableView<Libro>` | Tabla de objetos Libro |
| `TableColumn<Libro, Integer>` | Columna de ID |
| `TableColumn<Libro, String>` | Columna de título |
| `ObservableList<Libro>` | Colección observable |
| SelectionModel | Selección actual |

## Vista

Vista FXML completa del proyecto de referencia, con imports y controlador. El cargador configura una controller factory para suministrar el DAO antes de initialize; los campos conservan los mismos fx:id.

<!-- example:fx-table-fxml -->
```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TableColumn?>
<TableView xmlns="http://javafx.com/javafx/21" xmlns:fx="http://javafx.com/fxml/1" fx:id="tablaLibros" fx:controller="TablaLibrosDemo">
    <columns>
        <TableColumn fx:id="colId" text="ID"/>
        <TableColumn fx:id="colTitulo" text="Título"/>
    </columns>
</TableView>
```

## Controlador

Fragmentos para una clase con los imports de JavaFX, modelo Libro, DAO y SQLException. El modelo expone getId() y getTitulo().

<!-- example:fx-table-controller -->
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

PropertyValueFactory busca [propiedades observables](/handbook/java/javafx/properties-and-binding/) o getters (métodos que devuelven un atributo) compatibles mediante reflexión, es decir, inspección de los miembros de un tipo durante la ejecución. Un callback es una operación que JavaFX invoca para obtener o presentar el valor; consulta [lambdas](/handbook/java/lambdas/). El nombre titulo corresponde a getTitulo(). En un proyecto modular puede ser necesario abrir el paquete del modelo a javafx.base. Con getters normales, editar el objeto no garantiza una notificación automática de la celda.

### Alternativa sin reflexión

<!-- example:fx-table-alternative -->
```java
colTitulo.setCellValueFactory(
        celda -> new ReadOnlyStringWrapper(
                celda.getValue().getTitulo()
        )
);
```

Requiere javafx.beans.property.ReadOnlyStringWrapper. Resulta útil para detectar errores de nombres en compilación.

## Selección y refresco

<!-- example:fx-table-select -->
```java
Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();

if (seleccionado == null) {
    mostrarError("Selecciona un libro.");
    return;
}

int id = seleccionado.getId();
```

Usa la [clave primaria](/handbook/sql/relational-model/) o ID real, no getSelectedIndex(), que solo indica una posición visual. Después de un INSERT, UPDATE o DELETE confirmado, vuelve a consultar el DAO. setAll sustituye los elementos de la lista, conservando su identidad. refresh() solicita redibujar las celdas; no consulta la base.

## Configuración de columnas · complementario

| API | Parámetros y comportamiento |
| --- | --- |
| TableColumn(String text) | Crea una columna con el encabezado indicado |
| setCellValueFactory(callback) | Extrae el valor observable para cada fila |
| setCellFactory(callback) | Personaliza representación y edición |
| setSortable(boolean) | Permite o impide ordenar esa columna |
| setPrefWidth(double) | Sugiere el ancho en píxeles |
| setVisible(boolean) | Muestra u oculta una columna |
| getColumns() | Colección de columnas hijas (permite agrupación) |

No confundas el valor de una celda con su representación. Una columna numérica debe conservar un tipo numérico aunque se muestre con formato.

<!-- example:fx-table-columns -->
```java
colId.setCellValueFactory(
        celda -> new ReadOnlyObjectWrapper<>(
                celda.getValue().getId()
        )
);
colId.setPrefWidth(80);
colTitulo.setPrefWidth(280);
```

Fragmento de controlador con las columnas declaradas arriba; importa javafx.beans.property.ReadOnlyObjectWrapper. Esta alternativa comprueba el getter en compilación.

## Selección simple y múltiple · complementario

<!-- example:fx-table-multiple -->
```java
tablaLibros.getSelectionModel().setSelectionMode(
        SelectionMode.MULTIPLE
);

List<Libro> seleccionados = new ArrayList<>(
        tablaLibros.getSelectionModel().getSelectedItems()
);
```

Importa javafx.scene.control.SelectionMode, java.util.List y java.util.ArrayList. Copia la selección antes de realizar una operación que pueda modificar la tabla. La selección simple es apropiada para editar un registro; la múltiple puede servir para una operación masiva autorizada.

Para actualizar un formulario cuando cambia la selección:

<!-- example:fx-table-listener -->
```java
tablaLibros.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, anterior, actual) -> {
            if (actual == null) {
                txtTitulo.clear();
            } else {
                txtTitulo.setText(actual.getTitulo());
            }
        });
```

El [listener](/handbook/java/javafx/events/) puede recibir null al eliminar una fila o recargar datos. No guardes automáticamente cada cambio de selección.

## Ordenamiento y filtrado · complementario

Por defecto, pulsar un encabezado ordenable modifica el orden presentado. El orden puede abarcar varias columnas. El orden de la UI no es una garantía del orden en SQL.

Para filtrar por texto sin perder registros ni reconsultar la base por cada tecla, utiliza una lista fuente observable y vistas derivadas. Consulta la [guía de filtrado y ordenamiento](/handbook/java/javafx/table-view-filter-sort/) para una aplicación autónoma.

## Personalización de celdas · avanzado

Una cell factory utiliza un callback, normalmente una [lambda](/handbook/java/lambdas/), para cambiar cómo se presenta el valor sin modificar el modelo. Fragmento para la columna de título:

<!-- example:fx-table-cell -->
```java
colTitulo.setCellFactory(columna -> new TableCell<Libro, String>() {

    @Override
    protected void updateItem(String titulo, boolean empty) {
        super.updateItem(titulo, empty);

        if (empty || titulo == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(titulo.toUpperCase(Locale.ROOT));
            setGraphic(null);
        }
    }
});
```

Importa javafx.scene.control.TableCell y java.util.Locale. JavaFX puede reutilizar celdas al desplazar la tabla. Limpia su estado al representar una celda vacía. El cambio de presentación no modifica el título real.

## Edición · avanzado

TableView admite celdas editables si la tabla, la columna y la implementación de celda permiten edición. Por ejemplo, TextFieldTableCell.forTableColumn() crea una celda de texto editable. El evento de confirmación proporciona el valor nuevo y el objeto de fila.

En una aplicación JDBC, editar visualmente no equivale a guardar: valida el nuevo dato, ejecuta la operación DAO por ID y recarga la lista tras confirmarla. Si falla, conserva o restaura el estado consistente e informa el error. No habilites edición directa en la tabla si el formulario ya resuelve el caso con mayor claridad.

## Rendimiento y concurrencia · avanzado

Para grandes volúmenes, evita cargar toda la tabla de la base solo para mostrar una parte. Considera consultas paginadas y ordenamiento en SQL. Ejecuta consultas bloqueantes fuera del hilo gráfico mediante Task o Service cuando corresponda; aplica los resultados al modelo de UI desde el hilo JavaFX. La virtualización visual de celdas no sustituye la paginación de datos.

## Métodos frecuentes

| Método | Efecto |
| --- | --- |
| getItems() | Obtiene la ObservableList |
| setItems(lista) | Reemplaza la colección asociada |
| getItems().setAll(lista) | Sustituye sus elementos |
| getSelectionModel().getSelectedItem() | Objeto seleccionado o null |
| clearSelection() | Quita la selección |
| refresh() | Solicita actualizar la presentación |
| getColumns() | Devuelve las columnas de nivel superior |
| getSortOrder() | Devuelve columnas que participan en el orden |
| setEditable(boolean) | Habilita o deshabilita edición de tabla |
| scrollTo(int) | Solicita desplazar la vista al índice |
| setPlaceholder(Node) | Configura contenido cuando no hay datos |

## Prueba y diagnóstico

Si SQL devuelve dos filas, la tabla debe mostrar dos objetos. Si la tabla contiene filas con celdas vacías, revisa getters y nombres de propiedad. Si no contiene filas, comprueba el tamaño de dao.listarTodos(), el método cargar y las excepciones. Tras escribir, compara TableView con SELECT y reinicia para verificar persistencia.

## Matriz de comprobaciones

| Caso | Resultado esperado |
| --- | --- |
| Dos objetos con IDs distintos | Dos filas, datos correspondientes |
| Lista fuente vacía | Sin filas, placeholder si se configuró |
| Reordenar por título | El ID viaja con el objeto |
| Limpiar selección | selectedItem es null |
| Recargar tras INSERT confirmado | El nuevo objeto aparece |
| Getter no observable modificado | No confiar en actualización automática |
| Consulta DAO falla | Mensaje de error, sin anunciar éxito |

Estas comprobaciones son criterios de prueba; no constituyen evidencia de ejecución.

## Cómo se representa una fila

![Una lista contiene dos objetos Libro. Cada fila corresponde a un objeto; las factorías de columnas extraen id y titulo para las celdas.](/diagrams/tableview-row-column.svg)

TableView utiliza objetos como elementos; TableColumn identifica el tipo de fila y el tipo de valor que representa. La cell value factory obtiene ese valor, normalmente mediante un ObservableValue. La cell factory configura la presentación de la celda, por ejemplo texto, formato o gráfico; no debe modificar el modelo para simular un formato visual. Consulta [observabilidad](/handbook/java/javafx/table-view-observability/) para comprender las notificaciones.

### Contraste entre identidad visual y de datos

Si la tabla muestra los libros ID 8 e ID 3, la posición 0 podría corresponder al ID 8. Ordenar puede cambiar qué objeto ocupa esa posición. Obtén el objeto seleccionado y su identificador según el contrato; no envíes getSelectedIndex como clave de almacenamiento.

Una tabla vacía puede significar una consulta exitosa sin resultados, un filtro sin coincidencias o un fallo de carga. La aplicación debe distinguirlos. Si la carga inicial usa dependencias configuradas después de FXMLLoader.load, no las invoques dentro de initialize antes de recibirlas.

## Conceptos relacionados

- [Observabilidad y propiedades](/handbook/java/javafx/table-view-observability/)
- [Filtrar y ordenar una TableView](/handbook/java/javafx/table-view-filter-sort/)

- [FXML y Controller](/handbook/java/javafx/fxml-controller/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [CRUD](/handbook/java/jdbc/crud/)
- [Validaciones](/handbook/java/javafx/validation/)

## Verificación ejecutable

Fuentes canónicas en `examples/java/handbook/maven/javafx/`. Los casos se ejecutan con JavaFX real y un DAO simulado; no acreditan persistencia. Consulta la suite JUnit y el pipeline del commit utilizado. Para ejecutar localmente en Linux: `xvfb-run -a mvn -B -ntp clean test` desde el módulo.

## Documentación oficial

- [TableView](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TableView.html)
- [TableColumn](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TableColumn.html)
- [TableCell](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TableCell.html)
- [PropertyValueFactory](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/cell/PropertyValueFactory.html)
- [ObservableList](https://openjfx.io/javadoc/21/javafx.base/javafx/collections/ObservableList.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
