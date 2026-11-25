---
title: "Diagnóstico de errores"
description: "Procedimiento de depuración y catálogo de síntomas Java, JDBC y JavaFX."
---

## Método de diagnóstico

Diagnosticar consiste en identificar la causa de un comportamiento incorrecto mediante evidencia, no adivinar una solución. Un síntoma es lo que observas; la causa es lo que origina el fallo. Un caso mínimo reduce entradas y componentes hasta conservar solo lo necesario para reproducirlo. El stack trace (traza de pila) muestra llamadas y ubicaciones asociadas a una [excepción](/handbook/java/exceptions/); no todas las líneas representan la causa original.

1. Reproduce el problema con un caso mínimo.
2. Lee el mensaje completo y la primera causa relevante del stack trace.
3. Identifica la frontera: build, conexión, SQL, modelo, FXML o vista.
4. Formula una hipótesis comprobable.
5. Cambia una cosa y repite el caso.
6. Verifica que no se rompió el comportamiento anterior.

No cambies simultáneamente dependencias, credenciales, consultas y controladores: perderás la capacidad de identificar la causa.

## Build y conexión

| Síntoma | Comprobación inicial | Ficha |
| --- | --- | --- |
| Invalid target release | mvn -version y JDK | [Maven](/handbook/tools/maven/) |
| No suitable driver | Dependencia y URL | [Connection](/handbook/java/jdbc/connection/) |
| Connection refused | Servidor, host y puerto | [Connection](/handbook/java/jdbc/connection/) |
| Access denied | Usuario y permisos | [Connection](/handbook/java/jdbc/connection/) |
| Unknown database | Existencia y nombre del esquema | [SQL](/handbook/sql/) |

## SQL y mapeo

| Síntoma | Qué revisar |
| --- | --- |
| Parameter index out of range | Cantidad y posición de marcadores |
| Column not found | Nombre o alias del SELECT |
| Cursor inválido | Llamada a next() antes de leer |
| NullPointerException al convertir fecha | NULL SQL y getDate |
| UPDATE afecta varias filas | WHERE e ID, detener modificaciones inseguras |
| Se reciben cero filas afectadas | ID y semántica del conector |

## Interfaz JavaFX

| Síntoma | Qué revisar |
| --- | --- |
| FXML no encontrado | Classpath y ruta |
| LoadException | Causa original y línea del FXML |
| Campo @FXML null | fx:id, controlador y momento de inicialización |
| TableView con filas vacías | PropertyValueFactory y getters |
| Tabla sin filas | Resultado DAO, cargar(), excepciones |
| Interfaz congelada | Acceso bloqueante en hilo JavaFX |

## Usar breakpoints

Un breakpoint (punto de interrupción) detiene temporalmente la ejecución en una ubicación elegida para inspeccionar [variables](/handbook/java/variables/), llamadas y estado. Un depurador permite continuar o avanzar paso a paso. Coloca un breakpoint en el [evento del controlador](/handbook/java/javafx/events/) y otro en el DAO. Inspecciona el valor de título, ID y SQL parametrizado, avanza hasta executeUpdate() y revisa el conteo retornado. En una lectura, inspecciona el cursor y el tamaño de la colección después del bucle. Evita mostrar secretos en capturas.

## Ejemplo de diagnóstico aislado

La tabla aparece vacía tras guardar. Comprueba primero SELECT directo en MariaDB. Si existe el registro, la escritura funcionó; revisa el refresco del controlador. Si no existe, inspecciona filas afectadas, excepciones y transacción. No agregues objetos artificialmente a la TableView para esconder un fallo de persistencia.

## Receta de diagnóstico por capas · complementario

| Checkpoint | Evidencia mínima | Si falla |
| --- | --- | --- |
| Entorno | java -version y mvn -version | [Maven](/handbook/tools/maven/) |
| Conexión | isValid(3) | [Connection](/handbook/java/jdbc/connection/) |
| Consulta aislada | SELECT devuelve filas esperadas | [Consultas SQL](/handbook/sql/queries/) |
| DAO | Lista, identidad y conteo correcto | [CRUD](/handbook/java/jdbc/crud/) |
| Carga FXML | Controlador y campos inyectados | [FXML](/handbook/java/javafx/fxml-controller/) |
| Presentación | Items y columnas correctos | [TableView](/handbook/java/javafx/table-view/) |
| Persistencia | Reinicio y SELECT directo | [Transacciones](/handbook/java/jdbc/transactions/) |

Inspecciona las excepciones encadenadas mediante getCause(), getSuppressed() y, para SQLException, getNextException(). [Excepciones y try-with-resources](/handbook/java/exceptions/) explica por qué cerrar un recurso puede generar una excepción adicional. No confundas la línea superior del stack trace con la causa de negocio. Conserva el mensaje completo en un entorno local seguro, sin secretos.

## Evidencia útil

Registra entrada, resultado esperado, resultado observado, causa, cambio y nueva prueba. Un mensaje de éxito o una captura de la interfaz no son suficientes para demostrar que los datos persisten.

## Conceptos relacionados

- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [FXML](/handbook/java/javafx/fxml-controller/)
- [TableView](/handbook/java/javafx/table-view/)

## Documentación oficial

- [Depuración en IntelliJ IDEA](https://www.jetbrains.com/help/idea/debugging-code.html)
- [SQLException](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/SQLException.html)

## Practicar en Academic Labs

- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
