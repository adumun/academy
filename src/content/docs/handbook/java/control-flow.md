---
title: "Control de flujo: decisiones y bucles"
description: "Bloques, if, else, while, for, return y recorrido de datos."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

## Qué es el flujo de ejecución

El flujo es el orden en que el programa ejecuta sus instrucciones. Una condición es una [expresión booleana](/handbook/java/expressions/) que permite elegir caminos. Un bloque agrupa instrucciones entre llaves.

## Decisiones

```java
if (cantidad <= 0) {
    System.out.println("Cantidad inválida");
} else {
    System.out.println("Cantidad aceptada");
}
```

Fragmento con cantidad de tipo int. Se ejecuta exactamente una de las ramas. Usa llaves incluso si contiene una sola instrucción.

## Repetición

| Construcción | Cuándo usarla |
| --- | --- |
| while | Repetir mientras una condición sea verdadera |
| for | Controlar inicialización, condición y avance |
| for-each | Recorrer elementos de un iterable o arreglo |
| break | Salir del bucle o switch correspondiente |
| continue | Pasar a la siguiente iteración |
| return | Salir del método, con valor si corresponde |

```java
import java.util.List;

public class FlujoDemo {

    public static void main(String[] args) {
        List<String> titulos = List.of("Java", "SQL");

        for (String titulo : titulos) {
            System.out.println(titulo);
        }

        int contador = 0;

        while (contador < 2) {
            contador++;
        }

        System.out.println(contador);
    }
}
```

Archivo FlujoDemo.java. Salida esperada: Java, SQL y 2. La [colección](/handbook/java/collections/) se recorre elemento por elemento. El while revisa la condición antes de cada iteración.

## Aplicación JDBC

Un [ResultSet](/handbook/java/jdbc/result-set/) tiene un cursor que avanza mediante next(). El patrón while (resultado.next()) procesa cada fila hasta que no quedan más. Invocar next dos veces por iteración puede saltar registros.

## Errores frecuentes

Un bucle que no cambia las condiciones de salida puede no terminar. Un índice debe estar dentro de los límites de la colección. Un return dentro de un bucle finaliza el método, no solo la iteración. Comprueba casos vacíos, un elemento y varios.

## Conceptos relacionados

- [Variables](/handbook/java/variables/)
- [Métodos](/handbook/java/methods/)
- [Excepciones](/handbook/java/exceptions/)

## Documentación oficial

- [JLS 21: Blocks and Statements](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html)
