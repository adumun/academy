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
| `ComboBox<T>` | getValue(), setValue(), getItems() |
| DatePicker | getValue() devuelve LocalDate o null |
| Button | onAction en FXML o setOnAction |
| `TableView<T>` | Selección y colección de elementos |
| Alert | showAndWait() para diálogo modal |

## Qué validar

Un formulario es un conjunto de [controles](/handbook/java/javafx/controls/) que permite introducir datos. Validar significa comprobar condiciones antes de aceptar una operación; una regla del dominio protege un requisito del problema, por ejemplo que la cantidad sea positiva. Una alerta es un diálogo que comunica información o pide confirmación; modal significa que restringe la interacción con otras ventanas según su modalidad. Una [condición booleana](/handbook/java/expressions/) devuelve true o false y permite decidir si continuar.

Valida el formulario antes de construir el [objeto del dominio](/handbook/java/classes-and-objects/) o invocar el DAO. La validación es una regla, mientras las [excepciones](/handbook/java/exceptions/) representan fallos o condiciones excepcionales del contrato. La interfaz puede dar retroalimentación temprana; las restricciones del esquema siguen siendo necesarias. No confundas un texto vacío con NULL SQL.

<!-- example:fx-validation-title -->
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

Fragmento de Controller con txtTitulo, dao, cargar y mostrarError definidos. En este ejemplo cargar declara throws SQLException y no captura internamente el error; el guardado y el refresco se tratan por separado. Se presupone que insertar usa auto-commit y comunica su resultado según el contrato. El ID cero representa un objeto aún no persistido en este ejemplo; no se incluye en el INSERT autoincremental.

<!-- example:fx-validation-save -->
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

    } catch (SQLException exception) {
        mostrarError("No se pudo confirmar el guardado del libro.");
        return;
    }

    txtTitulo.clear();
    try {
        cargar();
    } catch (SQLException exception) {
        mostrarError("Libro guardado; no se pudo actualizar el listado.");
    }
}
```

No borres los datos si la operación falló. Si el guardado fue exitoso pero el refresco falla, informa que no se pudo actualizar la vista; no afirmes que la escritura se revirtió.

## Confirmar eliminación

<!-- example:fx-validation-confirm -->
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

<!-- example:fx-validation-error -->
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

Los TextField contienen texto, no números. [Convertir o analizar un valor](/handbook/java/types/) transforma el texto en un tipo numérico; validar comprueba que ese valor cumpla la regla. Una entrada vacía y una [referencia null](/handbook/java/references/) son situaciones distintas. Convierte y valida antes de construir el modelo. Fragmento de controlador con txtCantidad y mostrarError definidos:

<!-- example:fx-validation-quantity -->
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

Una fecha de DatePicker es [LocalDate](/handbook/java/dates/); verifica si hay valor y si satisface las reglas del dominio. Una `ComboBox<Libro>` utiliza [genéricos](/handbook/java/generics/) y retorna el objeto seleccionado con getValue(), que puede ser null. No confundas el texto mostrado con la identidad del objeto. Para reaccionar a cambios de entrada usa [eventos y listeners](/handbook/java/javafx/events/); no dupliques todas las reglas de negocio en cada listener.

## Casos de prueba

| Entrada | Resultado |
| --- | --- |
| Vacía o espacios | Rechazo, sin llamada al DAO |
| Más de 120 caracteres | Rechazo |
| Título válido | INSERT y refresco |
| Actualizar sin selección | Mensaje y ninguna escritura |
| Cancelar eliminación | No llamar al DAO |
| SQLException al insertar | Guardado no confirmado, conservar entrada |
| SQLException al recargar tras INSERT confirmado | Informar guardado confirmado y fallo de refresco |

Para fechas, comprueba que DatePicker.getValue() no sea null antes de convertir a java.sql.Date. Para números, captura NumberFormatException y aplica límites de dominio.

## Separar formato, regla y persistencia

| Frontera | Ejemplo | Resultado ante fallo |
|---|---|---|
| Formato de entrada | Convertir texto a número | Mostrar corrección requerida |
| Regla de dominio | Cantidad mayor que cero | Rechazar operación inválida |
| Acceso a datos | Restricción única o conexión | Comunicar fallo según contrato |
| Presentación posterior | Recargar tabla tras confirmar | Informar fallo de refresco sin negar la escritura confirmada |

Una condición de botón deshabilitado no sustituye ninguna de las reglas que protegen el estado. El guardado puede completarse y la recarga posterior fallar: son dos resultados distintos. Si el acceso falla en un punto en el que no se conoce si el motor confirmó, no reintentes ciegamente; informa incertidumbre y verifica el estado.

## Prueba de contraste

Configura un DAO de prueba que retorna una fila insertada y una operación de lectura que falla. Comprueba que la interfaz comunica que la escritura se confirmó pero que no pudo refrescarse. Luego invierte el escenario: falla insertar y se conserva el texto de entrada. Estos son criterios para la prueba gráfica, no resultados ejecutados.

## Conceptos relacionados

- [Controles de formulario](/handbook/java/javafx/controls/)
- [Properties y binding](/handbook/java/javafx/properties-and-binding/)

- [FXML y Controller](/handbook/java/javafx/fxml-controller/)
- [TableView](/handbook/java/javafx/table-view/)
- [CRUD](/handbook/java/jdbc/crud/)
- [Diagnóstico](/handbook/troubleshooting/)

## Verificación ejecutable

El módulo JavaFX de Academy verifica las reglas de entrada, las alertas reales y las ramas de guardado y refresco usando un DAO simulado. La confirmación de escritura es una respuesta del doble de prueba, no una transacción real. Consulta los informes Surefire del commit utilizado. Las fuentes están en `examples/java/handbook/maven/javafx/`.

## Documentación oficial

- [Controles JavaFX](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/package-summary.html)
- [Alert](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/Alert.html)
- [DatePicker](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/DatePicker.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
