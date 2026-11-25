---
title: "Controles, validación y alertas"
description: "Leer entradas, validar, manejar excepciones y confirmar operaciones."
---

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Excepciones](/handbook/java/exceptions/)
- [Eventos](/handbook/java/javafx/events/)

## Referencia de controles

| Control | Lectura o acción habitual |
| --- | --- |
| TextField | getText(), setText(), clear() |
| TextArea | getText(), setText(), clear() |
| ComboBox<T> | getValue(), setValue(), getItems() |
| DatePicker | getValue() devuelve LocalDate o null |
| Button | onAction en FXML o setOnAction |
| TableView<T> | Selección y colección de elementos |
| Alert | showAndWait() para diálogo modal |

## Qué validar

Valida el formulario antes de construir el [objeto del dominio](/handbook/java/classes-and-objects/) o invocar el DAO. La validación es una regla, mientras las [excepciones](/handbook/java/exceptions/) representan fallos o condiciones excepcionales del contrato. La interfaz puede dar retroalimentación temprana; las restricciones del esquema siguen siendo necesarias. No confundas un texto vacío con NULL SQL.

```java
private boolean validarTitulo(String titulo) {
    if (titulo == null || titulo.isBlank()) {
        mostrarError("El título es obligatorio.");
        return false;
    }

    if (titulo.strip().length() > 120) {
        mostrarError("El título no puede superar 120 caracteres.");
        return false;
    }

    return true;
}
```

## Guardar y manejar fallos

Fragmento de Controller con txtTitulo, dao, cargar y mostrarError definidos. El ID cero representa un objeto aún no persistido en este ejemplo; no se incluye en el INSERT autoincremental.

```java
@FXML
private void guardar() {
    String titulo = txtTitulo.getText();

    if (!validarTitulo(titulo)) {
        return;
    }

    try {
        Libro libro = new Libro(0, titulo.strip());
        int filas = dao.insertar(libro);

        if (filas != 1) {
            mostrarError("No se confirmó la inserción.");
            return;
        }

        txtTitulo.clear();
        cargar();
    } catch (SQLException e) {
        mostrarError("No se pudo guardar el libro.");
    }
}
```

No borres los datos si la operación falló. Si el guardado fue exitoso pero el refresco falla, informa que no se pudo actualizar la vista; no afirmes que la escritura se revirtió.

## Confirmar eliminación

```java
Alert alerta = new Alert(
        Alert.AlertType.CONFIRMATION,
        "¿Eliminar el libro seleccionado?"
);

boolean confirmado = alerta.showAndWait()
        .filter(ButtonType.OK::equals)
        .isPresent();

if (!confirmado) {
    return;
}
```

Luego ejecuta DELETE por el ID seleccionado y verifica el resultado. Cerrar el diálogo no equivale a aceptar.

## Informar un error

```java
private void mostrarError(String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle("Error");
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}
```

El detalle técnico puede registrarse por separado sin incluir credenciales ni datos sensibles.

## Números, fechas y selecciones · complementario

Los TextField contienen texto, no números. Convierte y valida antes de construir el modelo. Fragmento de controlador con txtCantidad y mostrarError definidos:

```java
int cantidad;

try {
    cantidad = Integer.parseInt(txtCantidad.getText().strip());
} catch (NumberFormatException e) {
    mostrarError("Ingresa un número entero válido.");
    return;
}

if (cantidad <= 0) {
    mostrarError("La cantidad debe ser positiva.");
    return;
}
```

Una fecha de DatePicker es [LocalDate](/handbook/java/dates/); verifica si hay valor y si satisface las reglas del dominio. Una ComboBox<Libro> utiliza [genéricos](/handbook/java/generics/) y retorna el objeto seleccionado con getValue(), que puede ser null. No confundas el texto mostrado con la identidad del objeto. Para reaccionar a cambios de entrada usa [eventos y listeners](/handbook/java/javafx/events/); no dupliques todas las reglas de negocio en cada listener.

## Casos de prueba

| Entrada | Resultado |
| --- | --- |
| Vacía o espacios | Rechazo, sin llamada al DAO |
| Más de 120 caracteres | Rechazo |
| Título válido | INSERT y refresco |
| Actualizar sin selección | Mensaje y ninguna escritura |
| Cancelar eliminación | No llamar al DAO |
| SQLException | Mensaje de fallo, sin anunciar éxito |

Para fechas, comprueba que DatePicker.getValue() no sea null antes de convertir a java.sql.Date. Para números, captura NumberFormatException y aplica límites de dominio.

## Conceptos relacionados

- [FXML y Controller](/handbook/java/javafx/fxml-controller/)
- [TableView](/handbook/java/javafx/table-view/)
- [CRUD](/handbook/java/jdbc/crud/)
- [Diagnóstico](/handbook/troubleshooting/)

## Documentación oficial

- [Controles JavaFX](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/package-summary.html)
- [Alert](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/Alert.html)
- [DatePicker](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/DatePicker.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
