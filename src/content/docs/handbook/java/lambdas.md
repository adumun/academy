---
title: "Lambdas e interfaces funcionales"
description: "Funciones como valores, parámetros, callbacks y referencias a métodos."
---

**Tipo:** explicación y referencia. **Nivel:** complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Interfaces](/handbook/java/interfaces/)
- [Genéricos](/handbook/java/generics/)

## Qué es una lambda

Una lambda expresa una implementación de una interfaz funcional: una interfaz con un único método abstracto relevante. El tipo de destino determina los parámetros y retorno esperados.

```java
Predicate<String> noVacio = texto -> !texto.isBlank();
System.out.println(noVacio.test("Java"));
```

Importa java.util.function.Predicate. La salida es true. El parámetro texto se infiere como String.

## Formas habituales

| Forma | Significado |
| --- | --- |
| () -> realizarAccion() | Sin argumentos |
| texto -> texto.length() | Un argumento, retorno de expresión |
| (a, b) -> a.compareTo(b) | Dos argumentos |
| valor -> { return valor * 2; } | Cuerpo de bloque |

Los tipos se pueden declarar cuando sea necesario. Una expresión puede omitir return; un bloque que devuelve valor debe retornarlo según el contrato.

## Interfaces funcionales

| Tipo | Método abstracto |
| --- | --- |
| `Predicate<T>` | boolean test(T) |
| `Function<T,R>` | R apply(T) |
| `Consumer<T>` | void accept(T) |
| `Supplier<T>` | T get() |
| `Comparator<T>` | int compare(T,T) |

## Ejemplo completo

```java
import java.util.ArrayList;
import java.util.List;

public class LambdasDemo {

    public static void main(String[] args) {
        List<String> titulos = new ArrayList<>(
                List.of("Java", "", "SQL")
        );

        titulos.removeIf(titulo -> titulo.isBlank());
        titulos.forEach(System.out::println);
    }
}
```

Salida: Java y SQL. removeIf recibe un Predicate; forEach un Consumer. System.out::println es una referencia a método compatible.

## Captura de variables y efectos

Una lambda puede usar variables locales final o efectivamente final. Evita efectos inesperados en operaciones de filtrado: un Predicate debería decidir si un elemento coincide, no escribir en la base de datos.

## En JavaFX

Una cell value factory recibe información de la fila y devuelve un valor observable. Un listener recibe los parámetros definidos por su interfaz. No memorices flechas: identifica primero el tipo funcional y la firma esperada.

## Comprobación

Añade un título en blanco al ejemplo y comprueba que se elimina. Cambia el predicado para seleccionar títulos largos. Si aparece incompatible parameter types, revisa la interfaz funcional de destino.

## Conceptos relacionados

- [Colecciones](/handbook/java/collections/)
- [Eventos JavaFX](/handbook/java/javafx/events/)
- [TableView](/handbook/java/javafx/table-view/)

## Documentación oficial

- [Lambda Expressions](https://dev.java/learn/lambdas/)
- [java.util.function](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/package-summary.html)
