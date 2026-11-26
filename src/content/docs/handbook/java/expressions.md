---
title: "Expresiones, operadores y condiciones"
description: "Literales, evaluación, asignación, comparación, booleanos y precedencia."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

## Expresión e instrucción

Consulta [expresiones y control de flujo](/handbook/foundations/flow/) para el modelo conceptual. Esta página desarrolla los operadores y reglas de evaluación propios de Java.

Una expresión es una construcción que puede evaluarse para producir un valor o realizar un efecto, según el caso. Una instrucción (statement) es una unidad de ejecución. Un literal representa directamente un valor, como 42, "Java" o true.

```java
int cantidad = 3 + 2;
boolean valida = cantidad > 0 && cantidad <= 10;
```

3 + 2 produce 5. La segunda expresión combina dos comparaciones y produce true. Consulta [tipos](/handbook/java/types/) y [variables](/handbook/java/variables/).

| Operador | Función |
| --- | --- |
| +, -, *, /, % | Operaciones aritméticas; + también concatena texto |
| = | Asignación |
| ==, != | Igualdad/desigualdad según el tipo |
| <, <=, >, >= | Comparación numérica |
| &&, \|\| | AND y OR con cortocircuito |
| ! | Negación booleana |

## Cortocircuito

```java
String titulo = null;

if (titulo != null && !titulo.isBlank()) {
    System.out.println(titulo);
}
```

Si titulo es null, la segunda condición no se evalúa. Esto evita invocar isBlank sobre una referencia ausente. El orden es relevante. Con OR, el segundo operando no se evalúa si el primero es true.

## Precedencia y efectos

Multiplicación y división preceden a suma y resta; las comparaciones preceden a operadores lógicos. Utiliza paréntesis si aclaran intención. Evita mezclar asignaciones, incrementos y llamadas con efectos en una condición compleja.

## Comprobación

Con cantidad = 5, evalúa cantidad > 0, cantidad == 5, cantidad < 3 y !(cantidad < 3). Resultados esperados: true, true, false y true. Para igualdad de texto utiliza equals o una comparación adecuada a las reglas del negocio, no identidad de referencias.

## Operadores, operandos y orden de evaluación

Un operador especifica una operación; sus operandos son las expresiones sobre las que actúa. La precedencia define cómo se agrupa una expresión sin paréntesis. No es lo mismo que el orden temporal de evaluación de operandos. En Java los operandos se evalúan de izquierda a derecha conforme a las reglas de cada operador.

| Expresión | Resultado | Motivo |
|---|---|---|
| 2 + 3 * 4 | 14 | Multiplicación antes de suma |
| (2 + 3) * 4 | 20 | Agrupación explícita |
| 5 / 2 | 2 | División entera |
| 5 % 2 | 1 | Resto |
| "Total: " + 2 + 3 | Total: 23 | Concatenación desde la izquierda |
| "Total: " + (2 + 3) | Total: 5 | Primero suma numérica |

## Ejemplo autónomo: cortocircuito

```java
public class CortocircuitoDemo {

    private static boolean comprobar() {
        System.out.println("Evaluado");
        return true;
    }

    public static void main(String[] args) {
        System.out.println(false && comprobar());
        System.out.println(true || comprobar());
        System.out.println(true && comprobar());
    }
}
```

Archivo CortocircuitoDemo.java. Salida esperada: false, true, Evaluado, true (cada resultado en su propia línea). Las dos primeras expresiones omiten la llamada porque su resultado ya es conocido. La tercera la invoca. Evita incluir cambios de estado necesarios en condiciones que pueden omitir su evaluación. Para rangos no uses `0 < cantidad < 10`; combina comparaciones con &&. Consulta [lógica proposicional](/handbook/foundations/boolean-logic/).

## Conceptos relacionados

- [Control de flujo](/handbook/java/control-flow/)
- [Referencias y null](/handbook/java/references/)
- [Validaciones](/handbook/java/javafx/validation/)

## Documentación oficial

- [JLS 21: Expressions](https://docs.oracle.com/javase/specs/jls/se21/html/jls-15.html)
