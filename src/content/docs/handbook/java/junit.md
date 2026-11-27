---
title: "Pruebas automatizadas en Java con JUnit"
description: "JUnit Jupiter, aserciones, casos negativos y temporales."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Modelo mental

Una [prueba](/handbook/foundations/testing/) define un resultado esperado bajo condiciones controladas. JUnit Jupiter identifica métodos de prueba anotados con @Test, ejecuta cada caso y registra fallos. Una aserción compara expectativas, no imprime solamente valores.

## Ejemplo de implementación y prueba

Requiere un proyecto Maven Java 21 con JUnit Jupiter 5 configurado como dependencia test y Maven Surefire compatible. El código de producción se guarda como src/main/java/Rango.java. La prueba invoca ese código, en lugar de duplicar su algoritmo.

<!-- example:rango-source -->
```java
public final class Rango {
    private Rango() {
    }

    public static long amplitud(int minimo, int maximo) {
        if (minimo > maximo) {
            throw new IllegalArgumentException("Rango invalido");
        }
        return (long) maximo - minimo;
    }
}
```

Guarda la siguiente prueba como src/test/java/RangoTest.java. No es un programa con main.

<!-- example:rango-junit -->
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RangoTest {
    @Test
    void rangoValido() {
        assertEquals(5L, Rango.amplitud(3, 8));
    }

    @Test
    void rechazaRangoInvertido() {
        assertThrows(IllegalArgumentException.class, () -> Rango.amplitud(8, 3));
    }
}
```

Ejecuta `mvn -Dtest=RangoTest test` y comprueba que detecta y ejecuta dos tests. No interpretar BUILD SUCCESS sin verificar número de pruebas y que no se omitieron. Rango utiliza long para representar la diferencia completa de dos int; el proyecto incluye otras dos pruebas de límites.

## Archivos y casos negativos

JUnit puede inyectar un directorio temporal mediante `@TempDir Path carpeta`. Úsalo en pruebas de [archivos](/handbook/java/files/) y JSON inválido. Prepara datos independientes, actúa y comprueba: archivo inexistente, válido, corrupto, fallo de escritura. No utilizar datos reales del usuario ni depender de pruebas anteriores.

## Preparar un proyecto de pruebas reproducible

En un proyecto Maven Java 21 no modular, agrega esta dependencia en dependencies y el plugin en build/plugins. Son versiones de ejemplo fijadas de JUnit 5 y Surefire, no una afirmación de que sean las últimas.

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.11.4</version>
    <scope>test</scope>
</dependency>
```

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.2</version>
</plugin>
```

La dependencia test proporciona APIs para compilar y ejecutar pruebas, sin integrar JUnit en el código productivo. Surefire ejecuta las pruebas durante la fase test; JUnit Jupiter proporciona anotaciones y aserciones.

```text
src/main/java/  → código productivo
src/test/java/  → pruebas del comportamiento
mvn test        → compilación + ejecución + resultados
```

## Interpretar las aserciones

assertEquals compara un resultado esperado con el observado; assertTrue comprueba una condición; assertThrows comprueba que la operación lanza una excepción compatible con el tipo requerido. Una prueba debe fallar si la implementación deja de cumplir el contrato. Cambia temporalmente el retorno del cálculo a cero y confirma que rangoValido falla; restaura el código antes de continuar.

RangoTest invoca la implementación productiva Rango. Cambiarla en una copia temporal debe hacer fallar rangoValido y conservar la prueba de rango invertido. Esto verifica que la prueba detecta una regresión concreta, no que cubra todos los defectos. Una prueba unitaria no sustituye una prueba de integración con MariaDB ni una prueba de interacción JavaFX.

## Verificación ejecutable

Proyecto real: `examples/java/handbook/maven/json-junit/`. Ejecuta `mvn -B -ntp clean test` desde esa carpeta. El proyecto contiene dos pruebas RangoTest, dos pruebas de frontera y doce de serialización. Consulta `target/surefire-reports/`; la comprobación automatizada de regresión deliberada se realiza en una copia desechable, sin editar el código canónico.

## Referencias

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/puentes-unidad-02/10-pruebas-evidencia-y-junit.md). Los ejemplos no requieren un proyecto de negocio específico.
