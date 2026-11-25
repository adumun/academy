---
title: "Clases y métodos abstractos"
description: "Contrato común, comportamiento obligatorio y clases concretas."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

Una clase abstracta expresa una [abstracción](/handbook/foundations/object-relationships/) que no puede instanciarse directamente. Puede tener campos, constructores y métodos concretos. Un método abstracto declara una firma sin cuerpo; una subclase concreta debe implementar los métodos abstractos que queden pendientes. Una interfaz y una clase abstracta resuelven necesidades relacionadas pero diferentes.

## Ejemplo ejecutable

Archivo `AbstraccionDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 AbstraccionDemo.java` y `java AbstraccionDemo` con JDK 21. No requiere dependencias externas.

```java
public class AbstraccionDemo {

    abstract static class Medicion {
        private final String unidad;

        Medicion(String unidad) {
            this.unidad = unidad;
        }

        abstract int valor();

        String mostrar() {
            return valor() + " " + unidad;
        }
    }

    static class Temperatura extends Medicion {
        Temperatura() {
            super("C");
        }

        @Override
        int valor() {
            return 21;
        }
    }

    public static void main(String[] args) {
        Medicion medicion = new Temperatura();
        System.out.println(medicion.mostrar());
    }
}
```

Salida esperada:

```text
21 C
```

La superclase inicializa el estado común y llama al método polimórfico desde mostrar. Evita llamar a métodos sobrescribibles desde constructores si dependen del estado aún no inicializado del subtipo.

## Cuándo usar

Usa clase abstracta cuando hay una familia real con implementación/estado común y operaciones especializadas. Una [interfaz](/handbook/java/interfaces/) es apropiada para capacidades que pueden atravesar familias diferentes. No declares una clase abstracta únicamente para impedir instanciarla si el contrato no lo justifica.

## Documentación oficial

- [JLS 21: clases abstractas](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.1.1.1)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/02-clases-abstractas.md); los ejemplos de esta ficha son independientes.
