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

## Referencias

- [Scene Builder](https://gluonhq.com/products/scene-builder/)
- [OpenJFX FXML](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-09/03-scene-builder-y-fxml.md). Los ejemplos no requieren un proyecto de negocio específico.
