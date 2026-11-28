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

<!-- example:fx-control-combo -->
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

<!-- example:fx-control-date -->
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

## El valor visual y el valor del modelo

Leer un control no equivale a validar ni guardar. Un TextField contiene texto incluso cuando el dominio requiere un entero. Una cadena vacía, una fecha ausente y un número fuera de rango son problemas diferentes.

```text
Control → extracción → conversión → validación → operación de dominio
 texto       String       int          rango       cambio de estado
```

### Ejemplo de conversión explícita

Fragmento dentro de un controlador: cantidadField es TextField y mensaje es Label.

<!-- example:fx-control-quantity -->
```java
String entrada = cantidadField.getText().strip();

try {
    int cantidad = Integer.parseInt(entrada);
    if (cantidad <= 0) {
        mensaje.setText("La cantidad debe ser positiva");
        return;
    }
    mensaje.setText("Cantidad válida: " + cantidad);
} catch (NumberFormatException exception) {
    mensaje.setText("Ingresa un entero válido");
}
```

Prueba con espacios, abc, 0 y 12. Solo el último caso debe aceptar la cantidad. El texto de confirmación no demuestra que el dato haya sido persistido. Si se utiliza una selección de objetos, verifica también que el valor seleccionado siga siendo válido antes de ejecutar la operación.

## Selección y agrupación

RadioButton permite representar opciones mutuamente excluyentes si pertenecen a un mismo ToggleGroup. CheckBox representa una decisión independiente. ComboBox permite escoger un valor de una lista; DatePicker proporciona una fecha. El control se elige por el tipo de interacción, no por su apariencia. Para varias decisiones independientes, no agrupes los botones como si fueran una sola elección.

## Conceptos relacionados

- [Validación y alertas](/handbook/java/javafx/validation/)
- [ObservableList](/handbook/java/javafx/observable-list/)
- [TableView](/handbook/java/javafx/table-view/)

## Verificación ejecutable

Fuentes canónicas en `examples/java/handbook/maven/javafx/`. Los casos se ejecutan con JavaFX real y un DAO simulado; no acreditan persistencia. Consulta la suite JUnit y el pipeline del commit utilizado. Para ejecutar localmente en Linux: `xvfb-run -a mvn -B -ntp clean test` desde el módulo.

## Documentación oficial

- [ComboBox](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/ComboBox.html)
- [DatePicker](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/DatePicker.html)
- [TextInputControl](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/TextInputControl.html)
- [CheckBox](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/CheckBox.html)
