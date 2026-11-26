---
title: "Clases, objetos y encapsulamiento"
description: "Estado, identidad, constructores, acceso y modelos de dominio."
---

**Tipo:** explicación. **Nivel:** esencial. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Variables, valores y constantes](/handbook/java/variables/)
- [Tipos de datos](/handbook/java/types/)
- [Métodos, parámetros y retorno](/handbook/java/methods/)

## Clase e instancia en Java

La definición canónica independiente del lenguaje está en [objetos, estado y comportamiento](/handbook/foundations/objects/). Aquí se desarrolla su implementación mediante clases, campos, constructores y referencias de Java.

Una **clase** define un [tipo](/handbook/java/types/): especifica qué información y operaciones tienen sus objetos. Una **instancia** u objeto es una realización concreta de esa clase, creada durante la ejecución.

El **estado** es el conjunto de valores que caracterizan a un objeto en un momento determinado; por ejemplo, el ID y el título de un libro. El **comportamiento** es lo que el objeto puede hacer, expresado mediante [métodos](/handbook/java/methods/), como consultar el título o actualizarlo si el contrato lo permite.

| Concepto | Ejemplo con Libro |
| --- | --- |
| Clase | La definición Libro |
| Objeto o instancia | Un libro concreto con ID 1 y título Java |
| Estado | id = 1, titulo = "Java" |
| Comportamiento | getTitulo() devuelve su título |
| Campo o atributo | Una variable declarada dentro de la clase, como titulo |
| Constructor | Inicializa un objeto nuevo con datos válidos |

Una [variable](/handbook/java/variables/) almacena un valor. Cuando su tipo es Libro, almacena una [referencia](/handbook/java/references/) al objeto, o null. Asignar la referencia a otra variable no crea una copia.

```java
Libro primero = new Libro(1, "Java");
Libro segundo = primero;
Libro tercero = new Libro(1, "Java");

System.out.println(primero == segundo);
System.out.println(primero == tercero);
```

Fragmento con la clase Libro del ejemplo siguiente. Salida esperada: true y false. Los dos primeros nombres apuntan a la misma instancia; tercero representa otro objeto, aunque sus datos coincidan.

La **encapsulación** protege el estado mediante una interfaz de uso controlada. Una [invariante](/handbook/architecture/contracts/) es una condición que debe mantenerse válida, como exigir un título no vacío.

## Ejemplo completo: contexto ilustrativo de biblioteca

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

Archivo Libro.java, JDK 21. Salida: Java y SQL. El constructor valida y establece el estado inicial. [final](/handbook/java/variables/) impide reasignar los campos; String es inmutable y el campo int guarda un valor primitivo. La [condición](/handbook/java/expressions/) del constructor rechaza null o texto vacío y lanza una [excepción](/handbook/java/exceptions/) si se incumple la regla.

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

## Aplicaciones e implementaciones: persistencia

[ResultSet](/handbook/java/jdbc/result-set/) lee filas y construye objetos. Cada fila requiere una instancia propia. El modelo no debería contener [SQL](/handbook/sql/) ni controles de interfaz: el [DAO y controlador](/handbook/architecture/) se encargan de esos aspectos. Para una UI observable, puedes adaptar el modelo sin introducir JavaFX en el dominio.

## Comprobación

Construye dos libros, verifica sus atributos y provoca un título vacío en un proyecto de prueba: debe lanzarse IllegalArgumentException. Si aparece NullPointerException al invocar un getter, comprueba que la referencia exista.

## De la clase a las instancias

![La clase Libro declara id y titulo y un constructor. Dos invocaciones de new crean dos instancias con sus propios valores.](/diagrams/java-class-instance.svg)

El archivo fuente contiene la declaración de clase, que describe los campos y métodos; cada new reserva e inicializa una nueva instancia en el sentido del modelo de ejecución Java. Los campos de instancia tienen valores propios. Los métodos de instancia trabajan con el receptor (this), mientras los campos static no tienen una copia por objeto. No confundas el nombre de una variable con la identidad del objeto.

## Ejemplo complementario: estado que cambia sin romper reglas

Archivo ContadorValidoDemo.java, Java 21. El dominio es distinto del ejemplo Libro y permite observar una operación de cambio.

```java
public class ContadorValidoDemo {

    private int valor;

    public boolean decrementar() {
        if (valor == 0) {
            return false;
        }
        valor--;
        return true;
    }

    public boolean incrementar() {
        if (valor == Integer.MAX_VALUE) {
            return false;
        }
        valor++;
        return true;
    }

    public int obtenerValor() {
        return valor;
    }

    public static void main(String[] args) {
        ContadorValidoDemo primero = new ContadorValidoDemo();
        ContadorValidoDemo segundo = new ContadorValidoDemo();
        primero.incrementar();
        System.out.println(primero.obtenerValor());
        System.out.println(segundo.obtenerValor());
        primero.decrementar();
        System.out.println(primero.decrementar());
        System.out.println(primero.obtenerValor());
    }
}
```

Salida esperada: 1, 0, false y 0. El constructor implícito y valor inicial por defecto crean contadores válidos; las operaciones controlan los cambios, incluido el desbordamiento. No hay setter que permita invalidar la regla. Consulta [constructores](/handbook/java/constructors/) para inicializaciones explícitas.

## Diferencias entre acceso y encapsulamiento

private controla acceso de código según las reglas de Java (incluidas clases anidadas); por sí solo no garantiza un modelo correcto. Un método público que acepta cualquier valor puede romper invariantes aun con campos privados. Tampoco devuelvas una colección mutable interna si eso permite saltarse el contrato.

## Conceptos relacionados

- [Genéricos](/handbook/java/generics/)
- [Interfaces](/handbook/java/interfaces/)
- [MVC y DAO](/handbook/architecture/)
- [Observabilidad](/handbook/java/javafx/table-view-observability/)

## Documentación oficial

- [Classes and Objects](https://dev.java/learn/classes-objects/)
- [Object, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html)
