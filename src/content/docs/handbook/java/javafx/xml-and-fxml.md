---
title: "XML y FXML"
description: "Etiquetas, atributos, estructura, espacios de nombres y construcción de vistas."
---

**Tipo:** explicación y referencia. **Nivel:** esencial.

## Qué es XML

XML es un formato de texto estructurado. Un elemento tiene una etiqueta de apertura y cierre (o una etiqueta autocerrada), puede contener otros elementos y atributos. Un documento XML bien formado tiene un elemento raíz, nombres válidos, atributos entre comillas y elementos correctamente anidados.

<!-- example:fx-xml-book -->
```xml
<libro id="1">
    <titulo>Java</titulo>
</libro>
```

libro es el elemento raíz; id es un atributo y titulo un elemento hijo. XML describe estructura, no determina por sí mismo qué acciones realiza una aplicación.

## Qué añade FXML

FXML es un formato XML que FXMLLoader interpreta para construir objetos JavaFX. Las etiquetas pueden representar clases y propiedades; los atributos pueden configurar valores o referencias.

<!-- example:fx-xml-button -->
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

## Del documento al árbol de objetos

El anidamiento representa relaciones estructurales. En el ejemplo FXML, VBox es el nodo contenedor y Button es un hijo; children representa la propiedad de colección que los conecta.

```text
VBox
└── children
    └── Button
        ├── text = Guardar
        └── fx:id = btnGuardar
```

Una etiqueta XML distingue mayúsculas y minúsculas. Los espacios de nombres califican nombres: el prefijo fx se asocia al espacio de nombres de FXML, no a una variable Java. El identificador fx:id no equivale al texto visible.

| Forma | Significado |
|---|---|
| `<Button/>` | Elemento autocerrado |
| text="Guardar" | Atributo de configuración |
| `<children>...</children>` | Elemento que representa una propiedad |
| fx:controller="app.FormController" | Clase asociada al documento |
| onAction="#aceptar" | Referencia a un manejador del controlador |

Los caracteres reservados del XML requieren escape cuando se utilizan como datos, por ejemplo `&amp;` para el ampersand.

## Dos niveles de validación

Un documento puede ser XML bien formado pero FXML inválido. Por ejemplo, un atributo con nombre de propiedad inexistente puede respetar la sintaxis XML y fallar al construir el objeto JavaFX. Una etiqueta sin cerrar falla antes, en el análisis del documento. Ante una LoadException, inspecciona la causa y la línea del archivo en lugar de modificar nombres al azar.

## Conceptos relacionados

- [Paquetes e imports](/handbook/java/packages-and-imports/)
- [Eventos y listeners](/handbook/java/javafx/events/)
- [FXML y Controller](/handbook/java/javafx/fxml-controller/)

## Verificación ejecutable

El módulo JavaFX de Academy carga el recurso FXML real, analiza el XML de datos y contrasta un XML mal formado con un FXML de propiedad inexistente. Consulta los informes Surefire del commit utilizado. Las fuentes están en `examples/java/handbook/maven/javafx/`.

## Documentación oficial

- [W3C: XML](https://www.w3.org/XML/)
- [OpenJFX: Introduction to FXML](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)
