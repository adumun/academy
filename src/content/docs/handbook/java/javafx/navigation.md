---
title: "Navegación entre vistas JavaFX"
description: "Stage, Scene, FXMLLoader, controladores y estado compartido."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Modelo mental

Stage es la ventana; Scene contiene el árbol visual. Navegar puede significar cambiar la escena o la raíz sin crear otra ventana. FXMLLoader construye una vista y su controlador; cada carga puede crear nuevas instancias. El estado que debe sobrevivir a la navegación debe pertenecer a un objeto compartido con responsabilidad clara, no a campos estáticos de los controles.

## Ejemplo: un Stage, dos vistas

Fragmento de una clase Application que ya conserva el Stage y un servicio compartido. Cada archivo FXML declara su controlador; configura antes de mostrar la vista. Imports: javafx.fxml.FXMLLoader, javafx.scene.Parent, javafx.scene.Scene, java.io.IOException, java.util.Objects.

<!-- example:fx-navigation -->
```java
private void mostrarDetalle() throws IOException {
    FXMLLoader loader = new FXMLLoader(
            Objects.requireNonNull(getClass().getResource("/view/detalle.fxml")));
    Parent raiz = loader.load();
    DetalleController controlador = loader.getController();
    controlador.configurar(servicio, this::volverAlListado);
    stage.setScene(new Scene(raiz));
}
```

El controlador expone configurar para recibir el servicio y un Runnable de retorno. initialize se ejecuta durante load, antes de configurar: no uses allí dependencias que todavía no llegaron. Maneja el fallo de carga en el límite de interacción; no anuncies navegación exitosa si el recurso no existe.

## Comprobación

Abre listado, navega a detalle y vuelve tres veces. Debe mantenerse una ventana y el mismo estado compartido. Cancelar no modifica datos; guardar válido actualiza el modelo. Un error en el archivo FXML no debe abrir ventanas duplicadas ni perder el estado. Consulta [FXML](/handbook/java/javafx/fxml-controller/) y [eventos](/handbook/java/javafx/events/).

## Conservar el estado sin acoplar controladores

![Una Application mantiene un Stage y el estado compartido. Al cargar listado o detalle crea su controlador y le entrega la misma referencia al servicio.](/diagrams/javafx-navigation-state.svg)

La Application o un coordinador posee la ventana y las dependencias compartidas. Cargar una vista con un FXMLLoader nuevo puede crear otro controlador y otro árbol; si cada controlador crea su propio servicio en memoria, los datos se separan accidentalmente. Si se conserva un servicio común, la nueva vista puede reconstruir su presentación a partir del mismo estado.

| Decisión | Cuándo usarla |
|---|---|
| stage.setScene(...) | Cambiar la escena completa en la misma ventana |
| scene.setRoot(...) | Sustituir el árbol conservando la Scene |
| Nuevo Stage | Otra ventana, diálogo o herramienta independiente |
| Reutilizar vista | Preservar controles cuando exista una necesidad real y se gestione su ciclo de vida |

## Traza de navegación

1. Inicializar una sola instancia del servicio.
2. Cargar listado, obtener su controlador y configurar dependencias.
3. Al solicitar detalle, cargar el segundo FXML y entregar la misma instancia del servicio.
4. Al guardar válido, modificar el estado a través del servicio; al cancelar, no modificarlo.
5. Volver a listado y recuperar el estado actual. Retirar suscripciones de vistas descartadas cuando corresponda.

Si la carga de detalle falla, conserva la escena anterior: crea y configura el nuevo árbol antes de sustituir el actual. El ejemplo original emplea un Runnable para retornar; es una interfaz funcional sin parámetros ni retorno, consulta [lambdas](/handbook/java/lambdas/). La navegación no implica almacenamiento persistente.

## Verificación ejecutable

La prueba utiliza dos vistas y un servicio compartido de ejemplo. Proyecto Maven: `examples/java/handbook/maven/javafx/`. En Linux con pantalla virtual, ejecuta `xvfb-run -a mvn -B -ntp clean test` desde esa carpeta. Consulta los informes Surefire del SHA utilizado.

## Referencias

- [FXMLLoader](https://openjfx.io/javadoc/21/javafx.fxml/javafx/fxml/FXMLLoader.html)
- [Stage](https://openjfx.io/javadoc/21/javafx.graphics/javafx/stage/Stage.html)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/labs/semana-10/03-navegacion.md). Los ejemplos no requieren un proyecto de negocio específico.
