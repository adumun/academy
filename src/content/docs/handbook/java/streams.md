---
title: "Streams en Java"
description: "Pipelines, predicados, transformaciones, operaciones terminales y cierre."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

**Al terminar esta ficha:** Reconocer operaciones intermedias y terminales y comprobar equivalencia con un ciclo.

## Modelo mental

Un Stream representa una secuencia de elementos procesable mediante operaciones. No es una colección: puede provenir de una [List](/handbook/java/collections/) u otra fuente y se consume una sola vez. Una operación intermedia construye el pipeline; una terminal produce un resultado o efecto. Las operaciones pueden evaluarse de forma perezosa, al necesitarse el resultado.

## Ejemplo autónomo

Archivo `StreamsDemo.java`. Compila con `javac --release 21 StreamsDemo.java` y ejecuta `java StreamsDemo`.

<!-- example:streams-streamsdemo -->
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

## Fuente, operaciones intermedias y terminal

![Una fuente contiene 1, -2 y 3. filter conserva 1 y 3, map los duplica y toList materializa 2 y 6.](/diagrams/java-stream-pipeline.svg)

El pipeline describe una secuencia de procesamiento: obtiene elementos de una fuente, aplica transformaciones intermedias y finalmente produce un resultado. filter recibe un Predicate, map una Function y toList materializa una lista. Las operaciones intermedias son perezosas: se procesan cuando la terminal lo necesita, no necesariamente se construye una colección por cada etapa.

| Etapa del ejemplo | Elementos conceptuales |
|---|---|
| Fuente | 1, -2, 3 |
| filter positivo | 1, 3 |
| map por 2 | 2, 6 |
| toList | Lista no modificable con 2 y 6 |

anyMatch puede detener el recorrido al encontrar una coincidencia. allMatch sobre una fuente vacía retorna true por su contrato; anyMatch retorna false. Si necesitas repetir una operación terminal, crea otro Stream a partir de la fuente. No se garantiza que un pipeline paralelo preserve el orden de efectos secundarios; evita añadir paralelismo sin una necesidad y medición.

## Contraste con un ciclo

<!-- example:streams-loop-equivalence -->
```java
java.util.List<Integer> resultado = new java.util.ArrayList<>();
for (Integer valor : java.util.List.of(1, -2, 3)) {
    if (valor > 0) {
        resultado.add(valor * 2);
    }
}
System.out.println(resultado); // [2, 6]
```

Fragmento dentro de un método. El ciclo y el pipeline expresan el mismo cálculo, aunque esta lista es modificable y la de toList no. Elige según claridad, contrato y necesidad de control del recorrido.

## Verificación ejecutable

Los ejemplos y fragmentos están respaldados por fuentes Java en `examples/java/handbook/streams/`. La suite `StreamsTest` comprueba el contrato del concepto, además de las salidas de los ejemplos completos. Predice el resultado antes de ejecutar `npm run test:java-sources` con JDK 21; usa `npm run validate:snippets` para verificar sincronía.

## Documentación oficial

- [Stream Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/stream/Stream.html)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/docs/GUIA-STREAMS-LAMBDAS-R06.md). Los ejemplos no requieren un proyecto de negocio específico.
