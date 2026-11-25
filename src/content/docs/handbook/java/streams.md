---
title: "Streams en Java"
description: "Pipelines, predicados, transformaciones, operaciones terminales y cierre."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Modelo mental

Un Stream representa una secuencia de elementos procesable mediante operaciones. No es una colección: puede provenir de una [List](/handbook/java/collections/) u otra fuente y se consume una sola vez. Una operación intermedia construye el pipeline; una terminal produce un resultado o efecto. Las operaciones pueden evaluarse de forma perezosa, al necesitarse el resultado.

## Ejemplo autónomo

Archivo `StreamsDemo.java`. Compila con `javac --release 21 StreamsDemo.java` y ejecuta `java StreamsDemo`.

```java
import java.util.List;

public class StreamsDemo {

    public static void main(String[] args) {
        List<Integer> mediciones = List.of(1, -2, 3);
        boolean invalida = mediciones.stream().anyMatch(valor -> valor < 0);
        List<Integer> positivas = mediciones.stream()
                .filter(valor -> valor > 0)
                .map(valor -> valor * 2)
                .toList();

        System.out.println(invalida);
        System.out.println(positivas);
    }
}
```

Salida esperada:

```text
true
[2, 6]
```

| Operación | Tipo y resultado |
|---|---|
| filter | Intermedia: selecciona elementos |
| map | Intermedia: transforma elementos |
| anyMatch | Terminal: si alguno cumple; false si vacío |
| allMatch | Terminal: si todos cumplen; true si vacío |
| toList | Terminal: lista no modificable |
| count | Terminal: cantidad |

## Criterio de uso

Una [lambda](/handbook/java/lambdas/) implementa la interfaz funcional que recibe cada operación; una referencia a método puede simplificarla. No uses un stream solo por novedad; un for claro es válido. Evita efectos secundarios dentro de filter/map. No reutilices streams consumidos. Los streams de E/S como Files.list deben cerrarse; una colección no mantiene ese recurso.

## Documentación oficial

- [Stream Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/docs/GUIA-STREAMS-LAMBDAS-R06.md). Los ejemplos no requieren un proyecto de negocio específico.
