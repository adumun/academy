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

Cada enlace apunta al snapshot exacto documentado. Descarga el código de un checkpoint para contrastar tu implementación y no para sustituir el desarrollo. La guía original y sus capturas están en la [clase consolidada](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/README.md).

### R00 — Arranque y entorno

**Trabajo y prueba:** Configura JDK 21 y Maven. Ejecuta la ventana inicial y comprueba que no compila con una configuración accidental de Java 8.

[Fuentes del checkpoint](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/tree/f3408276766358884c1c12f4c0b3ed63211bc36f) · [ZIP fijo](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/archive/f3408276766358884c1c12f4c0b3ed63211bc36f.zip).

### R01 — Modelo

**Trabajo y prueba:** Crea MensajeFuturo con autor, texto y plazoAnios; prueba por consola la construcción y getters.

[Fuentes del checkpoint](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/tree/13a21df0cfc536c0bcc016f2bae8f063058291dc) · [ZIP fijo](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/archive/13a21df0cfc536c0bcc016f2bae8f063058291dc.zip).

### R02 — Tabla

**Trabajo y prueba:** Presenta mensajes de prueba mediante TableView y ObservableList; verifica filas y columnas.

[Fuentes del checkpoint](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/tree/44511026ba156c4ed863ed45f987e4b7709b9179) · [ZIP fijo](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/archive/44511026ba156c4ed863ed45f987e4b7709b9179.zip).

### R03 — Formulario

**Trabajo y prueba:** Integra la entrada en la misma vista. Registra un mensaje válido y rechaza campos vacíos; el estado aún es memoria.

[Fuentes del checkpoint](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/tree/ec77d7341973f21d14d29a437ba00c3adf9bd56c) · [ZIP fijo](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/archive/ec77d7341973f21d14d29a437ba00c3adf9bd56c.zip).

### R04 — Dos vistas

**Trabajo y prueba:** Separa formulario y listado en FXML; navega conservando los mensajes en memoria. No añadas almacenamiento todavía.

[Fuentes del checkpoint](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/tree/09e5b99e9dce71998333f6c2718ff29040d9b00e) · [ZIP fijo](https://github.com/cmartinezs/DSY1102-JFX-TIME-CAPSULE/archive/09e5b99e9dce71998333f6c2718ff29040d9b00e.zip).

## Alternativa A — JSON (R05 y R06)

### R05 — Service, DAO y JSON

Separa MensajeService, contrato MensajeDao e implementación JsonMensajeDao. Integra Jackson y guarda los mensajes al registrar. Compara la tabla con el JSON después de uno y dos registros. El origen registra once capturas reales y la versión Jackson 2.22.4, pero no congela todavía un snapshot de R05: consulta la [matriz de código y enlaces históricos](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/CODIGO-POR-INCREMENTO.md), sin confundir la rama de trabajo con una versión aceptada.

### R06 — Recuperación y corrupción

Recupera los mensajes al iniciar, verifica un tercer registro, respalda el archivo y trata JSON inválido o claves duplicadas sin sobrescribir datos. El origen documenta capturas de alertas, preservación .corrupt, restauración manual y trece tests JUnit exitosos. No afirma recuperación automática desde .bak ni captura de la ejecución posterior a la restauración manual específica. Consulta [evidencias históricas R06](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/docs/EVIDENCIAS-R06.md), [NIO.2](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/docs/GUIA-NIO2-PATH-FILES-R06.md) y [Streams/lambdas](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/docs/GUIA-STREAMS-LAMBDAS-R06.md). Estos resultados pertenecen a la evidencia histórica del docente; ejecuta tus propias pruebas.

## Alternativa B — JDBC (R07 y R08)

Parte de una copia aislada de R04, no del recorrido JSON. Conserva modelo y dos vistas. Configura MariaDB local y credenciales fuera del repositorio; utiliza [Connection](/handbook/java/jdbc/connection/), [PreparedStatement](/handbook/java/jdbc/prepared-statement/) y [ResultSet](/handbook/java/jdbc/result-set/).

### R07 — Conexión e INSERT

Implementa la conexión, el contrato DAO, escritura parametrizada y validación. Guarda un mensaje y comprueba la fila con una consulta SQL independiente. Todavía no se recupera al reiniciar la UI. [Guía de configuración y código R07](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/jdbc/01-r07-conexion-insert-validacion.md).

### R08 — SELECT y carga de tabla

Sustituye únicamente el JdbcMensajeDao indicado por la guía: implementa SELECT, recorre ResultSet y mapea al modelo; la UI carga los datos. Prueba cerrar y abrir JavaFX y recuperar todas las filas. [Guía R08](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/jdbc/02-r08-resultset-modelo-tableview.md) · [Archivos Java, SQL y configuración de referencia](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/jdbc/README.md).

**Límite:** las guías JDBC están publicadas en el repositorio docente, pero la ejecución integral MariaDB no está certificada. R07/R08 no son snapshots del repositorio de aplicación ni reemplazan R05/R06. No extrapolar las pruebas JSON al recorrido SQL.

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

[Historia de la clase](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/README.md), [matriz de commits](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/CODIGO-POR-INCREMENTO.md) y [extensión JDBC](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/jdbc/README.md), fuente docente fijada al 25-11-2026. La presentación original mantiene sus capturas y navegación. Practica habilidades puntuales en [Detective JSON](https://academic-labs.adumun.cl/activities/detective-json/) o [JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/).
