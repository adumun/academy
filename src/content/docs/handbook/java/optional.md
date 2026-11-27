---
title: "Optional y ausencia de valores"
description: "Representar ausencia explícita, métodos y límites."
---

**Tipo:** explicación y referencia. **Nivel:** complementario.

**Al terminar esta ficha:** Distinguir presencia, ausencia, transformación y evaluación de alternativas.

## Conocimientos previos recomendados

- [Genéricos](/handbook/java/generics/)
- [Excepciones](/handbook/java/exceptions/)
- [Lambdas](/handbook/java/lambdas/)

## Qué representa

`Optional<T>` representa presencia o ausencia de un valor no nulo. T es el [parámetro genérico](/handbook/java/generics/); [null](/handbook/java/references/) significa que no hay referencia a un objeto. Optional expresa ausencia mediante una instancia vacía y proporciona [métodos](/handbook/java/methods/) para consultarla o transformar su contenido. En orElseGet, map e ifPresent se pasan [interfaces funcionales](/handbook/java/lambdas/). No representa automáticamente éxito o error. Una consulta que no encuentra una fila puede retornar Optional.empty(); un fallo JDBC debe conservar su excepción.

| Método | Semántica |
| --- | --- |
| Optional.of(valor) | Requiere valor no nulo |
| Optional.ofNullable(valor) | Vacío si recibe null |
| Optional.empty() | Ausencia |
| isPresent() / isEmpty() | Consultar presencia |
| orElse(alternativa) | Valor o alternativa ya evaluada |
| orElseGet(supplier) | Calcula alternativa solo si está vacío |
| orElseThrow() | Valor o NoSuchElementException |
| map(funcion) | Transforma valor presente; null se convierte en vacío |
| ifPresent(consumer) | Ejecuta acción si hay valor |

## Ejemplo completo

<!-- example:optional-optionaldemo -->
```java
import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {
        Optional<String> titulo = Optional.of("Java");
        Optional<String> ausente = Optional.empty();

        System.out.println(titulo.orElse("Sin título"));
        System.out.println(ausente.orElse("Sin título"));
    }
}
```

Salida: Java y Sin título. Archivo OptionalDemo.java, JDK 21.

## Aplicación en DAO

Un buscarPorId puede retornar `Optional<Libro>`; el controlador distingue vacío de error y decide el mensaje. No retornes null en lugar de Optional ni uses get() sin comprobar presencia. No reemplaza validación de negocio ni representa un valor SQL NULL de manera universal.

## Presencia no equivale a éxito

Un Optional vacío representa que no hay valor, no que se produjo cualquier error. Una búsqueda válida sin coincidencias y una búsqueda que no pudo realizarse tienen resultados semánticos distintos. El método que retorna Optional no debe retornar null: eso crea dos mecanismos de ausencia simultáneos.

### Alternativa evaluada frente a alternativa diferida

<!-- example:optional-fallback -->
```java
java.util.Optional<String> dato = java.util.Optional.of("A");
System.out.println(dato.orElse(alternativa()));
System.out.println(dato.orElseGet(() -> alternativa()));
```

Fragmento dentro de un método; presupone un método alternativa que imprime un mensaje y retorna texto. La primera llamada evalúa alternativa aunque dato esté presente. La segunda no invoca el Supplier mientras haya valor. Evita utilizar alternativas costosas o con efectos innecesarios mediante orElse.

## Transformar sin extraer prematuramente

map aplica una transformación cuando hay valor; filter conserva el valor si satisface un predicado; flatMap permite encadenar una transformación que ya retorna Optional. Consulta [lambdas](/handbook/java/lambdas/) para identificar los contratos funcionales. No uses Optional como campo obligatorio que deba verificarse cada vez si el modelo exige presencia al construir.

## Autocomprobación

Prueba ofNullable(null), ofNullable(""), empty y una búsqueda que lanza una excepción. Explica por qué el texto vacío puede ser un valor presente y por qué el fallo no debe transformarse automáticamente en Optional.empty().

## Conceptos relacionados

- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [CRUD JDBC](/handbook/java/jdbc/crud/)

## Verificación ejecutable

Los ejemplos y fragmentos están respaldados por fuentes Java en `examples/java/handbook/optional/`. La suite `OptionalTest` comprueba el contrato del concepto, además de las salidas de los ejemplos completos. Predice el resultado antes de ejecutar `npm run test:java-sources` con JDK 21; usa `npm run validate:snippets` para verificar sincronía.

## Documentación oficial

- [Optional, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html)
