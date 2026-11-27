---
title: "Variables, valores y constantes"
description: "Declaración, inicialización, asignación, alcance y final."
---

**Tipo:** explicación y referencia. **Nivel:** esencial. **Versión:** Java 21.

**Al terminar esta ficha:** Identificar el tipo, nombre, valor, alcance y duración de variables, y distinguir reasignación de una referencia de mutación del objeto.

## Qué es una variable en Java

Consulta [valores, variables y tipos](/handbook/foundations/values-and-types/) para la definición independiente del lenguaje. Aquí se desarrolla la semántica de almacenamiento, asignación y alcance en Java.

Una variable es un lugar de almacenamiento identificado por un nombre que contiene un valor de un [tipo](/handbook/java/types/). El tipo determina qué valores puede contener y qué operaciones son válidas. Declarar una variable no es lo mismo que asignarle un valor.

<!-- example:variables-declaration -->
```java
int cantidad = 3;
cantidad = 5;
final int MAXIMO = 10;
```

En la primera línea, int es el tipo, cantidad el nombre, 3 el valor inicial y = el operador de asignación. En la segunda, se sustituye el valor almacenado por 5. MAXIMO no puede reasignarse después de su inicialización.

| Término | Significado |
| --- | --- |
| Declaración | Introduce el nombre y tipo de una variable |
| Inicialización | Le proporciona su primer valor |
| Asignación | Guarda un valor compatible |
| Alcance | Región del programa en que el nombre está disponible |
| final | Impide reasignar la variable después de inicializarla |

## Variables locales, parámetros y campos

Una variable local pertenece a un bloque o método. Un parámetro recibe un argumento al llamar a un [método](/handbook/java/methods/). Un campo pertenece a una clase o instancia y puede representar parte del estado de un objeto. Las variables locales deben estar definitivamente asignadas antes de leerse; los campos tienen valores iniciales por defecto según su tipo, aunque conviene inicializarlos explícitamente.

<!-- example:variables-demo -->
```java
public class VariablesDemo {

    private int total = 0;

    public void sumar(int cantidad) {
        int resultado = total + cantidad;
        total = resultado;
    }

    public static void main(String[] args) {
        VariablesDemo demo = new VariablesDemo();
        demo.sumar(3);
        demo.sumar(2);
        System.out.println(demo.total);
    }
}
```

Archivo VariablesDemo.java. Salida esperada: 5. total es un campo, cantidad un parámetro y resultado una variable local. El acceso a resultado termina al salir del método.

## Valor primitivo y referencia

Una variable int contiene un valor entero. Una variable Libro contiene una [referencia](/handbook/java/references/) a un objeto Libro o null; copiar esa variable no duplica el objeto. final impide cambiar la referencia guardada, no garantiza la inmutabilidad del objeto.

## Errores habituales

- Variable might not have been initialized: proporciona un valor válido antes de leerla.
- Cannot assign a value to final variable: revisa si realmente debía ser constante.
- Cannot find symbol: verifica nombre, alcance y ortografía.
- Incompatible types: revisa el tipo y la conversión, no agregues casts indiscriminados.

## Alcance y duración: no son lo mismo

El alcance indica dónde puede utilizarse un nombre en el código; la duración de un valor depende de cuándo existe el almacenamiento o el objeto. Un campo de instancia pertenece al objeto; un campo static pertenece a la clase en el sentido de que no existe una copia por instancia. Un parámetro se inicializa con el argumento de cada llamada.

<!-- example:variables-scope -->
```java
int total = 10;
if (total > 0) {
    int incremento = 2;
    total += incremento;
}
// total vale 12; incremento no está en alcance aquí.
```

Fragmento dentro de un método. La declaración local sin inicializador es válida, pero leerla antes de asignarla definitivamente no compila. Los campos numéricos reciben cero por defecto; boolean recibe false y las referencias reciben null. No confíes en esos valores para construir estados válidos.

## Ejemplo de referencia final

<!-- example:variables-final-reference -->
```java
final StringBuilder texto = new StringBuilder("A");
texto.append("B");
System.out.println(texto); // AB
// texto = new StringBuilder("C"); // no compila
```

El objeto puede cambiar; lo que final impide es asignar otra referencia a texto. No todo campo final es una constante de compilación. Una constante de compilación Java es una variable final de tipo primitivo o String inicializada con una expresión constante.

## Autocomprobación

En VariablesDemo, señala qué variable se crea en cada llamada, qué campo conserva su valor entre llamadas y qué cambia si creas una segunda instancia. Después intenta leer una variable local no inicializada y observa el diagnóstico del compilador.

## Verificación del ejemplo

La asignación de un valor a un campo no cambia los campos de otras instancias. Una variable local debe estar definitivamente asignada; un campo recibe un valor inicial por defecto. La prueba de referencia final comprueba que mutar el objeto es distinto de reasignar la variable.

Las fuentes versionadas de esta ficha se encuentran en `examples/java/handbook/variables/`. Los fragmentos se extraen de clases compilables y los ejemplos completos se conservan íntegros. Ejecuta `npm run validate:snippets` y `npm run test:java-sources` desde la raíz del repositorio con Node.js y JDK 21.

## Práctica de diagnóstico

Dibuja el estado de dos instancias de VariablesDemo antes y después de sumar cantidades diferentes. En el fragmento final, predice por separado si append cambia el contenido y si asignar otro StringBuilder a texto compila. Cambia el alcance de incremento e intenta leerlo fuera del bloque: el error de alcance no es lo mismo que el de inicialización definitiva.

**Fuentes de los ejemplos mostrados:** [VariablesFragmentsTest.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/variables/VariablesFragmentsTest.java) · [VariablesDemo.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/variables/VariablesDemo.java).

Casos de rechazo verificados con javac 21: [FinalReassignment.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/negative/FinalReassignment.java) · [OutOfScope.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/negative/OutOfScope.java) · [UninitializedLocal.java](https://github.com/adumun/academy/blob/41676c634274d580ab7985417c90bc949045d72d/examples/java/handbook/negative/UninitializedLocal.java). Son archivos deliberadamente inválidos, no ejemplos para copiar como solución.

## Conceptos relacionados

- [Tipos de datos](/handbook/java/types/)
- [Expresiones y operadores](/handbook/java/expressions/)
- [Referencias y null](/handbook/java/references/)
- [Clases y objetos](/handbook/java/classes-and-objects/)

## Documentación oficial

- [JLS 21: Variables](https://docs.oracle.com/javase/specs/jls/se21/html/jls-4.html#jls-4.12)
- [JLS 21: Scope](https://docs.oracle.com/javase/specs/jls/se21/html/jls-6.html#jls-6.3)
