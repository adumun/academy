---
title: "Clases, objetos y encapsulamiento"
description: "Estado, identidad, constructores, acceso y modelos de dominio."
---

**Tipo:** explicación. **Nivel:** esencial. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Java y POO para persistencia](/handbook/java/fundamentals/)

## Clase e instancia

Una clase define un tipo con estado y comportamiento. Una instancia contiene valores propios. Una variable de tipo referencia apunta a un objeto: asignar una segunda variable no crea una copia.

## Ejemplo completo

```java
public class Libro {

    private final int id;
    private final String titulo;

    public Libro(int id, String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título obligatorio");
        }

        this.id = id;
        this.titulo = titulo.strip();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public static void main(String[] args) {
        Libro primero = new Libro(1, "Java");
        Libro segundo = new Libro(2, "SQL");

        System.out.println(primero.getTitulo());
        System.out.println(segundo.getTitulo());
    }
}
```

Archivo Libro.java, JDK 21. Salida: Java y SQL. El constructor valida y establece el estado inicial. final impide reasignar los campos; los tipos usados aquí son inmutables.

## Acceso y encapsulamiento

| Modificador | Alcance habitual |
| --- | --- |
| private | Dentro de la clase que declara el miembro |
| sin modificador | Paquete |
| protected | Paquete y acceso de subclases según reglas de Java |
| public | Acceso desde consumidores autorizados por el módulo |

Un getter es una operación que expone un valor, no una obligación de permitir modificarlo. Un setter debe preservar invariantes, no simplemente escribir cualquier dato. Para editar un Libro inmutable, construye otra instancia con el mismo ID y el título nuevo.

## Identidad frente a igualdad

El ID de persistencia identifica una fila; el operador == entre referencias comprueba si apuntan al mismo objeto. equals puede representar igualdad lógica si el modelo lo define. No asumas que dos instancias obtenidas en consultas distintas son el mismo objeto.

## Mapeo y límites

[ResultSet](/handbook/java/jdbc/result-set/) lee filas y construye objetos. Cada fila requiere una instancia propia. El modelo no debería contener SQL ni controles de interfaz: el DAO y controlador se encargan de esos aspectos. Para una UI observable, puedes adaptar el modelo sin introducir JavaFX en el dominio.

## Comprobación

Construye dos libros, verifica sus atributos y provoca un título vacío en un proyecto de prueba: debe lanzarse IllegalArgumentException. Si aparece NullPointerException al invocar un getter, comprueba que la referencia exista.

## Conceptos relacionados

- [Genéricos](/handbook/java/generics/)
- [Interfaces](/handbook/java/interfaces/)
- [MVC y DAO](/handbook/architecture/)
- [Observabilidad](/handbook/java/javafx/table-view-observability/)

## Documentación oficial

- [Classes and Objects](https://dev.java/learn/classes-objects/)
- [Object, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html)
