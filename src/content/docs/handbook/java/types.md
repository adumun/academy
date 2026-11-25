---
title: "Tipos de datos y conversiones"
description: "Primitivos, referencias, String, wrappers y compatibilidad de tipos."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

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

```java
int edad = 21;
Integer edadOpcional = null;
String titulo = "Java";
boolean disponible = true;
```

Son declaraciones independientes. No uses el valor cero como sustituto universal de ausencia.

## Conversión y análisis de texto

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
