---
title: "Variables, valores y constantes"
description: "Declaración, inicialización, asignación, alcance y final."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

## Qué es una variable en Java

Consulta [valores, variables y tipos](/handbook/foundations/values-and-types/) para la definición independiente del lenguaje. Aquí se desarrolla la semántica de almacenamiento, asignación y alcance en Java.

Una variable es un lugar de almacenamiento identificado por un nombre que contiene un valor de un [tipo](/handbook/java/types/). El tipo determina qué valores puede contener y qué operaciones son válidas. Declarar una variable no es lo mismo que asignarle un valor.

```java
int cantidad = 3;
cantidad = 5;
final int MAXIMO = 10;
```

En la primera línea, int es el tipo, cantidad el nombre, 3 el valor inicial y = el operador de asignación. En la segunda, se sustituye el valor almacenado por 5. MAXIMO no puede reasignarse después de su inicialización.

| Término | Significado |
| --- | --- |
| Declaración | Introduce el nombre y tipo de una variable |
| Inicialización | Le proporciona su primer valor |
| Asignación | Guarda un valor compatible |
| Alcance | Región del programa en que el nombre está disponible |
| final | Impide reasignar la variable después de inicializarla |

## Variables locales, parámetros y campos

Una variable local pertenece a un bloque o método. Un parámetro recibe un argumento al llamar a un [método](/handbook/java/methods/). Un campo pertenece a una clase o instancia y puede representar parte del estado de un objeto. Las variables locales deben estar definitivamente asignadas antes de leerse; los campos tienen valores iniciales por defecto según su tipo, aunque conviene inicializarlos explícitamente.

```java
public class VariablesDemo {

    private int total = 0;

    public void sumar(int cantidad) {
        int resultado = total + cantidad;
        total = resultado;
    }

    public static void main(String[] args) {
        VariablesDemo demo = new VariablesDemo();
        demo.sumar(3);
        demo.sumar(2);
        System.out.println(demo.total);
    }
}
```

Archivo VariablesDemo.java. Salida esperada: 5. total es un campo, cantidad un parámetro y resultado una variable local. El acceso a resultado termina al salir del método.

## Valor primitivo y referencia

Una variable int contiene un valor entero. Una variable Libro contiene una [referencia](/handbook/java/references/) a un objeto Libro o null; copiar esa variable no duplica el objeto. final impide cambiar la referencia guardada, no garantiza la inmutabilidad del objeto.

## Errores habituales

- Variable might not have been initialized: proporciona un valor válido antes de leerla.
- Cannot assign a value to final variable: revisa si realmente debía ser constante.
- Cannot find symbol: verifica nombre, alcance y ortografía.
- Incompatible types: revisa el tipo y la conversión, no agregues casts indiscriminados.

## Conceptos relacionados

- [Tipos de datos](/handbook/java/types/)
- [Expresiones y operadores](/handbook/java/expressions/)
- [Referencias y null](/handbook/java/references/)
- [Clases y objetos](/handbook/java/classes-and-objects/)

## Documentación oficial

- [JLS 21: Variables](https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.12)
- [JLS 21: Scope](https://docs.oracle.com/javase/specs/jls/se21/html/jls-6.html#jls-6.3)
