---
title: "Métodos, parámetros y valores de retorno"
description: "Firma, argumentos, retorno, void, static y sobrecarga."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

## Qué es un método en Java

Las [operaciones, parámetros y contratos](/handbook/foundations/operations/) existen independientemente del lenguaje. Aquí se documentan las reglas de declaración, invocación y retorno de métodos en Java.

Un método es una operación con nombre declarada en una clase o interfaz. Su cuerpo puede ejecutar instrucciones, recibir datos mediante parámetros y devolver un resultado. El conjunto de operaciones de un objeto contribuye a su comportamiento.

<!-- example:method-instance -->
```java
public int sumar(int primero, int segundo) {
    return primero + segundo;
}
```

Es un fragmento de clase. public indica acceso; int es el [tipo](/handbook/java/types/) retornado; sumar es el nombre; primero y segundo son [variables](/handbook/java/variables/) de parámetro. return termina la ejecución y devuelve el resultado.

## Llamada, parámetros y argumentos

<!-- example:method-static -->
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

## Anatomía de una declaración

| Parte | Ejemplo | Función |
|---|---|---|
| Acceso | public | Determina quién puede usar el método según reglas de acceso |
| Modificador | static | Asocia el método con la clase; no tiene this |
| Retorno | int | Tipo de resultado, o void si no devuelve valor |
| Nombre y parámetros | sumar(int primero, int segundo) | Identifican la firma en este caso |
| Cuerpo | return primero + segundo; | Ejecuta la operación |

Una llamada evalúa sus argumentos y crea parámetros locales con sus valores. Un return con expresión entrega un valor compatible con el retorno; return sin expresión puede terminar un método void. La salida impresa en consola no equivale al valor devuelto al llamador.

## Ejemplo ejecutable: sobrecarga y estado

Archivo SobrecargaDemo.java, Java 21.

<!-- example:method-overload -->
```java
public class SobrecargaDemo {

    private int total = 0;

    public void agregar(int cantidad) {
        total += cantidad;
    }

    public void agregar(int primera, int segunda) {
        agregar(primera + segunda);
    }

    public int consultar() {
        return total;
    }

    public static void main(String[] args) {
        SobrecargaDemo acumulador = new SobrecargaDemo();
        acumulador.agregar(2);
        acumulador.agregar(3, 4);
        System.out.println(acumulador.consultar());
    }
}
```

Salida esperada: 9. La sobrecarga con dos argumentos delega a la operación con uno; no duplica el cambio de estado. consultar retorna el valor sin imprimirlo. La elección de sobrecarga ocurre durante compilación, a diferencia del despacho dinámico de métodos sobrescritos.

## Comprobación ejecutable

Los tres ejemplos de esta ficha se mantienen como fuentes Java en `examples/java/handbook/methods/`. El primer bloque es un método de instancia extraído de una clase compilable; los otros dos son clases completas. Desde la raíz del repositorio, `npm run test:java-sources` compila las fuentes y ejecuta pruebas de comportamiento, incluidas las salidas publicadas. `npm run validate:snippets` comprueba que el texto mostrado coincida con las fuentes.

La suma de enteros usa la aritmética de `int`: si supera el máximo representable, se produce desbordamiento. Para rechazarlo explícitamente existe `Math.addExact`. Cada instancia del acumulador conserva su propio total; un argumento negativo lo reduce porque este ejemplo no impone una regla de negocio.

## Autocomprobación

¿Qué ocurre si declaras otro agregar(int cantidad) con retorno int? No basta cambiar el retorno para distinguir la firma. Intenta llamar consultar directamente desde main sin una instancia y explica el diagnóstico.

## Conceptos relacionados

- [Expresiones y operadores](/handbook/java/expressions/)
- [Control de flujo](/handbook/java/control-flow/)
- [Interfaces](/handbook/java/interfaces/)
- [Lambdas](/handbook/java/lambdas/)

## Documentación oficial

- [JLS 21: Method Declarations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.4)
- [JLS 21: Constructor Declarations](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.8)
