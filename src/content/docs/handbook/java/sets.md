---
title: "Set, HashSet y unicidad"
description: "Conjuntos, operaciones, igualdad y orden de recorrido."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

**Al terminar esta ficha:** Distinguir unicidad de orden y seleccionar una implementación de Set.

## Modelo mental

Un [conjunto](/handbook/foundations/data-structures/) expresa unicidad. `Set<E>` es la interfaz Java y HashSet una implementación basada en hashing. No garantiza orden de recorrido. LinkedHashSet conserva un orden de encuentro definido; TreeSet ordena según comparación. Usa la implementación que corresponde a tu necesidad.

## Ejemplo ejecutable

Archivo `SetDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 SetDemo.java` y `java SetDemo` con JDK 21. No requiere dependencias externas.

<!-- example:sets-setdemo -->
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

## Elegir una implementación y observar unicidad

| Implementación | Propiedad principal |
|---|---|
| HashSet | Unicidad mediante igualdad y hashing; no garantiza orden de recorrido |
| LinkedHashSet | Unicidad y orden de encuentro definido |
| TreeSet | Unicidad según comparación y orden de clasificación |

Set no ofrece acceso por índice como List. El retorno de add permite distinguir incorporación de duplicado. En TreeSet, si el comparador considera dos elementos equivalentes (compare devuelve cero), se consideran la misma posición del conjunto; por ello conviene un orden consistente con equals para respetar el contrato general de Set.

### Ejemplo de contraste

<!-- example:sets-insertion-order -->
```java
java.util.Set<String> etiquetas = new java.util.LinkedHashSet<>();
etiquetas.add("B");
etiquetas.add("A");
etiquetas.add("B");
System.out.println(etiquetas); // [B, A]
```

Fragmento dentro de un método. La segunda B no se agrega; el orden mostrado corresponde a LinkedHashSet. No dependas del mismo resultado al cambiar a HashSet. Con objetos propios, define [igualdad y hashCode](/handbook/java/equality/) según el significado de unicidad; no confundas igualdad de referencia con igualdad de negocio.

## Autocomprobación

Elige un Set para etiquetas únicas sin orden, para conservar orden de llegada y para ordenar alfabéticamente. Predice qué ocurre al eliminar un elemento ausente y al intentar agregar una segunda instancia lógicamente igual a otra existente.

## Verificación ejecutable

Los ejemplos y fragmentos están respaldados por fuentes Java en `examples/java/handbook/sets/`. La suite `SetsTest` comprueba el contrato del concepto, además de las salidas de los ejemplos completos. Predice el resultado antes de ejecutar `npm run test:java-sources` con JDK 21; usa `npm run validate:snippets` para verificar sincronía.

## Documentación oficial

- [Set](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Set.html)
- [HashSet](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashSet.html)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-08/01-set.md); los ejemplos de esta ficha son independientes.
