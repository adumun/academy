---
title: "Referencias, identidad y null"
description: "Diferencia entre objetos y referencias, alias, igualdad, ausencia y copia."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

## Objeto y referencia

Los [objetos, identidad y estado](/handbook/foundations/objects/) son conceptos generales. Aquí se explica cómo Java representa y copia referencias y cómo utiliza null.

Un [objeto](/handbook/java/classes-and-objects/) es una instancia creada en ejecución. Una referencia es un valor que permite acceder a ese objeto. Una [variable](/handbook/java/variables/) de [tipo de referencia](/handbook/java/types/) contiene una referencia o null, no una copia automática del objeto.

```java
StringBuilder primero = new StringBuilder("Java");
StringBuilder segundo = primero;
segundo.append("FX");

System.out.println(primero);
```

Salida esperada: JavaFX. Ambas variables permiten acceder al mismo objeto mutable. La asignación copia la referencia, no el objeto.

## Identidad, igualdad y copia

| Operación | Qué pregunta |
| --- | --- |
| a == b (referencias) | ¿Son la misma referencia, o ambas null? |
| a.equals(b) | ¿Son iguales según el contrato de la clase? |
| new Clase(...) | Crea otra instancia |
| new ArrayList<>(lista) | Crea otra lista; comparte referencias a sus elementos |

Un objeto puede ser inmutable, como String: sus operaciones no cambian su estado observable. Una variable final puede apuntar a un objeto mutable.

## Ejemplo de ausencia

```java
public class ReferenciasDemo {

    public static void main(String[] args) {
        String titulo = null;

        if (titulo == null) {
            System.out.println("Sin título");
        } else {
            System.out.println(titulo.length());
        }
    }
}
```

Archivo ReferenciasDemo.java. Salida esperada: Sin título. Invocar un método de instancia mediante una referencia null produce NullPointerException. No confundas null, una cadena vacía, una lista vacía y un error de acceso a base de datos.

## Paso de argumentos

Java pasa argumentos por valor. Si un argumento es una referencia, el método recibe una copia de esa referencia. Puede modificar un objeto mutable compartido, pero reasignar su parámetro no reasigna la variable del llamador.

## Visualizar alias y reasignación

![Las variables primero y segundo contienen referencias al mismo StringBuilder, cuyo contenido cambia a JavaFX. Otra instancia con el mismo contenido tiene identidad propia.](/diagrams/java-reference-alias.svg)

En el diagrama A y B son etiquetas conceptuales, no direcciones de memoria ni valores de puntero que Java permita manipular. Dos referencias pueden compartir un objeto (alias); reasignar una de ellas cambia esa variable, no el objeto ni las otras variables.

## Ejemplo ejecutable: paso por valor

Archivo AliasDemo.java, Java 21 sin dependencias.

```java
public class AliasDemo {

    private static void modificar(StringBuilder texto) {
        texto.append("B");
        texto = new StringBuilder("C");
        texto.append("D");
    }

    public static void main(String[] args) {
        StringBuilder primero = new StringBuilder("A");
        StringBuilder segundo = primero;
        modificar(segundo);
        System.out.println(primero);
        System.out.println(segundo);
        System.out.println(primero == segundo);
    }
}
```

Salida esperada: AB, AB y true (una línea por valor). La primera llamada append modifica el objeto compartido. La asignación posterior solo cambia el parámetro local texto; el nuevo objeto CD no reemplaza primero ni segundo.

## Autocomprobación

Cambia segundo por una instancia nueva antes de invocar modificar. Predice los dos textos y la identidad. Para igualdad lógica, consulta [equals y hashCode](/handbook/java/equality/).

## Conceptos relacionados

- [Clases, objetos y encapsulamiento](/handbook/java/classes-and-objects/)
- [Métodos](/handbook/java/methods/)
- [Colecciones](/handbook/java/collections/)
- [Optional](/handbook/java/optional/)

## Documentación oficial

- [JLS 21: Reference Types and Values](https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.3)
- [Object](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html)
