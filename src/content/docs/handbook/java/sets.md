---
title: "Set, HashSet y unicidad"
description: "Conjuntos, operaciones, igualdad y orden de recorrido."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

Un [conjunto](/handbook/foundations/data-structures/) expresa unicidad. `Set<E>` es la interfaz Java y HashSet una implementación basada en hashing. No garantiza orden de recorrido. LinkedHashSet conserva un orden de encuentro definido; TreeSet ordena según comparación. Usa la implementación que corresponde a tu necesidad.

## Ejemplo ejecutable

Archivo `SetDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 SetDemo.java` y `java SetDemo` con JDK 21. No requiere dependencias externas.

```java
import java.util.HashSet;
import java.util.Set;

public class SetDemo {

    public static void main(String[] args) {
        Set<String> etiquetas = new HashSet<>();
        System.out.println(etiquetas.add("rojo"));
        System.out.println(etiquetas.add("rojo"));
        etiquetas.add("azul");
        System.out.println(etiquetas.contains("azul"));
        System.out.println(etiquetas.size());
    }
}
```

Salida esperada:

```text
true
false
true
2
```

| Operación | Contrato |
|---|---|
| add(elemento) | true si se incorporó |
| contains(elemento) | Comprueba pertenencia |
| remove(elemento) | Elimina coincidencia si existe |
| size() | Cantidad de elementos |

HashSet depende de [equals y hashCode](/handbook/java/equality/). No cambies campos que participan en esos métodos mientras el elemento está en la estructura. No dependas de una salida ordenada; si el orden es un requisito, elige otra implementación o presenta una copia ordenada.

## Documentación oficial

- [Set](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Set.html)
- [HashSet](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashSet.html)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-08/01-set.md); los ejemplos de esta ficha son independientes.
