---
title: "Genéricos en Java"
description: "Parámetros de tipo, seguridad de tipos, límites, comodines e invariancia."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a avanzado. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Interfaces y polimorfismo](/handbook/java/interfaces/)

## Qué problema resuelven

Un genérico parametriza un tipo o método. Permite expresar qué clase de valor acepta o devuelve una estructura sin escribir una implementación diferente para cada tipo. El compilador comprueba la compatibilidad antes de ejecutar.

```java
List<String> titulos = new ArrayList<>();
titulos.add("Java");
String primero = titulos.get(0);
```

El fragmento usa java.util.List y java.util.ArrayList. La lista acepta String. Intentar agregar un Integer produciría un error de compilación. Sin genéricos, una colección de Object obliga a verificar y convertir manualmente los valores.

## Anatomía de un tipo parametrizado

| Expresión | Significado |
| --- | --- |
| `Caja<T>` | Declaración con parámetro de tipo T |
| `Caja<String>` | Uso con argumento de tipo String |
| new Caja<>(...) | Inferencia de argumentos (diamond) |
| `Map<Integer, String>` | Dos argumentos de tipo |
| `TableColumn<Libro, String>` | Tipo de fila y tipo del valor de columna |

T, E, K y V son convenciones, no palabras reservadas. Un parámetro debe representar un propósito. Los argumentos de tipo no pueden ser primitivos: usa Integer en lugar de int.

## Ejemplo completo: clase genérica

Archivo Caja.java. No requiere JavaFX ni dependencias externas.

```java
public class Caja<T> {

    private final T valor;

    public Caja(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public static void main(String[] args) {
        Caja<String> texto = new Caja<>("Java");
        Caja<Integer> numero = new Caja<>(21);

        System.out.println(texto.getValor());
        System.out.println(numero.getValor());
    }
}
```

Salida: Java y 21, en líneas distintas. Ejecuta javac Caja.java y java Caja con JDK 21.

## Métodos genéricos y límites · complementario

Un método puede declarar sus propios parámetros aunque su clase no sea genérica.

```java
public static <T> T primero(List<T> elementos) {
    if (elementos.isEmpty()) {
        throw new IllegalArgumentException("Lista vacía");
    }

    return elementos.get(0);
}
```

El método utiliza una List no nula. El compilador infiere T a partir del argumento. Un límite restringe los tipos permitidos, por ejemplo `<T extends Number>` permite utilizar métodos de Number.

## Invariancia y comodines · avanzado

`List<Integer>` no es subtipo de `List<Number>`, aunque Integer sea subtipo de Number. De otro modo podríamos agregar un Double a la lista de enteros.

| Tipo | Lectura y escritura |
| --- | --- |
| `List<? extends Number>` | Leer como Number; no agregar números arbitrarios |
| `List<? super Integer>` | Agregar Integer; leer como Object |
| `List<?>` | Leer como Object; tipo concreto desconocido |

```java
public static double sumar(List<? extends Number> valores) {
    double total = 0;

    for (Number valor : valores) {
        total += valor.doubleValue();
    }

    return total;
}
```

Ejemplo de cálculo aproximado, no apropiado para dinero. Los comodines expresan flexibilidad cuando el método consume o produce valores.

## Límites en ejecución

La mayoría de la información de parámetros de tipo se borra durante la compilación (type erasure). Por eso no puedes crear new T() sin otra estrategia ni comprobar instanceof `List<String>`. No uses tipos raw como List para silenciar errores: pierdes comprobaciones.

## Aplicación en Academy

[ObservableList](/handbook/java/javafx/observable-list/) parametriza el elemento observable. `TableView<Libro>` establece el tipo de fila; `TableColumn<Libro, String>` establece el tipo de fila y valor. Comprender esto permite leer firmas de API sin memorizar declaraciones.

## Diagnóstico y comprobación

Prueba `Caja<String>` y `Caja<Integer>`. Luego intenta asignar `Caja<Integer>` a `Caja<String>` en una copia: la compilación debe fallar. Si aparece incompatible types, revisa argumentos de tipo y retornos, no añadas casts arbitrarios.

## Conceptos relacionados

- [Colecciones y List](/handbook/java/collections/)
- [Lambdas](/handbook/java/lambdas/)
- [TableView](/handbook/java/javafx/table-view/)

## Documentación oficial

- [Generics, Dev.java](https://dev.java/learn/generics/)
- [List, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html)
