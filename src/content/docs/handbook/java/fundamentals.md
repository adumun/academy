---
title: "Java y POO para persistencia"
description: "Clases, encapsulamiento, constructores, colecciones, interfaces y excepciones."
---

## Índice de conocimientos canónicos

- [Clases, objetos y encapsulamiento](/handbook/java/classes-and-objects/)
- [Genéricos](/handbook/java/generics/)
- [Colecciones y List](/handbook/java/collections/)
- [Interfaces y polimorfismo](/handbook/java/interfaces/)
- [Excepciones y try-with-resources](/handbook/java/exceptions/)
- [Lambdas e interfaces funcionales](/handbook/java/lambdas/)

Las siguientes secciones presentan una síntesis en el contexto de persistencia. Consulta las fichas independientes para profundizar.

## Modelo orientado a objetos

Una clase define estructura y comportamiento; una instancia representa un objeto concreto. Para mapear una tabla, el modelo debe representar sus atributos y proteger su estado.

```java
public class Libro {
    private final int id;
    private final String titulo;

    public Libro(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }
}
```

El ID identifica el registro, no su posición en una lista. final impide reasignar los atributos una vez construido el objeto; para editar, podemos crear otra instancia con el mismo ID y nuevos datos.

## Colecciones y genéricos

```java
List<Libro> libros = new ArrayList<>();
libros.add(new Libro(1, "Java"));
libros.add(new Libro(2, "SQL"));

for (Libro libro : libros) {
    System.out.println(libro.getTitulo());
}
```

List<Libro> expresa qué objetos se esperan. List es un contrato y ArrayList una implementación. La colección en memoria no persiste por sí misma.

## Interfaces y polimorfismo

```java
public interface LibroDao {
    List<Libro> listarTodos() throws SQLException;
}

LibroDao dao = new LibroDaoMySql();
```

La declaración del contrato permite cambiar la implementación sin cambiar el consumidor, si se respeta el comportamiento definido.

## Excepciones

SQLException es verificada: Java exige capturarla o declararla. El DAO puede propagarla; el controlador decide cómo informar al usuario.

```java
try {
    List<Libro> libros = dao.listarTodos();
    System.out.println(libros.size());
} catch (SQLException e) {
    System.err.println("No se pudieron consultar los libros");
}
```

No captures una excepción para simular éxito. El ejemplo muestra un mensaje mínimo; en un programa real conserva diagnóstico técnico apropiado sin exponer secretos.

## Recursos y buenas prácticas

Try-with-resources administra objetos AutoCloseable. No sustituye la validación de datos. Usa llaves incluso para bloques de una instrucción, nombres descriptivos, métodos pequeños, imports explícitos y una instrucción por línea. Diferencia los tipos primitivos de sus envoltorios cuando exista ausencia de valor.

## Comprobación

Crea dos instancias con títulos distintos, agrégalas a una lista y comprueba que mantienen sus valores. Luego recupera los mismos atributos desde ResultSet. Si reinicias sin persistencia, una lista creada en memoria se pierde.

## Conceptos relacionados

- [ResultSet](/handbook/java/jdbc/result-set/)
- [DAO y MVC](/handbook/architecture/)
- [Validación](/handbook/java/javafx/validation/)

## Documentación oficial

- [Java Learn](https://dev.java/learn/)
- [Excepciones Java](https://dev.java/learn/exceptions/)
- [Genéricos](https://dev.java/learn/generics/)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
