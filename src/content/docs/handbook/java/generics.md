---
title: "Genéricos en Java"
description: "Parámetros de tipo, seguridad de tipos, límites, comodines e invariancia."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a avanzado. **Versión:** Java 21.

**Al terminar esta ficha:** Usar argumentos de tipo, métodos genéricos y comodines sin perder seguridad estática.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Interfaces y polimorfismo](/handbook/java/interfaces/)

## Qué problema resuelven

Un genérico parametriza un [tipo](/handbook/java/types/) o [método](/handbook/java/methods/). Parametrizar significa declarar una parte variable del contrato (por ejemplo T) y especificarla cuando se usa. Una caja de String y una caja de Integer tienen la misma implementación, pero aceptan valores de tipos distintos. El [compilador](/handbook/java/programming/) comprueba la compatibilidad antes de ejecutar. Una [colección](/handbook/java/collections/) es una estructura que agrupa elementos; sus genéricos indican qué elementos admite. Los límites y comodines avanzados presuponen la relación entre tipos e interfaces.

<!-- example:generics-typed-list -->
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

<!-- example:generics-caja -->
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

<!-- example:generics-first -->
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

<!-- example:generics-upper-bound -->
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

## Aplicaciones e implementaciones: JavaFX

[ObservableList](/handbook/java/javafx/observable-list/) parametriza el elemento observable. `TableView<Libro>` establece el tipo de fila; `TableColumn<Libro, String>` establece el tipo de fila y valor. Comprender esto permite leer firmas de API sin memorizar declaraciones.

## Diagnóstico y comprobación

Prueba `Caja<String>` y `Caja<Integer>`. Luego intenta asignar `Caja<Integer>` a `Caja<String>` en una copia: la compilación debe fallar. Si aparece incompatible types, revisa argumentos de tipo y retornos, no añadas casts arbitrarios.

## Visualizar un argumento de tipo

![Caja declara un parámetro T. Caja de String acepta texto y Caja de Integer acepta enteros envueltos; el compilador comprueba los tipos.](/diagrams/java-generic-types.svg)

T no es una variable de instancia: es un parámetro de tipo declarado por Caja. El mismo código genérico funciona para distintos argumentos compatibles. Al usar Caja de String, getValor retorna String según el tipo estático; no requiere un cast manual. Una colección raw pierde esa comprobación y puede desplazar el fallo a ejecución.

## Por qué la invariancia protege el contrato

Supón que se permitiera asignar una List de Integer a una List de Number. El segundo nombre podría agregar un Double; el primero ya no garantizaría enteros. Java evita esa asignación. Un comodín extends permite leer como supertipo, pero no insertar un valor arbitrario del supertipo (puede admitir null si la implementación lo permite). Un comodín super permite entregar valores del límite inferior compatible, pero solo garantiza lectura como Object.

### Ejemplo de método genérico

<!-- example:generics-choose -->
```java
public static <T> T elegir(boolean usarPrimero, T primero, T segundo) {
    if (usarPrimero) {
        return primero;
    }
    return segundo;
}
```

Fragmento dentro de una clase. elegir(true, "A", "B") retorna A; elegir(false, 1, 2) retorna 2 como Integer según inferencia y contexto. El parámetro de tipo se declara antes del retorno. Distingue un parámetro de tipo (T) de un parámetro de valor (primero). Los genéricos no constituyen por sí solos un mecanismo de validación de datos externos.

## Conceptos relacionados

- [Colecciones y List](/handbook/java/collections/)
- [Lambdas](/handbook/java/lambdas/)
- [TableView](/handbook/java/javafx/table-view/)

## Verificación ejecutable

Los ejemplos y fragmentos están respaldados por fuentes Java en `examples/java/handbook/generics/`. La suite `GenericsTest` comprueba el contrato del concepto, además de las salidas de los ejemplos completos. Predice el resultado antes de ejecutar `npm run test:java-sources` con JDK 21; usa `npm run validate:snippets` para verificar sincronía.

## Documentación oficial

- [Generics, Dev.java](https://dev.java/learn/generics/)
- [List, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html)
