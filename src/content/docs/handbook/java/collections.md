---
title: "Colecciones, List y ArrayList"
description: "Interfaces de colección, orden, operaciones, mutabilidad y selección de estructuras."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Genéricos](/handbook/java/generics/)

## Modelo mental

Una colección organiza objetos en memoria. List<E> representa una secuencia ordenada que permite duplicados; ArrayList<E> es una implementación redimensionable. Set evita duplicados según su contrato de igualdad; Map asocia claves con valores y no implementa Collection.

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

Prueba duplicados, tamaño, lista vacía y eliminación por posición. Para List<Integer>, diferencia remove(1) de remove(Integer.valueOf(1)): el primero elimina por índice; el segundo por valor.

## Conceptos relacionados

- [Genéricos](/handbook/java/generics/)
- [Lambdas](/handbook/java/lambdas/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [ObservableList](/handbook/java/javafx/observable-list/)

## Documentación oficial

- [Collections Framework](https://dev.java/learn/api/collections-framework/)
- [List](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html)
- [ArrayList](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html)
