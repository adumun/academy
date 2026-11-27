---
title: "Cápsula del Tiempo FX"
description: "Proyecto de JavaFX con recorridos alternativos JSON y JDBC, checkpoints y evidencia."
---

**Tipo:** proyecto incremental con variantes de persistencia. **Entorno:** JDK 21, Maven, JavaFX. **Modelo:** MensajeFuturo (autor, texto, plazoAnios). No confundir con el ejemplo independiente Capsula del Handbook/labs JDBC.

## Problema

Construye una aplicación de escritorio para registrar mensajes destinados al futuro, mostrarlos en una tabla y conservarlos entre ejecuciones. Avanza por checkpoints reproducibles. Primero construye la UI en memoria; después elige una alternativa de persistencia. No es necesario implementar ambas en un mismo proyecto.

## Conocimientos previos recomendados

- [Maven](/handbook/tools/maven/)
- [Clases y objetos](/handbook/java/classes-and-objects/)
- [JavaFX](/handbook/java/javafx/)
- [FXML y Controller](/handbook/java/javafx/fxml-controller/)
- [TableView](/handbook/java/javafx/table-view/)

## Tramo común R00–R04

Cada enlace apunta al proyecto Maven adaptado en Academy. Clona el repositorio de la organización, abre la carpeta del incremento con JDK 21 y Maven instalado; ejecuta `mvn compile` y `mvn javafx:run`. Los fuentes de cada etapa se conservan por separado. Las adaptaciones todavía no están certificadas mediante ejecución nueva.

### R00 — Arranque y entorno

**Trabajo y prueba:** Configura JDK 21 y Maven. Ejecuta la ventana inicial y comprueba que no compila con una configuración accidental de Java 8.

[Proyecto Maven de referencia R00](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r00).

### R01 — Modelo

**Trabajo y prueba:** Crea MensajeFuturo con autor, texto y plazoAnios; prueba por consola la construcción y getters.

[Proyecto Maven de referencia R01](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r01).

### R02 — Tabla

**Trabajo y prueba:** Presenta mensajes de prueba mediante TableView y ObservableList; verifica filas y columnas.

[Proyecto Maven de referencia R02](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r02).

### R03 — Formulario

**Trabajo y prueba:** Integra la entrada en la misma vista. Registra un mensaje válido y rechaza campos vacíos; el estado aún es memoria.

[Proyecto Maven de referencia R03](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r03).

### R04 — Dos vistas

**Trabajo y prueba:** Separa formulario y listado en FXML; navega conservando los mensajes en memoria. No añadas almacenamiento todavía.

[Proyecto Maven de referencia R04](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r04).

## Alternativa A — JSON (R05 y R06)

### R05 — Service, DAO y JSON

Separa MensajeService, contrato MensajeDao e implementación JsonMensajeDao. Integra Jackson y guarda los mensajes al registrar. Compara la tabla con el JSON después de uno y dos registros. [Proyecto Maven R05](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r05). Se fijó el código de la rama de trabajo como referencia de Academy; no equivale a una versión históricamente aprobada. Ejecuta `mvn clean test` antes de usar el proyecto.

### R06 — Recuperación y corrupción

Recupera los mensajes al iniciar, verifica un tercer registro, respalda el archivo y trata JSON inválido o claves duplicadas sin sobrescribir datos. [Proyecto Maven R06 con pruebas JUnit](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r06) · [Guía NIO.2](/projects/time-capsule-fx/nio2/) · [Guía Streams y lambdas](/projects/time-capsule-fx/streams/). Ejecuta `mvn clean test` y practica con datos descartables. El origen documentó trece tests JUnit exitosos, pero estos resultados históricos no certifican la adaptación. No hay restauración automática desde `.bak`.

## Alternativa B — JDBC (R07 y R08)

Parte de una copia aislada de R04, no del recorrido JSON. Conserva modelo y dos vistas. Configura MariaDB local y credenciales fuera del repositorio; utiliza [Connection](/handbook/java/jdbc/connection/), [PreparedStatement](/handbook/java/jdbc/prepared-statement/) y [ResultSet](/handbook/java/jdbc/result-set/).

### R07 — Conexión e INSERT

Implementa la conexión, el contrato DAO, escritura parametrizada y validación. Guarda un mensaje y comprueba la fila con una consulta SQL independiente. Todavía no se recupera al reiniciar la UI. [Guía R07](/projects/time-capsule-fx/r07/) · [Proyecto Maven y SQL R07](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r07).

### R08 — SELECT y carga de tabla

Sustituye únicamente el JdbcMensajeDao indicado por la guía: implementa SELECT, recorre ResultSet y mapea al modelo; la UI carga los datos. Prueba cerrar y abrir JavaFX y recuperar todas las filas. [Guía R08](/projects/time-capsule-fx/r08/) · [Proyecto Maven y SQL R08](https://github.com/adumun/academy/tree/8c7b1daf78e1560a49b0e5f2037ddaed04c2c207/examples/java/projects/time-capsule-fx/r08).

**Límite:** los proyectos JDBC se construyeron a partir de R04 y los archivos de referencia. La ejecución integral con MariaDB no está certificada. R07/R08 no reemplazan R05/R06. No extrapolar las pruebas JSON al recorrido SQL.

## Aceptación de tu implementación

| Prueba | Resultado |
|---|---|
| Campos válidos | Mensaje visible en la tabla |
| Entrada vacía | Rechazo sin anunciar guardado |
| Navegar y regresar | Conserva datos del tramo en memoria |
| Reiniciar con JSON válido o BD disponible | Recupera datos en la variante elegida |
| Fallo de acceso / JSON inválido | Mensaje claro y no destruir datos existentes |

Conserva evidencia de la ejecución local, versión, datos de prueba, decisión de persistencia y resultado. No confundas un archivo o fila guardada con una tabla refrescada. No incluyas contraseñas ni datos personales.

## Procedencia

Adaptación de material didáctico propio. Los commits, documentos originales y límites de la adaptación están registrados en el manifiesto editorial de Academy. Las capturas históricas no se incorporaron a esta versión. Practica habilidades puntuales en [Detective JSON](https://academic-labs.adumun.cl/activities/detective-json/) o [JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/).
