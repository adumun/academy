---
title: "FXML y Controller"
description: "Vincular vistas XML, campos, eventos, recursos y ciclo de inicialización."
---

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Eventos y listeners](/handbook/java/javafx/events/)

## Contrato entre vista y controlador

[FXML](/handbook/java/javafx/xml-and-fxml/) es XML especializado para describir la vista; una etiqueta puede representar una clase y un atributo configura un valor. La vista es la estructura presentada al usuario; el controlador es el objeto que coordina la interacción (consulta [MVC y DAO](/handbook/architecture/)). FXMLLoader interpreta el documento, construye los [objetos](/handbook/java/classes-and-objects/) y conecta sus [campos](/handbook/java/variables/) y [métodos](/handbook/java/methods/) con su controlador. Inyección significa que el cargador asigna referencias a los objetos construidos en los campos del controlador; @FXML es una anotación que marca miembros para el cargador. fx:controller identifica la clase; fx:id permite inyectar controles; onAction invoca un [manejador de eventos](/handbook/java/javafx/events/) compatible.

## Ejemplo completo de vista y controlador

Ubica libro.fxml en src/main/resources/view/. La clase del controlador vive en src/main/java/controller/.

```xml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.VBox?>

<VBox xmlns="http://javafx.com/javafx/21"
      xmlns:fx="http://javafx.com/fxml/1"
      fx:controller="controller.LibroController"
      spacing="10">
    <TextField fx:id="txtTitulo" promptText="Título"/>
    <Button text="Mostrar" onAction="#mostrar"/>
    <Label fx:id="lblResultado"/>
</VBox>
```

```java
package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LibroController {

    @FXML
    private TextField txtTitulo;

    @FXML
    private Label lblResultado;

    @FXML
    private void initialize() {
        lblResultado.setText("Esperando entrada");
    }

    @FXML
    private void mostrar() {
        lblResultado.setText(txtTitulo.getText());
    }
}
```

Carga la vista desde Main.start(Stage stage) en lugar de construir VBox por código. Imports: FXMLLoader, Scene y Objects.

```java
FXMLLoader loader = new FXMLLoader(
        Objects.requireNonNull(
                getClass().getResource("/view/libro.fxml")
        )
);

Scene scene = new Scene(loader.load(), 480, 320);
stage.setScene(scene);
stage.show();
```

## Ciclo de inicialización

El constructor se ejecuta antes de la inyección FXML; no accedas allí a controles anotados. initialize se invoca tras cargar el documento e inyectar los campos. Los eventos llegan posteriormente al interactuar.

## API habitual

| Elemento | Uso |
| --- | --- |
| FXMLLoader(URL) | Identificar el recurso |
| load() | Crear la vista; puede lanzar IOException |
| getController() | Obtener el controlador cargado |
| @FXML | Permitir acceso a miembros asociados al documento |
| initialize() | Configurar controles después de la inyección |

Si eliges configurar un controlador programáticamente mediante setController, no declares también fx:controller en el mismo FXML.

## Diagnóstico

| Síntoma | Revisión |
| --- | --- |
| Location is not set | Ruta del recurso y empaquetado Maven |
| LoadException | Línea FXML, imports y causa original |
| Campo null en initialize | fx:id exacto, @FXML y controlador |
| Botón sin acción | onAction y nombre del método |
| Error de acceso modular | opens del paquete a javafx.fxml |

El ejemplo es no modular. Si utilizas module-info.java, debes declarar las dependencias y los permisos de reflexión necesarios. Reflexión es la capacidad de inspeccionar o acceder a tipos y miembros durante la ejecución, sujeta a las reglas de acceso y módulos. Consulta [paquetes, imports y classpath](/handbook/java/packages-and-imports/).

## Comprobación

Abre la ventana: debe mostrar Esperando entrada. Escribe un título y pulsa Mostrar; la etiqueta debe coincidir con la entrada. Cambia temporalmente el fx:id en una copia para observar el fallo y luego restáuralo.

## Conceptos relacionados

- [JavaFX](/handbook/java/javafx/)
- [TableView](/handbook/java/javafx/table-view/)
- [Controles y validación](/handbook/java/javafx/validation/)
- [MVC y DAO](/handbook/architecture/)

## Documentación oficial

- [Introducción a FXML](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)
- [FXMLLoader](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/FXMLLoader.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
