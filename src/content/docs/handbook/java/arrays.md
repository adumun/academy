---
title: "Arrays en Java"
description: "Longitud fija, índices, tipos, recorrido y arrays de referencias."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

**Al terminar esta ficha:** Distinguir longitud, índices, alias y copia de arreglos.

## Modelo mental

Un array es un objeto con un número fijo de elementos de un mismo tipo declarado. Su longitud se fija al crearlo. Los índices comienzan en cero; la última posición válida es length - 1. Un array de referencias recién creado contiene null en sus posiciones; un array de int contiene ceros. Consulta [estructuras de datos](/handbook/foundations/data-structures/) y [tipos](/handbook/java/types/).

## Ejemplo ejecutable

Archivo `ArraysDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 ArraysDemo.java` y `java ArraysDemo` con JDK 21. No requiere dependencias externas.

<!-- example:arrays-arraysdemo -->
```java
public class ArraysDemo {

    public static void main(String[] args) {
        int[] mediciones = {10, 20, 30};
        int total = 0;

        for (int medicion : mediciones) {
            total += medicion;
        }

        System.out.println(mediciones.length);
        System.out.println(mediciones[1]);
        System.out.println(total);
    }
}
```

Salida esperada:

```text
3
20
60
```

## API y errores

| Operación | Significado |
|---|---|
| new int[3] | Crea tres posiciones inicializadas a cero |
| array.length | Longitud (campo, no método) |
| array[indice] | Leer o escribir la posición válida |
| for-each | Recorrer valores |
| Arrays.copyOf | Crear otro array y copiar elementos |

Un índice negativo o igual a length provoca ArrayIndexOutOfBoundsException. Un array no crece; copia a otro array o utiliza [List](/handbook/java/collections/) si necesitas altas y bajas frecuentes. La copia de un array de referencias no duplica los objetos.

## Visualizar longitud e índices

![La variable mediciones referencia un array de tres elementos. Los índices 0, 1 y 2 contienen 10, 20 y 30; length vale 3.](/diagrams/java-array-slots.svg)

La variable contiene una referencia al array. Su tipo determina qué elementos pueden almacenarse; length es la longitud de esa instancia y no se modifica. Un array de longitud cero es válido y no tiene índices utilizables. Distingue posición de valor: el índice 1 del ejemplo contiene 20.

### Reasignar, copiar y recorrer

<!-- example:arrays-alias-copy -->
```java
int[] original = {10, 20, 30};
int[] alias = original;
alias[1] = 99;
System.out.println(original[1]); // 99
int[] copia = java.util.Arrays.copyOf(original, 5);
System.out.println(copia.length); // 5
System.out.println(copia[4]); // 0
```

Fragmento dentro de un método. Asignar copia la referencia; copyOf crea otro array con longitud solicitada y copia valores. En arrays de referencias eso no implica copiar objetos. Si la longitud solicitada es menor, se truncan elementos. Para recorrer posiciones usa un índice desde 0 hasta length exclusivo. La expresión new int[-1] provoca NegativeArraySizeException.

## Autocomprobación

Crea un array vacío, otro de tres valores y uno de String de longitud dos. Predice sus valores iniciales y qué ocurre al leer la última posición. Consulta [referencias](/handbook/java/references/) y [colecciones](/handbook/java/collections/) para distinguir alias y estructuras redimensionables.

## Verificación ejecutable

Los ejemplos y fragmentos están respaldados por fuentes Java en `examples/java/handbook/arrays/`. La suite `ArraysTest` comprueba el contrato del concepto, además de las salidas de los ejemplos completos. Predice el resultado antes de ejecutar `npm run test:java-sources` con JDK 21; usa `npm run validate:snippets` para verificar sincronía.

## Documentación oficial

- [JLS 21: arrays](https://docs.oracle.com/javase/specs/jls/se21/html/jls-10.html)
- [Arrays, API Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-06/01-arrays.md); los ejemplos de esta ficha son independientes.
