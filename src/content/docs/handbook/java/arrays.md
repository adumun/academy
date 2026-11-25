---
title: "Arrays en Java"
description: "Longitud fija, índices, tipos, recorrido y arrays de referencias."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

Un array es un objeto con un número fijo de elementos de un mismo tipo declarado. Su longitud se fija al crearlo. Los índices comienzan en cero; la última posición válida es length - 1. Un array de referencias recién creado contiene null en sus posiciones; un array de int contiene ceros. Consulta [estructuras de datos](/handbook/foundations/data-structures/) y [tipos](/handbook/java/types/).

## Ejemplo ejecutable

Archivo `ArraysDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 ArraysDemo.java` y `java ArraysDemo` con JDK 21. No requiere dependencias externas.

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

## Documentación oficial

- [JLS 21: arrays](https://docs.oracle.com/javase/specs/jls/se21/html/jls-10.html)
- [Arrays, API Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Arrays.html)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-06/01-arrays.md); los ejemplos de esta ficha son independientes.
