---
title: "Control de flujo: decisiones y bucles"
description: "Bloques, if, else, while, for, return y recorrido de datos."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

**Al terminar esta ficha:** Elegir una estructura de control, justificar su condición de terminación y distinguir una posición válida de un resultado de ausencia.

## Qué es el flujo de ejecución

Los [conceptos de secuencia, decisión y repetición](/handbook/foundations/flow/) son independientes de Java. Esta ficha muestra sus construcciones concretas y su sintaxis.

El flujo es el orden en que el programa ejecuta sus instrucciones. Una condición es una [expresión booleana](/handbook/java/expressions/) que permite elegir caminos. Un bloque agrupa instrucciones entre llaves.

## Decisiones

<!-- example:control-flow-decision -->
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

<!-- example:control-flow-loops -->
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

## Elegir la estructura de control

| Problema | Construcción |
|---|---|
| Ejecutar una de varias ramas | if / else if / else o switch apropiado |
| Recorrer un número conocido de posiciones | for con índice |
| Recorrer elementos sin necesitar posición | for-each |
| Repetir mientras cambie una condición | while |
| Ejecutar al menos una vez y luego evaluar | do-while |

Un for clásico tiene inicialización, condición y actualización; evalúa la condición antes de cada iteración. do-while evalúa después del cuerpo. break sale del bucle correspondiente; continue omite lo restante de la iteración; return finaliza todo el método.

## Ejemplo autónomo: buscar el primer positivo

<!-- example:control-flow-search -->
```java
public class BusquedaDemo {

    private static int buscarPrimeroPositivo(int[] valores) {
        for (int indice = 0; indice < valores.length; indice++) {
            if (valores[indice] > 0) {
                return indice;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(buscarPrimeroPositivo(new int[] {-2, 0, 4, 8}));
        System.out.println(buscarPrimeroPositivo(new int[] {-2, 0}));
        System.out.println(buscarPrimeroPositivo(new int[] {}));
    }
}
```

Archivo BusquedaDemo.java. Salida esperada: 2, -1, -1. El contrato retorna índice de la primera coincidencia o -1 si no hay ninguna; presupone un array no nulo. No se recorre 8 cuando ya se encontró 4. Para un array vacío el cuerpo no se ejecuta. Consulta [arrays](/handbook/java/arrays/) para el rango de índices.

## Verificación del ejemplo

Los casos de frontera para una condición `cantidad <= 0` son -1, 0 y 1. En una búsqueda, distingue el índice cero de -1 y el arreglo vacío de un arreglo nulo.

Las fuentes versionadas de esta ficha se encuentran en `examples/java/handbook/control-flow/`. Los fragmentos se extraen de clases compilables y los ejemplos completos se conservan íntegros. Ejecuta `npm run validate:snippets` y `npm run test:java-sources` desde la raíz del repositorio con Node.js y JDK 21.

## Práctica de diagnóstico

Traza buscarPrimeroPositivo con [4, 0, 8], [-2, 0, 4, 8] y []. Predice 0, 2 y -1 respectivamente; el índice 0 no equivale a ausencia. Explica por qué el return evita recorrer los elementos posteriores y qué precondición exige el método respecto a null. El ejemplo publicado verifica el segundo y el tercer escenario; el primero es una extensión de práctica.

**Fuentes de los ejemplos mostrados:** [ControlFlowFragmentsTest.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/control-flow/ControlFlowFragmentsTest.java) · [FlujoDemo.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/control-flow/FlujoDemo.java) · [BusquedaDemo.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/control-flow/BusquedaDemo.java).

## Conceptos relacionados

- [Variables](/handbook/java/variables/)
- [Métodos](/handbook/java/methods/)
- [Excepciones](/handbook/java/exceptions/)

## Documentación oficial

- [JLS 21: Blocks and Statements](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html)
