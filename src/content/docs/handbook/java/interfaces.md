---
title: "Interfaces y polimorfismo"
description: "Contratos, implementaciones, sustitución y desacoplamiento."
---

**Tipo:** explicación. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)

## Contrato y comportamiento

Una interfaz expresa operaciones disponibles para un consumidor sin exigir que conozca una implementación concreta. Un contrato no es solamente una firma: también define qué significa el resultado, qué entradas son válidas y qué errores pueden producirse.

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

## Conceptos relacionados

- [Genéricos](/handbook/java/generics/)
- [Colecciones](/handbook/java/collections/)
- [MVC y DAO](/handbook/architecture/)
- [Lambdas](/handbook/java/lambdas/)

## Documentación oficial

- [Interfaces, Dev.java](https://dev.java/learn/interfaces/)
- [JLS 21, Interfaces](https://docs.oracle.com/javase/specs/jls/se21/html/jls-9.html)
