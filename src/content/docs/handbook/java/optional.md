---
title: "Optional y ausencia de valores"
description: "Representar ausencia explícita, métodos y límites."
---

**Tipo:** explicación y referencia. **Nivel:** complementario.

## Conocimientos previos recomendados

- [Genéricos](/handbook/java/generics/)
- [Excepciones](/handbook/java/exceptions/)
- [Lambdas](/handbook/java/lambdas/)

## Qué representa

Optional<T> representa presencia o ausencia de un valor no nulo. No representa automáticamente éxito o error. Una consulta que no encuentra una fila puede retornar Optional.empty(); un fallo JDBC debe conservar su excepción.

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

Un buscarPorId puede retornar Optional<Libro>; el controlador distingue vacío de error y decide el mensaje. No retornes null en lugar de Optional ni uses get() sin comprobar presencia. No reemplaza validación de negocio ni representa un valor SQL NULL de manera universal.

## Conceptos relacionados

- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [CRUD JDBC](/handbook/java/jdbc/crud/)

## Documentación oficial

- [Optional, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Optional.html)
