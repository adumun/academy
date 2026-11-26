---
title: "Colecciones, List y ArrayList"
description: "Interfaces de colección, orden, operaciones, mutabilidad y selección de estructuras."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Genéricos](/handbook/java/generics/)

## Modelo mental

El modelo general de [estructuras de datos](/handbook/foundations/data-structures/) distingue secuencias, conjuntos y asociaciones. Una colección organiza [objetos](/handbook/java/classes-and-objects/) en memoria. Cada elemento es un valor; en una colección de objetos se almacenan [referencias](/handbook/java/references/). Un índice identifica una posición empezando desde cero, no la identidad del objeto. El parámetro E es un [genérico](/handbook/java/generics/) que describe el tipo de elemento. La interfaz es el contrato de operaciones, mientras que la implementación es la clase que las realiza (consulta [interfaces y polimorfismo](/handbook/java/interfaces/)). `List<E>` representa una secuencia ordenada que permite duplicados; `ArrayList<E>` es una implementación redimensionable. [Set](/handbook/java/sets/) evita duplicados según su contrato de [igualdad](/handbook/java/equality/); [Map](/handbook/java/maps/) asocia claves con valores y no implementa Collection.

| Necesidad | Alternativa habitual |
| --- | --- |
| Conservar secuencia y duplicados | List |
| Acceso por posición frecuente | ArrayList |
| Conjunto de valores únicos | Set |
| Buscar por clave | Map |

## API de List

| Método | Contrato |
| --- | --- |
| boolean add(E elemento) | Agrega al final, si es modificable |
| E get(int indice) | Retorna el elemento en posición; índice desde cero |
| E set(int indice, E elemento) | Reemplaza y retorna el anterior |
| E remove(int indice) | Elimina por posición |
| boolean remove(Object objeto) | Elimina la primera coincidencia |
| int size() | Cantidad de elementos |
| boolean isEmpty() | Si no hay elementos |
| boolean contains(Object objeto) | Si contiene una coincidencia |
| void clear() | Elimina todos, si admite modificación |

Una posición no es un ID de negocio. get(size()) falla, porque la última posición es size() - 1.

## Ejemplo autónomo

```java
import java.util.ArrayList;
import java.util.List;

public class ColeccionesDemo {

    public static void main(String[] args) {
        List<String> titulos = new ArrayList<>();
        titulos.add("Java");
        titulos.add("SQL");
        titulos.add("Java");

        for (String titulo : titulos) {
            System.out.println(titulo);
        }

        System.out.println("Total: " + titulos.size());
    }
}
```

Salida: Java, SQL, Java y Total: 3. Cada valor aparece en su propia línea.

## Mutabilidad y vistas · complementario

List.of("Java", "SQL") crea una lista no modificable y rechaza null. new ArrayList<>(otraLista) crea una colección independiente, pero no realiza copias profundas de los objetos. List.copyOf devuelve una lista no modificable con los elementos de la fuente en ese momento, rechazando null.

Para ordenar una lista modificable utiliza sort con un Comparator; para ordenar sin cambiar la original, copia primero. No elimines elementos durante un for-each ordinario: usa removeIf o un Iterator apropiado.

## De List a ObservableList

El DAO devuelve una List de objetos sin depender de JavaFX. Una [ObservableList](/handbook/java/javafx/observable-list/) añade notificaciones de cambios para los consumidores de la interfaz. Copiar objetos con setAll no genera escrituras SQL.

## Errores y pruebas

| Síntoma | Causa posible |
| --- | --- |
| IndexOutOfBoundsException | Índice inválido |
| UnsupportedOperationException | Operación no soportada en una lista no modificable |
| ConcurrentModificationException | Modificación estructural durante iteración incompatible |
| Listado vacío tras reiniciar | Colección en memoria sin persistencia |

Prueba duplicados, tamaño, lista vacía y eliminación por posición. Para `List<Integer>`, diferencia remove(1) de remove(Integer.valueOf(1)): el primero elimina por índice; el segundo por valor.

## Tamaño, índice y capacidad

![Una lista con A, B, C tiene índices 0, 1 y 2. Al eliminar B en índice 1, C pasa al índice 1. El tamaño cambia de 3 a 2.](/diagrams/java-list-index.svg)

El tamaño es el número de elementos actualmente almacenados. La capacidad de ArrayList se relaciona con el espacio interno y puede ser mayor; no se consulta como size. Cuando se elimina una posición, las siguientes se desplazan. Conservar un índice como identificador estable puede apuntar a otro elemento después de una modificación.

## Ejemplo autónomo: dos formas de eliminar

```java
import java.util.ArrayList;
import java.util.List;

public class EliminarDemo {

    public static void main(String[] args) {
        List<Integer> valores = new ArrayList<>(List.of(1, 8, 1));
        valores.remove(1);
        System.out.println(valores);
        valores.remove(Integer.valueOf(1));
        System.out.println(valores);
    }
}
```

Salida esperada: [1, 1] y [1]. La primera sobrecarga elimina índice 1 (el 8); la segunda elimina la primera coincidencia con el valor 1. Consulta [métodos y sobrecarga](/handbook/java/methods/). Para buscar por clave estable, considera [Map](/handbook/java/maps/). Las copias de listas no implican copiar sus objetos; revisa mutabilidad y referencias.

## Conceptos relacionados

- [Genéricos](/handbook/java/generics/)
- [Lambdas](/handbook/java/lambdas/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [ObservableList](/handbook/java/javafx/observable-list/)

## Documentación oficial

- [Collections Framework](https://dev.java/learn/api/collections-framework/)
- [List](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html)
- [ArrayList](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html)
