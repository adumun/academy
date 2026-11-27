---
title: "Excepciones y try-with-resources"
description: "Errores, propagación, captura, recursos y diagnóstico."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

**Al terminar esta ficha:** Distinguir ausencia de datos, excepciones y responsabilidad de traducción en un límite de aplicación.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)

## Qué representa una excepción

Una excepción es un objeto que representa una condición excepcional: interrumpe el [flujo normal](/handbook/java/control-flow/) y transporta información sobre una operación que no pudo completarse normalmente. Lanzar (throw) transfiere el control; capturar (catch) permite tratar el fallo; declarar (throws) indica al llamador las excepciones comprobadas que pueden propagarse. Un recurso es algo que requiere gestión de ciclo de vida, como un archivo o una conexión; AutoCloseable es una [interfaz](/handbook/java/interfaces/) cuyo método close libera el recurso. No todas las excepciones son errores recuperables ni requieren ser capturadas en el mismo lugar.

| Tipo | Ejemplo | Tratamiento |
| --- | --- | --- |
| Checked | IOException, SQLException | Capturar o declarar throws |
| Unchecked | IllegalArgumentException, NullPointerException | No exige declaración; corregir causa o tratar según contrato |
| Error | OutOfMemoryError | No tratar como fallo de negocio ordinario |

## Propagación y responsabilidad

El método DAO puede declarar throws SQLException, mientras un controlador captura el fallo, registra diagnóstico adecuado y comunica un mensaje. Un catch vacío o un retorno de éxito tras un error oculta información relevante.

<!-- example:exceptions-dao-boundary -->
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

<!-- example:exceptions-recursosdemo -->
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

## Seguir el flujo cuando algo falla

![main llama a calcular, que llama a convertir. Si convertir lanza NumberFormatException, el flujo normal se interrumpe y la excepción se propaga hasta el catch compatible de main.](/diagrams/java-exception-propagation.svg)

Cuando se ejecuta throw, no se siguen las instrucciones normales de ese bloque. Java busca un catch compatible en las invocaciones activas; si no se encuentra, el fallo se propaga. finally suele ejecutar limpieza al salir, pero no es garantía frente a terminación abrupta de la JVM. No retornes desde finally para ocultar una excepción pendiente.

## Ejemplo autónomo: capturar en el límite

<!-- example:exceptions-propagaciondemo -->
```java
public class PropagacionDemo {

    private static int convertir(String texto) {
        return Integer.parseInt(texto);
    }

    public static void main(String[] args) {
        try {
            System.out.println(convertir("12"));
            System.out.println(convertir("abc"));
            System.out.println("No se alcanza");
        } catch (NumberFormatException exception) {
            System.out.println("Formato invalido");
        } finally {
            System.out.println("Fin");
        }
    }
}
```

Salida esperada: 12, Formato invalido, Fin. La segunda llamada no retorna normalmente; se omite la siguiente impresión. El catch trata el formato inválido sin presentar un dato ficticio. Si el método no puede recuperarse, puede propagar o traducir preservando la causa. Throwable es la raíz de la jerarquía de excepciones y errores; capturar todo indiscriminadamente oculta condiciones que el programa quizá no puede manejar.

## Conceptos relacionados

- [Connection](/handbook/java/jdbc/connection/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [Diagnóstico](/handbook/troubleshooting/)

## Verificación ejecutable

El DAO del fragmento es una interfaz de prueba con tres respuestas controladas; no se conecta a una base de datos. Se verifica ausencia, éxito y SQLException.

Fuentes: `examples/java/handbook/exceptions/`. Ejecuta `npm run validate:snippets` y `npm run test:java-sources` con JDK 21.

## Documentación oficial

- [Exceptions, Dev.java](https://dev.java/learn/exceptions/)
- [AutoCloseable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/AutoCloseable.html)
- [SQLException](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/SQLException.html)
