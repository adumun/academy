---
title: "Lambdas e interfaces funcionales"
description: "Funciones como valores, parámetros, callbacks y referencias a métodos."
---

**Tipo:** explicación y referencia. **Nivel:** complementario. **Versión:** Java 21.

**Al terminar esta ficha:** Representar comportamiento con interfaces funcionales, lambdas y referencias a métodos.

## Conocimientos previos recomendados

- [Interfaces](/handbook/java/interfaces/)
- [Genéricos](/handbook/java/generics/)

## Qué es una lambda

Una lambda expresa una implementación de una [interfaz funcional](/handbook/java/interfaces/): una interfaz con un único método abstracto relevante. Un método abstracto especifica una operación sin su cuerpo. El tipo de destino determina los [parámetros y retorno](/handbook/java/methods/) esperados. Una [expresión](/handbook/java/expressions/) lambda permite describir una operación en el lugar donde otra API la necesita. Un callback es código entregado a otro componente para que lo invoque cuando corresponda; por ejemplo, un filtro llama a un Predicate para cada elemento.

<!-- example:lambdas-predicate -->
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

<!-- example:lambdas-lambdasdemo -->
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

## Declarar no es ejecutar

Una lambda tiene un tipo de destino, por ejemplo Predicate de String. La asignación crea u obtiene una implementación de ese contrato; el cuerpo se ejecuta cuando alguien llama a test, no al declarar la expresión. Una referencia a método indica una operación compatible sin invocarla en ese momento.

## Tres formas de expresar la misma operación

<!-- example:lambdas-predicadodemo -->
```java
import java.util.function.Predicate;

public class PredicadoDemo {

    private static boolean esPositivo(Integer valor) {
        return valor > 0;
    }

    public static void main(String[] args) {
        Predicate<Integer> primero = new Predicate<Integer>() {
            @Override
            public boolean test(Integer valor) {
                return valor > 0;
            }
        };
        Predicate<Integer> segundo = valor -> valor > 0;
        Predicate<Integer> tercero = PredicadoDemo::esPositivo;

        System.out.println(primero.test(2));
        System.out.println(segundo.test(-1));
        System.out.println(tercero.test(0));
    }
}
```

Salida esperada: true, false, false. La primera usa una clase anónima, la segunda una lambda y la tercera referencia a método. Cada variante cumple el mismo contrato boolean test(Integer); no son sintácticamente equivalentes en todos los aspectos (por ejemplo, this en una lambda conserva el significado del contexto, no introduce una instancia anónima propia). Un Predicate responde una pregunta; un Function transforma; un Consumer realiza una acción; un Supplier produce un valor sin entrada. Consulta [Streams](/handbook/java/streams/) para pipelines que reciben estas operaciones.

## Conceptos relacionados

- [Colecciones](/handbook/java/collections/)
- [Eventos JavaFX](/handbook/java/javafx/events/)
- [TableView](/handbook/java/javafx/table-view/)

## Verificación ejecutable

Los ejemplos y fragmentos están respaldados por fuentes Java en `examples/java/handbook/lambdas/`. La suite `LambdasTest` comprueba el contrato del concepto, además de las salidas de los ejemplos completos. Predice el resultado antes de ejecutar `npm run test:java-sources` con JDK 21; usa `npm run validate:snippets` para verificar sincronía.

## Documentación oficial

- [Lambda Expressions](https://dev.java/learn/lambdas/)
- [java.util.function](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/package-summary.html)
