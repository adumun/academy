---
title: "Map, HashMap y recuperación por clave"
description: "Asociación clave–valor, reemplazo, ausencia y recorrido."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

Un Map representa una [asociación](/handbook/foundations/data-structures/) clave–valor. Cada clave tiene como máximo un valor asociado. Los valores pueden repetirse. HashMap no garantiza orden de recorrido. Map no extiende Collection.

## Ejemplo ejecutable

Archivo `MapDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 MapDemo.java` y `java MapDemo` con JDK 21. No requiere dependencias externas.

```java
import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {
        Map<String, Integer> medidas = new HashMap<>();
        medidas.put("A", 10);
        medidas.put("B", 20);
        System.out.println(medidas.put("A", 15));
        System.out.println(medidas.get("A"));
        System.out.println(medidas.containsKey("C"));
        System.out.println(medidas.size());
    }
}
```

Salida esperada:

```text
10
15
false
2
```

| Operación | Contrato |
|---|---|
| put(clave, valor) | Asocia/reemplaza; retorna valor anterior o null |
| get(clave) | Retorna valor o null según contrato |
| containsKey(clave) | Distingue presencia |
| putIfAbsent(clave, valor) | Asocia si no existe valor no nulo |
| entrySet() | Vista de pares clave–valor |
| remove(clave) | Elimina asociación |

HashMap permite una clave null y valores null; otras implementaciones pueden rechazarlos. Un null retornado por get no siempre significa clave inexistente. Evita un reemplazo silencioso si la regla exige unicidad de registro: valida la semántica de la operación. Si las claves son objetos propios, revisa [igualdad y hashing](/handbook/java/equality/).

## Registrar, sustituir y distinguir ausencia

![Map contiene A igual a 10 y B igual a 20. put A,15 reemplaza el valor asociado a A sin crear una tercera entrada.](/diagrams/java-map-replace.svg)

Map representa asociaciones identificadas por clave, no posiciones. put retorna el valor anterior, que puede ser null si no había asociación o si estaba asociada a null. containsKey distingue los casos. El valor no necesita ser único. Una clave mutable que cambie su igualdad puede dejar de localizarse en una estructura hash.

### Recorrer pares sin perder su relación

```java
java.util.Map<String, Integer> medidas = new java.util.LinkedHashMap<>();
medidas.put("A", 10);
medidas.put("B", 10);
for (java.util.Map.Entry<String, Integer> entrada : medidas.entrySet()) {
    System.out.println(entrada.getKey() + ": " + entrada.getValue());
}
```

Fragmento de método. Salida esperada A: 10 y B: 10 en orden de inserción para esta configuración LinkedHashMap. Un HashMap no garantiza ese orden. TreeMap ordena claves según su comparación; no presupongas que orden y equals coinciden si usas un comparador inconsistente.

## Autocomprobación

Registra A dos veces con distinto valor y B con el mismo valor de A. Predice tamaño, valor anterior y contenido. Después prueba una clave ausente y una clave asociada a null en HashMap. Si la operación del dominio es registrar solo una vez, define rechazo explícito de duplicado en lugar de usar put como si fuera un alta incondicional.

## Documentación oficial

- [Map](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Map.html)
- [HashMap](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/HashMap.html)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-08/02-map.md); los ejemplos de esta ficha son independientes.
