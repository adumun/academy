---
title: "Constructores, this e inicialización"
description: "Constructor implícito, sobrecarga, delegación e invariantes."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

Un constructor inicializa una nueva [instancia](/handbook/foundations/objects/) y establece sus invariantes. Tiene el nombre de la clase y no declara retorno, ni siquiera void. `new` crea el objeto e invoca el constructor. Si no declaras ningún constructor, Java proporciona un constructor sin argumentos según las reglas de la clase; al declarar uno propio deja de hacerlo.

`this` representa la instancia actual. `this.campo` distingue el campo del parámetro homónimo; `this(...)` delega a otro constructor de la clase (en Java 21 debe ser la primera instrucción). Sobrecargar significa tener firmas de parámetros distintas.

## Ejemplo ejecutable

Archivo `ConstructorDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 ConstructorDemo.java` y `java ConstructorDemo` con JDK 21. No requiere dependencias externas.

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

    public int amplitud() {
        return maximo - minimo;
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

## Diagnóstico y buenas prácticas

No uses un setter obligatorio posterior para completar un objeto inválido. No ignores una entrada incorrecta dejando el valor por defecto accidentalmente; define explícitamente la política de error. El ejemplo usa una excepción no comprobada, consulta [excepciones](/handbook/java/exceptions/). El constructor de una subclase debe inicializar su parte heredada según [herencia](/handbook/java/inheritance/).

## Documentación oficial

- [JLS 21: constructores](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.8)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-04/01-constructores.md); los ejemplos de esta ficha son independientes.
