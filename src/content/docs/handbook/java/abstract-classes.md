---
title: "Clases y métodos abstractos"
description: "Contrato común, comportamiento obligatorio y clases concretas."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

**Al terminar esta ficha:** Identificar estado y comportamiento común en una clase abstracta y completar los métodos especializados en subtipos concretos.

## Modelo mental

Una clase abstracta expresa una [abstracción](/handbook/foundations/object-relationships/) que no puede instanciarse directamente. Puede tener campos, constructores y métodos concretos. Un método abstracto declara una firma sin cuerpo; una subclase concreta debe implementar los métodos abstractos que queden pendientes. Una interfaz y una clase abstracta resuelven necesidades relacionadas pero diferentes.

## Ejemplo ejecutable

Archivo `AbstraccionDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 AbstraccionDemo.java` y `java AbstraccionDemo` con JDK 21. No requiere dependencias externas.

<!-- example:abstract-classes-measurement -->
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

## Qué permite y qué impide abstract

Una clase abstracta declara un tipo de referencia válido: se pueden declarar variables, parámetros y resultados de ese tipo, pero no construir directamente una instancia con new. Una clase concreta puede extenderla si satisface los métodos abstractos heredados. Una clase abstracta también puede no declarar métodos abstractos: el modificador expresa que el tipo no se construye directamente.

| Construcción | Resultado en el ejemplo |
|---|---|
| Medicion medicion | Declaración válida de referencia |
| new Medicion(...) | Error de compilación |
| new Temperatura() | Crea una instancia concreta |
| medicion.mostrar() | Ejecuta el comportamiento común; valor() se despacha al subtipo |

El constructor de Medicion sí se ejecuta cuando se construye Temperatura. Que no pueda crearse sola no significa que no tenga estado ni constructor. El método abstracto declara lo que falta implementar; no significa que devuelva null o cero.

## Comprobación de extensión

Agrega una subclase que represente una distancia con unidad m y método valor que retorna 5. Invoca mostrar mediante una referencia Medicion. Debe producir 5 m. Si omites valor, la clase concreta no compila. Esta familia comparte representación de magnitud y unidad; un consumidor que espera exclusivamente temperatura necesita un contrato más específico. Compara con [herencia](/handbook/java/inheritance/) e [interfaces](/handbook/java/interfaces/).

## Práctica de diagnóstico y verificación

Añade una medición de distancia con unidad m y valor 5. Comprueba que el mismo comportamiento mostrar devuelve 5 m. Instanciar la clase abstracta u omitir el método abstracto en una clase concreta produce errores distintos.

Fuentes versionadas: `examples/java/handbook/abstract-classes/`. Pruebas: `npm run validate:snippets` y `npm run test:java-sources` desde la raíz del repositorio con JDK 21. Casos negativos deliberados en `examples/java/handbook/negative/`: `AbstractInstantiation.java`, `MissingAbstractMethod.java`.

## Documentación oficial

- [JLS 21: clases abstractas](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.1.1.1)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-07/02-clases-abstractas.md); los ejemplos de esta ficha son independientes.
