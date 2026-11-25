---
title: "Controles de formulario JavaFX"
description: "TextField, TextArea, ComboBox, DatePicker, CheckBox y manejo de valores."
---

**Tipo:** referencia y guía práctica. **Nivel:** esencial a complementario. **Versión:** JavaFX 21.

## Conocimientos previos recomendados

- [FXML y Controller](/handbook/java/javafx/fxml-controller/)
- [Genéricos](/handbook/java/generics/)
- [Eventos y listeners](/handbook/java/javafx/events/)

## Elegir el control según el dato

Un control es un [objeto](/handbook/java/classes-and-objects/) visual que recibe entrada o muestra información. Cada control expone [métodos](/handbook/java/methods/) y propiedades para leer o cambiar valores. String, boolean y LocalDate son [tipos](/handbook/java/types/); null expresa [ausencia de referencia](/handbook/java/references/). El parámetro T de ComboBox es un [genérico](/handbook/java/generics/) que indica qué clase de objeto selecciona el usuario.

| Control | Tipo de valor | Operaciones |
| --- | --- | --- |
| TextField | String | getText(), setText(), clear() |
| TextArea | String multilínea | getText(), setText(), clear() |
| `ComboBox<T>` | T o null | getValue(), setValue(), getItems() |
| DatePicker | LocalDate o null | getValue(), setValue() |
| CheckBox | boolean | isSelected(), setSelected(boolean) |
| Button | Evento de acción | setOnAction(handler), setDisable(boolean) |

El texto mostrado no siempre es el dato del dominio. ComboBox puede contener objetos; DatePicker retorna una fecha, no una cadena formateada.

## ComboBox con objetos

Fragmento de controlador con modelo Libro, colLibros de tipo `ComboBox<Libro>` y DAO definidos. Utiliza una StringConverter para la representación: transforma entre el objeto y el texto mostrado, sin reemplazar necesariamente la identidad del objeto. En este ejemplo no se permite escribir valores arbitrarios.

```java
colLibros.setEditable(false);
colLibros.getItems().setAll(dao.listarTodos());

colLibros.setConverter(new StringConverter<Libro>() {

    @Override
    public String toString(Libro libro) {
        return libro == null ? "" : libro.getTitulo();
    }

    @Override
    public Libro fromString(String texto) {
        throw new UnsupportedOperationException(
                "El selector no es editable"
        );
    }
});

Libro seleccionado = colLibros.getValue();

if (seleccionado != null) {
    int id = seleccionado.getId();
    System.out.println(id);
}
```

Importa javafx.util.StringConverter. Si necesitas personalización adicional de celdas, configura una cell factory; un selector editable requiere conversión inversa válida. Al recargar objetos, no presupongas que una instancia anterior sigue seleccionada: recupera la selección por ID si la experiencia lo requiere.

## Fechas y selección booleana

```java
LocalDate vencimiento = fechaVencimiento.getValue();
boolean confirmado = chkConfirmado.isSelected();

if (vencimiento == null || !confirmado) {
    mostrarError("Completa la fecha y confirma.");
    return;
}
```

Importa java.time.LocalDate. Un CheckBox puede configurarse con estado indeterminado; este ejemplo usa el modo binario por defecto. Para fecha sin hora consulta [LocalDate](/handbook/java/dates/).

## Reutilizar validación y observabilidad

Las propiedades de los controles permiten observar cambios y enlazar estados. Consulta [Properties y binding](/handbook/java/javafx/properties-and-binding/). Mantén una validación al ejecutar la acción aunque la interfaz deshabilite botones. En operaciones JDBC, usa el ID del objeto y no su posición visual.

## Diagnóstico

| Síntoma | Comprobación |
| --- | --- |
| ComboBox muestra referencias de objetos | Configura representación de los elementos |
| No hay valor seleccionado | getValue() puede retornar null |
| La fecha no se convierte | Comprueba null, tipo y formato |
| El botón no reacciona | Revisa evento, fx:id y estado disabled |
| El formulario pierde datos tras fallo | Limpia controles solo cuando la operación sea exitosa |

## Conceptos relacionados

- [Validación y alertas](/handbook/java/javafx/validation/)
- [ObservableList](/handbook/java/javafx/observable-list/)
- [TableView](/handbook/java/javafx/table-view/)

## Documentación oficial

- [ComboBox](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/ComboBox.html)
- [DatePicker](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/DatePicker.html)
- [TextInputControl](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TextInputControl.html)
- [CheckBox](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/CheckBox.html)
