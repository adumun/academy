---
title: "Eventos y listeners en JavaFX"
description: "Programación dirigida por eventos, handlers, cambios y responsabilidades."
---

**Tipo:** explicación y referencia. **Nivel:** complementario. **Versión:** Java 21, JavaFX 21.

## Conocimientos previos recomendados

- [Interfaces](/handbook/java/interfaces/)
- [Lambdas](/handbook/java/lambdas/)
- [JavaFX](/handbook/java/javafx/)

## Evento frente a cambio de propiedad

Un evento representa una interacción u ocurrencia, como pulsar un botón. Un listener (escuchador) es código registrado para recibir notificaciones; un handler (manejador) procesa un evento concreto. Un listener de [propiedad](/handbook/java/javafx/properties-and-binding/) observa el cambio de un valor. Suscripción significa registrar el callback; notificación significa invocarlo cuando se cumple la condición. El callback puede expresarse mediante una [lambda](/handbook/java/lambdas/) y recibe [parámetros](/handbook/java/methods/) según su interfaz. Ambos permiten reaccionar sin recorrer continuamente la interfaz.

| API | Semántica |
| --- | --- |
| `EventHandler<ActionEvent>` | Maneja un evento de acción |
| Button.setOnAction(handler) | Configura manejador de acción |
| `ChangeListener<T>` | Recibe observable, valor anterior y valor nuevo |
| ObservableValue.addListener(listener) | Suscribe cambios de valor |
| ObservableValue.removeListener(listener) | Retira la suscripción |

## Ejemplo de acción

<!-- example:fx-button-action -->
```java
Button guardar = new Button("Guardar");
guardar.setOnAction(evento -> guardarLibro());
```

Fragmento de UI con importar Button y método guardarLibro implementado. El handler se ejecuta cuando se dispara la acción, no al declarar la lambda.

En FXML, onAction="#guardarLibro" delega a un método del controlador. La instancia debe estar inicializada correctamente.

## Escuchar cambios

<!-- example:fx-text-listener -->
```java
txtTitulo.textProperty().addListener(
        (observable, anterior, actual) -> {
            btnGuardar.setDisable(actual.isBlank());
        }
);
```

Fragmento de initialize() con TextField y Button inyectados. Deshabilitar un botón mejora experiencia, pero no reemplaza validación al ejecutar la operación.

## Selección de fila

<!-- example:fx-row-listener -->
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

Un cambio de selección puede originarse por el usuario o por una recarga de la lista. No presupongas que siempre hay un objeto seleccionado.

## Límites y diseño

Los callbacks de la UI normalmente se ejecutan en el hilo JavaFX. Si la tarea tarda, la interfaz puede quedar bloqueada. No hagas una consulta SQL por cada tecla sin evaluar necesidad, filtrado local o una estrategia de debounce (esperar un breve período sin nuevas entradas antes de ejecutar una acción). Evita listeners duplicados al recargar una vista y retíralos cuando corresponda al ciclo de vida.

El [patrón Observer](/handbook/architecture/observer/) aporta el modelo mental de suscripción y notificación, aunque las APIs concretas de JavaFX tienen sus propios contratos.

## Comprobación

Escribe texto, borra la entrada, selecciona una fila y limpia selección. Verifica que el estado del formulario se actualiza sin invocar el DAO por accidente.

## Dos mecanismos de reacción

![Un clic genera ActionEvent e invoca un handler. Un cambio de texto notifica a ChangeListener con valor anterior y actual.](/diagrams/javafx-event-dispatch.svg)

Una acción no equivale necesariamente a un clic de ratón: Button puede disparar ActionEvent mediante teclado u otra interacción soportada. Un cambio de propiedad no tiene por qué proceder de un gesto: setText también puede notificar un cambio. No uses una notificación de propiedad como si fuera una confirmación de guardado.

### Ejemplo completo sin FXML

Archivo EventosDemo.java en un proyecto JavaFX 21 con javafx-controls; ejecuta con la configuración Maven de la ficha JavaFX.

<!-- example:fx-events-demo -->
```java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventosDemo extends Application {

    @Override
    public void start(Stage stage) {
        TextField entrada = new TextField();
        Label mensaje = new Label("Sin accion");
        Button aceptar = new Button("Aceptar");
        aceptar.setDisable(true);
        entrada.textProperty().addListener((observable, anterior, actual) -> {
            aceptar.setDisable(actual.isBlank());
        });
        aceptar.setOnAction(evento -> {
            mensaje.setText("Aceptado: " + entrada.getText());
        });
        stage.setScene(new Scene(new VBox(10, entrada, aceptar, mensaje), 340, 160));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

Resultado esperado: escribir texto habilita Aceptar, pero la etiqueta solo cambia al accionar el botón. Borrar el texto vuelve a deshabilitarlo. El listener mejora interacción; una operación de dominio aún debe validar su entrada. Ejecución JavaFX pendiente de verificación en un entorno con dependencias.

## Conceptos relacionados

- [FXML y Controller](/handbook/java/javafx/fxml-controller/)
- [ObservableList](/handbook/java/javafx/observable-list/)
- [TableView](/handbook/java/javafx/table-view/)

## Verificación ejecutable

Pruebas sobre las fuentes canónicas con el toolkit JavaFX real. Proyecto Maven: `examples/java/handbook/maven/javafx/`. En Linux con pantalla virtual, ejecuta `xvfb-run -a mvn -B -ntp clean test` desde esa carpeta. Consulta resultados Surefire del SHA utilizado; no equivale a revisión visual manual.

## Documentación oficial

- [EventHandler](https://openjfx.io/javadoc/21/javafx.base/javafx/event/EventHandler.html)
- [ChangeListener](https://openjfx.io/javadoc/21/javafx.base/javafx/beans/value/ChangeListener.html)
- [ButtonBase](https://openjfx.io/javadoc/21/javafx.controls/javafx/scene/control/ButtonBase.html)
