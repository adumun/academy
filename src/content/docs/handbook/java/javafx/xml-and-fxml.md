---
title: "XML y FXML"
description: "Etiquetas, atributos, estructura, espacios de nombres y construcción de vistas."
---

**Tipo:** explicación y referencia. **Nivel:** esencial.

## Qué es XML

XML es un formato de texto estructurado. Un elemento tiene una etiqueta de apertura y cierre (o una etiqueta autocerrada), puede contener otros elementos y atributos. Un documento XML bien formado tiene un elemento raíz, nombres válidos, atributos entre comillas y elementos correctamente anidados.

```xml
<libro id="1">
    <titulo>Java</titulo>
</libro>
```

libro es el elemento raíz; id es un atributo y titulo un elemento hijo. XML describe estructura, no determina por sí mismo qué acciones realiza una aplicación.

## Qué añade FXML

FXML es un formato XML que FXMLLoader interpreta para construir objetos JavaFX. Las etiquetas pueden representar clases y propiedades; los atributos pueden configurar valores o referencias.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <Button fx:id="btnGuardar" text="Guardar"/>
    </children>
</VBox>
```

Este documento crea un VBox con un botón. La instrucción import permite usar nombres de clases; xmlns:fx declara un prefijo de espacio de nombres; fx:id identifica un objeto para el cargador y puede vincularse a un campo del controlador. Para manejar una acción necesitas el controlador y su evento, según [FXML y Controller](/handbook/java/javafx/fxml-controller/).

## Diagnóstico

XML mal formado puede impedir cargar la vista. Un nombre de clase o propiedad incorrecto también puede fallar aunque el XML esté bien formado. Revisa el error y su causa, la ubicación del recurso, las dependencias y el nombre completo del controlador.

## Conceptos relacionados

- [Paquetes e imports](/handbook/java/packages-and-imports/)
- [Eventos y listeners](/handbook/java/javafx/events/)
- [FXML y Controller](/handbook/java/javafx/fxml-controller/)

## Documentación oficial

- [W3C: XML](https://www.w3.org/XML/)
- [OpenJFX: Introduction to FXML](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)
