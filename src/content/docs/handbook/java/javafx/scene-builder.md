---
title: "Scene Builder y diseño de vistas FXML"
description: "Editar visualmente el árbol de controles sin ocultar el XML."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Qué hace Scene Builder

Scene Builder es un editor visual para [FXML](/handbook/java/javafx/xml-and-fxml/), no el runtime que ejecuta tu aplicación ni un generador de reglas de negocio. El resultado es XML legible y versionable. La interfaz puede diseñarse escribiendo FXML o mediante el editor; el [Controller](/handbook/java/javafx/fxml-controller/) sigue implementando los eventos.

## Flujo de trabajo

1. Abre un FXML de un proyecto JavaFX de práctica; agrega un VBox, Label, TextField y Button.
2. Configura el nombre completo del controller, fx:id del campo y onAction del botón.
3. Guarda en src/main/resources, revisa el XML y confirma imports y rutas.
4. Escribe el handler @FXML, carga el recurso con FXMLLoader y ejecuta con Maven.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns:fx="http://javafx.com/fxml/1" fx:controller="app.FormController">
    <children>
        <TextField fx:id="texto"/>
        <Button text="Aceptar" onAction="#aceptar"/>
    </children>
</VBox>
```

Es una vista que necesita la clase app.FormController con campo texto y método aceptar. No es un programa autónomo. Comprueba que pulsar el botón ejecuta el handler y que una ruta o nombre inválido se diagnostica en la copia de práctica.

## Del diseño al controlador

El editor visual modifica un documento. Al ejecutar la aplicación, FXMLLoader lee ese documento e instancia sus nodos; no ejecuta Scene Builder.

```text
Scene Builder ── guarda ──► vista.fxml
                                │
                         FXMLLoader.load()
                                │
                    árbol de nodos + Controller
                                │
                         Scene / Stage
```

| Configuración | Para qué sirve | Verificación |
|---|---|---|
| Hierarchy | Contención y orden de nodos | El Button pertenece al VBox |
| Properties | Texto y otras propiedades | El texto del botón coincide con FXML |
| Layout | Tamaño, alineación y espaciado | Redimensionar no corta los controles |
| Code | fx:id y manejadores | El nombre coincide con el controlador |

### Completar el controlador del ejemplo

Archivo app/FormController.java, con las dependencias JavaFX del proyecto. El campo texto corresponde exactamente al fx:id; el método aceptar al onAction.

```java
package app;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FormController {

    @FXML
    private TextField texto;

    @FXML
    private void aceptar() {
        System.out.println(texto.getText());
    }
}
```

Cargar el documento desde la aplicación descrita en [FXML y Controller](/handbook/java/javafx/fxml-controller/). Al escribir Hola y pulsar Aceptar, la salida esperada es Hola. Cambia deliberadamente el nombre del manejador en una copia y verifica el error de carga. El ejemplo requiere ejecución JavaFX real para confirmar el resultado.

## Referencias

- [Scene Builder](https://gluonhq.com/products/scene-builder/)
- [OpenJFX FXML](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-09/03-scene-builder-y-fxml.md). Los ejemplos no requieren un proyecto de negocio específico.
