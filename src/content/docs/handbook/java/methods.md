---
title: "Métodos, parámetros y valores de retorno"
description: "Firma, argumentos, retorno, void, static y sobrecarga."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

## Qué es un método

Un método es una operación con nombre declarada en una clase o interfaz. Su cuerpo puede ejecutar instrucciones, recibir datos mediante parámetros y devolver un resultado. El conjunto de operaciones de un objeto contribuye a su comportamiento.

```java
public int sumar(int primero, int segundo) {
    return primero + segundo;
}
```

Es un fragmento de clase. public indica acceso; int es el [tipo](/handbook/java/types/) retornado; sumar es el nombre; primero y segundo son [variables](/handbook/java/variables/) de parámetro. return termina la ejecución y devuelve el resultado.

## Llamada, parámetros y argumentos

```java
public class MetodosDemo {

    public static int sumar(int primero, int segundo) {
        return primero + segundo;
    }

    public static void main(String[] args) {
        int resultado = sumar(3, 4);
        System.out.println(resultado);
    }
}
```

Archivo MetodosDemo.java. Salida esperada: 7. Los argumentos son 3 y 4; se asignan a los parámetros al realizar la llamada. static permite invocar el método sin crear una instancia. void indica que no se devuelve un valor.

## Firma, contrato y sobrecarga

La firma de un método Java considera el nombre y tipos de parámetros (con reglas adicionales para genéricos), no su tipo de retorno. Dos métodos pueden tener el mismo nombre con parámetros distintos: sobrecarga. Una [interfaz](/handbook/java/interfaces/) puede declarar un contrato que diferentes clases implementan.

Un contrato documenta entradas válidas, resultado, efectos secundarios y [excepciones](/handbook/java/exceptions/). Por ejemplo, buscarPorId puede devolver Optional vacío si no existe una fila, pero no debe ocultar un fallo SQL.

## Constructores

Un constructor inicializa una instancia. Se declara con el nombre de la clase y sin tipo de retorno. No es un método ordinario y no se invoca como uno. Consulta [clases y objetos](/handbook/java/classes-and-objects/).

## Diagnóstico

- Argumentos incompatibles: revisa número, orden y tipos.
- Missing return statement: asegúrate de que cada camino normal de un método con retorno produzca un valor.
- Non-static method cannot be referenced from a static context: necesitas una instancia o una operación realmente estática.

## Conceptos relacionados

- [Expresiones y operadores](/handbook/java/expressions/)
- [Control de flujo](/handbook/java/control-flow/)
- [Interfaces](/handbook/java/interfaces/)
- [Lambdas](/handbook/java/lambdas/)

## Documentación oficial

- [JLS 21: Method Declarations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.4)
- [JLS 21: Constructor Declarations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.8)
