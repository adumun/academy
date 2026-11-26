---
title: "Interfaces y polimorfismo"
description: "Contratos, implementaciones, sustitución y desacoplamiento."
---

**Tipo:** explicación. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)

## Contrato y comportamiento

Una interfaz es un [tipo de referencia](/handbook/java/types/) que expresa operaciones disponibles para un consumidor (código que las utiliza), sin exigir que conozca una implementación concreta. Una implementación es una clase que cumple esas operaciones. Un [contrato](/handbook/architecture/contracts/) no es solamente una [firma de método](/handbook/java/methods/): también define qué significa el resultado, qué entradas son válidas y qué errores pueden producirse. Polimorfismo significa que una misma referencia del tipo de la interfaz puede invocar el comportamiento de diferentes implementaciones; se selecciona el método de instancia apropiado durante la ejecución. Un método abstracto declara una operación sin cuerpo en ese contexto.

## Ejemplo completo

```java
import java.util.List;

interface Catalogo {
    List<String> listar();
}

class CatalogoMemoria implements Catalogo {

    @Override
    public List<String> listar() {
        return List.of("Java", "SQL");
    }
}

public class CatalogoDemo {

    public static void main(String[] args) {
        Catalogo catalogo = new CatalogoMemoria();
        System.out.println(catalogo.listar());
    }
}
```

Archivo CatalogoDemo.java. Salida: [Java, SQL]. La variable catalogo tiene tipo Catalogo; el método ejecutado corresponde a su implementación real.

## Interfaces frente a clases abstractas

| Aspecto | Interfaz | Clase abstracta |
| --- | --- | --- |
| Propósito | Contrato de capacidad | Base común de una familia |
| Estado de instancia | No define campos de instancia | Puede contener estado |
| Implementaciones | Una clase puede implementar varias | Una clase extiende una sola clase |
| Métodos con código | default, static y private según reglas | Métodos concretos y abstractos |

## Aplicación en DAO

Un controlador puede consumir LibroDao sin conocer la implementación de JDBC. Cambiar una implementación por otra exige conservar semántica, errores y resultados: no basta con que compile. Una implementación de prueba en memoria puede ayudar a comprobar el controlador sin MariaDB, pero no sustituye pruebas reales de persistencia.

## Límites

No introduzcas interfaces vacías o redundantes solo por cumplir una regla estética. Una interfaz aporta valor cuando expresa una capacidad o permite sustituir implementaciones y probar consumidores. Los métodos de una interfaz funcional también permiten [lambdas](/handbook/java/lambdas/).

## Comprobación

Agrega otra implementación que retorne una lista vacía y cambia únicamente la construcción de CatalogoDemo. El consumidor debería seguir compilando. Verifica qué espera el consumidor ante lista vacía y fallo.

## Cómo se selecciona una implementación

![El consumidor invoca convertir mediante el tipo Conversor; el objeto real puede ser una implementación CelsiusAFahrenheit o CelsiusAKelvin, con contratos de salida explícitos.](/diagrams/java-interface-dispatch.svg)

El tipo declarado determina qué operaciones puede invocar el consumidor. El objeto real determina qué implementación de un método de instancia se ejecuta. Para sustituir implementaciones, el contrato debe expresar la semántica completa: el ejemplo de conversión necesita documentar la unidad de salida; no debe presentar Kelvin como Fahrenheit. El consumidor puede recibir resultado y unidad explícitos, o usar un contrato más específico.

## Ejemplo ejecutable con dos implementaciones

Archivo DespachoDemo.java, Java 21. Ambas implementaciones cumplen el contrato de obtener una descripción textual no nula.

```java
public class DespachoDemo {

    interface Describible {
        String describir();
    }

    static class Punto implements Describible {
        @Override
        public String describir() {
            return "Punto";
        }
    }

    static class Linea implements Describible {
        @Override
        public String describir() {
            return "Linea";
        }
    }

    private static void mostrar(Describible elemento) {
        System.out.println(elemento.describir());
    }

    public static void main(String[] args) {
        mostrar(new Punto());
        mostrar(new Linea());
    }
}
```

Salida esperada: Punto y Linea. mostrar recibe el contrato, no necesita comprobar el tipo concreto con instanceof. Las clases anidadas solo simplifican la ejecución en un archivo; en un proyecto pueden estar separadas. Los métodos abstractos de interfaz son públicos según sus reglas; al implementar no se puede reducir la visibilidad. Una interfaz puede extender otras interfaces y una clase implementar más de una.

## Autocomprobación

Agrega un tercer elemento describible y úsalo sin editar mostrar. Compara con una clase abstracta cuando se necesita estado común. Consulta [herencia](/handbook/java/inheritance/) y [clases abstractas](/handbook/java/abstract-classes/).

## Conceptos relacionados

- [Genéricos](/handbook/java/generics/)
- [Colecciones](/handbook/java/collections/)
- [MVC y DAO](/handbook/architecture/)
- [Lambdas](/handbook/java/lambdas/)

## Documentación oficial

- [Interfaces, Dev.java](https://dev.java/learn/interfaces/)
- [JLS 21, Interfaces](https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html)
