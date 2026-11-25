---
title: "Pruebas automatizadas en Java con JUnit"
description: "JUnit Jupiter, aserciones, casos negativos y temporales."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Modelo mental

Una [prueba](/handbook/foundations/testing/) define un resultado esperado bajo condiciones controladas. JUnit Jupiter identifica métodos de prueba anotados con @Test, ejecuta cada caso y registra fallos. Una aserción compara expectativas, no imprime solamente valores.

## Ejemplo de prueba

Requiere un proyecto Maven Java 21 con JUnit Jupiter 5 configurado como dependencia test y Maven Surefire compatible. Es un archivo de pruebas, no un programa con main. Guarda como src/test/java/RangoTest.java.

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RangoTest {

    private int amplitud(int minimo, int maximo) {
        if (minimo > maximo) {
            throw new IllegalArgumentException("Rango invalido");
        }
        return maximo - minimo;
    }

    @Test
    void rangoValido() {
        assertEquals(5, amplitud(3, 8));
    }

    @Test
    void rechazaRangoInvertido() {
        assertThrows(IllegalArgumentException.class, () -> amplitud(8, 3));
    }
}
```

Ejecuta `mvn test` y comprueba que detecta y ejecuta dos tests. No interpretar BUILD SUCCESS sin verificar número de pruebas y que no se omitieron. El ejemplo no se ha ejecutado con JUnit en este incremento.

## Archivos y casos negativos

JUnit puede inyectar un directorio temporal mediante `@TempDir Path carpeta`. Úsalo en pruebas de [archivos](/handbook/java/files/) y JSON inválido. Prepara datos independientes, actúa y comprueba: archivo inexistente, válido, corrupto, fallo de escritura. No utilizar datos reales del usuario ni depender de pruebas anteriores.

## Referencias

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/puentes-unidad-02/10-pruebas-evidencia-y-junit.md). Los ejemplos no requieren un proyecto de negocio específico.
