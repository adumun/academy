---
title: "Constructores, this e inicialización"
description: "Constructor implícito, sobrecarga, delegación e invariantes."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

**Al terminar esta ficha:** Comprender la delegación entre constructores, establecer invariantes al crear objetos y elegir tipos capaces de representar el resultado.

## Modelo mental

Un constructor inicializa una nueva [instancia](/handbook/foundations/objects/) y establece sus invariantes. Tiene el nombre de la clase y no declara retorno, ni siquiera void. `new` crea el objeto e invoca el constructor. Si no declaras ningún constructor, Java proporciona un constructor sin argumentos según las reglas de la clase; al declarar uno propio deja de hacerlo.

`this` representa la instancia actual. `this.campo` distingue el campo del parámetro homónimo; `this(...)` delega a otro constructor de la clase (en Java 21 debe ser la primera instrucción). Sobrecargar significa tener firmas de parámetros distintas.

## Ejemplo ejecutable

Archivo `ConstructorDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 ConstructorDemo.java` y `java ConstructorDemo` con JDK 21. No requiere dependencias externas.

<!-- example:constructors-range -->
```java
public class ConstructorDemo {

    private final int minimo;
    private final int maximo;

    public ConstructorDemo(int maximo) {
        this(0, maximo);
    }

    public ConstructorDemo(int minimo, int maximo) {
        if (minimo > maximo) {
            throw new IllegalArgumentException("Rango invalido");
        }

        this.minimo = minimo;
        this.maximo = maximo;
    }

    public long amplitud() {
        return (long) maximo - minimo;
    }

    public static void main(String[] args) {
        ConstructorDemo primero = new ConstructorDemo(10);
        ConstructorDemo segundo = new ConstructorDemo(3, 8);
        System.out.println(primero.amplitud());
        System.out.println(segundo.amplitud());

        try {
            new ConstructorDemo(9, 2);
        } catch (IllegalArgumentException exception) {
            System.out.println("Rechazado");
        }
    }
}
```

Salida esperada:

```text
10
5
Rechazado
```

La amplitud es un `long` porque la diferencia entre dos valores `int` puede superar `Integer.MAX_VALUE`. El cast del máximo antes de restar hace que la operación se realice en `long`.

## Diagnóstico y buenas prácticas

No uses un setter obligatorio posterior para completar un objeto inválido. No ignores una entrada incorrecta dejando el valor por defecto accidentalmente; define explícitamente la política de error. El ejemplo usa una excepción no comprobada, consulta [excepciones](/handbook/java/exceptions/). El constructor de una subclase debe inicializar su parte heredada según [herencia](/handbook/java/inheritance/).

## Secuencia de inicialización

![new ConstructorDemo(10) delega al constructor de dos argumentos mediante this(0, 10); este valida los límites, inicializa campos y permite utilizar el objeto.](/diagrams/java-constructor.svg)

En Java 21, la invocación explícita this(...) o super(...) debe aparecer al principio del constructor. Si no se escribe ninguna, se invoca implícitamente super() cuando corresponde. La inicialización incluye valores por defecto, construcción de superclases y ejecución de inicializadores de instancia y cuerpos según el orden definido por Java. Los constructores no se heredan ni se sobrescriben.

| Declaración | Consecuencia |
|---|---|
| Ningún constructor declarado | Constructor por defecto, si las reglas de la clase lo permiten |
| Constructor con argumentos declarado | No se añade automáticamente uno sin argumentos |
| this(0, maximo) | Reutiliza otro constructor de la misma clase |
| super(...) | Inicializa mediante constructor de la superclase |

Si la superclase no tiene un constructor sin argumentos accesible, la subclase debe llamar al apropiado explícitamente. Una excepción durante construcción impide que la expresión new finalice normalmente con una referencia; evita publicar this antes de completar las invariantes.

## Autocomprobación

En ConstructorDemo, sigue la ejecución de new ConstructorDemo(10) y de new ConstructorDemo(9, 2). ¿Qué valores se validan? ¿Por qué no se debe duplicar la validación en todos los constructores?

## Práctica de diagnóstico y verificación

Prueba límites iguales, invertidos y los dos extremos de int. El ancho del rango completo es 4294967295: cabe en long, no en int. La conversión debe ocurrir antes de restar; convertir un resultado int desbordado no lo recupera. La ausencia de constructor sin argumentos también tiene un diagnóstico verificable.

Fuentes versionadas: `examples/java/handbook/constructors/`. Pruebas: `npm run validate:snippets` y `npm run test:java-sources` desde la raíz del repositorio con JDK 21. Casos negativos deliberados en `examples/java/handbook/negative/`: `MissingNoArgConstructor.java`.

## Documentación oficial

- [JLS 21: constructores](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.8)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-04/01-constructores.md); los ejemplos de esta ficha son independientes.
