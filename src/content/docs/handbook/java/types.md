---
title: "Tipos de datos y conversiones"
description: "Primitivos, referencias, String, wrappers y compatibilidad de tipos."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

**Al terminar esta ficha:** Elegir tipos según el dominio, anticipar conversiones implícitas y explícitas, y reconocer pérdida de precisión, desbordamiento y ausencia.

## Qué es un tipo en Java

La definición general de [valor y tipo](/handbook/foundations/values-and-types/) se aplica a distintos lenguajes; esta página documenta los tipos y conversiones de Java.

Un tipo define un conjunto de valores posibles y las operaciones que se pueden realizar con ellos. Por ejemplo, int permite representar enteros de 32 bits con signo; boolean representa true o false. Un tipo de referencia como String permite representar una referencia a un objeto de esa clase o null.

Java comprueba tipos durante la compilación. Eso permite detectar antes de ejecutar errores como asignar texto a una [variable](/handbook/java/variables/) int.

## Tipos primitivos

| Tipo | Valores o representación |
| --- | --- |
| byte | Entero con signo de 8 bits |
| short | Entero con signo de 16 bits |
| int | Entero con signo de 32 bits |
| long | Entero con signo de 64 bits |
| float | Punto flotante binario de 32 bits |
| double | Punto flotante binario de 64 bits |
| char | Unidad de código UTF-16 de 16 bits |
| boolean | true o false |

Un char no representa necesariamente un carácter Unicode completo. float y double tienen precisión finita; para montos decimales exactos, considera BigDecimal y una regla explícita de redondeo.

## Tipos de referencia

Clases, interfaces y arreglos definen tipos de referencia. String representa texto y es inmutable; Integer es la clase envoltorio de int. A diferencia de int, Integer puede representar ausencia mediante null. Los [genéricos](/handbook/java/generics/) utilizan tipos de referencia como argumentos.

<!-- example:types-declaration -->
```java
int edad = 21;
Integer edadOpcional = null;
String titulo = "Java";
boolean disponible = true;
```

Son declaraciones independientes. No uses el valor cero como sustituto universal de ausencia.

## Conversión y análisis de texto

<!-- example:types-demo -->
```java
public class TiposDemo {

    public static void main(String[] args) {
        int cantidad = Integer.parseInt("12");
        long cantidadGrande = cantidad;
        double proporcion = 5.0 / 2;
        int truncado = (int) proporcion;

        System.out.println(cantidadGrande);
        System.out.println(proporcion);
        System.out.println(truncado);
    }
}
```

Archivo TiposDemo.java. Salida esperada: 12, 2.5 y 2. La ampliación int a long es implícita; el cast double a int trunca hacia cero en este ejemplo. Integer.parseInt analiza texto y puede lanzar NumberFormatException. La división 5 / 2, con ambos operandos int, produce 2.

## Inferencia y boxing

var en una declaración local con inicializador permite inferir el tipo estático, no convierte Java en un lenguaje de tipado dinámico. El boxing convierte automáticamente un primitivo a su envoltorio; el unboxing de una referencia null falla con NullPointerException.

## Cómo elegir el tipo y reconocer sus límites

| Tipo | Mínimo | Máximo |
|---|---|---|
| byte | -128 | 127 |
| short | -32768 | 32767 |
| int | -2147483648 | 2147483647 |
| long | -9223372036854775808 | 9223372036854775807 |
| char | 0 | 65535 (unidad UTF-16) |

Los enteros tienen límites: sumar 1 a Integer.MAX_VALUE con aritmética int ordinaria da Integer.MIN_VALUE por desbordamiento, no una excepción. Si el contrato exige detectar el desbordamiento, Math.addExact es una alternativa. La conversión ampliadora puede perder precisión cuando se pasa a punto flotante, aun sin cast explícito (por ejemplo int a float).

## Conversiones distintas, resultados distintos

<!-- example:types-conversions -->
```java
System.out.println(5 / 2);          // 2
System.out.println(5 / 2.0);        // 2.5
System.out.println((double) (5 / 2)); // 2.0
System.out.println((int) -2.9);     // -2
System.out.println((byte) 130);     // -126
```

Fragmento dentro de un método. En la tercera operación la división entera ocurre antes de convertir. El cast a byte conserva los bits bajos según las reglas de Java, no valida un rango de negocio. Para leer texto usa parseInt o la API adecuada y trata errores de conversión. Para dinero, BigDecimal construido desde texto decimal evita el error de representar fracciones binarias; define también escala y redondeo.

## Autocomprobación

Predice la salida de 7 / 3, 7 / 3.0 y (double) (7 / 3). Explica por qué Integer puede ser null pero int no. Comprueba qué ocurre al convertir "12a" con Integer.parseInt.

## Verificación del ejemplo

La conversión numérica y la validación son operaciones distintas. Una prueba de desbordamiento o estrechamiento comprueba la semántica de Java, no la validez del dato para un dominio.

Las fuentes versionadas de esta ficha se encuentran en `examples/java/handbook/types/`. Los fragmentos se extraen de clases compilables y los ejemplos completos se conservan íntegros. Ejecuta `npm run validate:snippets` y `npm run test:java-sources` desde la raíz del repositorio con Node.js y JDK 21.

## Práctica de diagnóstico

Antes de ejecutar, predice 5 / 2, 5 / 2.0 y (double) (5 / 2). Explica por qué un cast posterior no recupera la fracción perdida. Contrasta convertir un valor fuera de rango con validar el rango antes de convertirlo. Prueba Integer.parseInt con texto inválido: el error es de ejecución, a diferencia de una asignación incompatible que javac rechaza.

**Fuentes de los ejemplos mostrados:** [TypesFragmentsTest.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/types/TypesFragmentsTest.java) · [TiposDemo.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/types/TiposDemo.java).

Casos de rechazo verificados con javac 21: [IncompatibleType.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/negative/IncompatibleType.java). Son archivos deliberadamente inválidos, no ejemplos para copiar como solución.

## Conceptos relacionados

- [Variables](/handbook/java/variables/)
- [Referencias y null](/handbook/java/references/)
- [Expresiones y operadores](/handbook/java/expressions/)
- [Genéricos](/handbook/java/generics/)
- [Validación JavaFX](/handbook/java/javafx/validation/)

## Documentación oficial

- [JLS 21: Types, Values, and Variables](https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html)
- [JLS 21: Conversions and Contexts](https://docs.oracle.com/javase/specs/jls/se21/html/jls-5.html)
- [Integer](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Integer.html)
