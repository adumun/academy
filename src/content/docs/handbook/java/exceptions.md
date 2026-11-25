---
title: "Excepciones y try-with-resources"
description: "Errores, propagación, captura, recursos y diagnóstico."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)

## Qué representa una excepción

Una excepción interrumpe el flujo normal y transporta información sobre una operación que no pudo completarse normalmente. No todas las excepciones son errores recuperables ni requieren ser capturadas en el mismo lugar.

| Tipo | Ejemplo | Tratamiento |
| --- | --- | --- |
| Checked | IOException, SQLException | Capturar o declarar throws |
| Unchecked | IllegalArgumentException, NullPointerException | No exige declaración; corregir causa o tratar según contrato |
| Error | OutOfMemoryError | No tratar como fallo de negocio ordinario |

## Propagación y responsabilidad

El método DAO puede declarar throws SQLException, mientras un controlador captura el fallo, registra diagnóstico adecuado y comunica un mensaje. Un catch vacío o un retorno de éxito tras un error oculta información relevante.

```java
try {
    int filas = dao.eliminar(id);

    if (filas == 0) {
        mostrarError("No se encontró el registro.");
    }
} catch (SQLException e) {
    mostrarError("No se pudo eliminar.");
}
```

Fragmento de controlador con dao, id y mostrarError definidos. No significa que la operación sea exitosa si aparece una excepción.

## Try-with-resources

AutoCloseable define close(). Try-with-resources administra automáticamente recursos declarados en el encabezado, incluso ante excepciones. Los cierra en orden inverso.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class RecursosDemo {

    public static void main(String[] args) throws IOException {
        try (BufferedReader lector =
                     new BufferedReader(new StringReader("Java"))) {
            System.out.println(lector.readLine());
        }
    }
}
```

Archivo RecursosDemo.java. Salida: Java. BufferedReader se cierra al salir del bloque.

En [JDBC](/handbook/java/jdbc/), Connection, PreparedStatement y ResultSet son recursos cerrables. No devuelvas un ResultSet cuya conexión ya se cerró; transforma sus filas dentro del alcance del recurso.

## Diagnóstico · complementario

SQLException ofrece getSQLState(), getErrorCode() y getNextException(). El stack trace y getCause() ayudan a identificar la causa. Si el cuerpo del try y close() fallan, la excepción de cierre puede quedar suprimida; getSuppressed() permite inspeccionarla. Nunca registres contraseñas o tokens.

## Comprobación

Prueba una entrada válida, una inválida y un fallo de recurso. Verifica que el sistema no comunique éxito ante fallo y que el diagnóstico identifique la frontera. No uses excepciones como sustituto de una validación sencilla.

## Conceptos relacionados

- [Connection](/handbook/java/jdbc/connection/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [Diagnóstico](/handbook/troubleshooting/)

## Documentación oficial

- [Exceptions, Dev.java](https://dev.java/learn/exceptions/)
- [AutoCloseable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/AutoCloseable.html)
- [SQLException](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/SQLException.html)
