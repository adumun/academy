---
title: "MVC y DAO"
description: "Responsabilidades, contratos, dependencias y refactorización de aplicaciones."
---

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Interfaces y polimorfismo](/handbook/java/interfaces/)
- [Colecciones y genéricos](/handbook/java/collections/)
- [Excepciones](/handbook/java/exceptions/)

## Por qué separar responsabilidades

Un patrón de diseño describe una solución reutilizable a un problema recurrente, no código obligatorio. MVC (Model-View-Controller) organiza la interacción con la interfaz: modelo, vista y controlador. DAO (Data Access Object) encapsula operaciones de acceso a datos, de modo que el resto de la aplicación utiliza su contrato sin conocer los detalles de almacenamiento. Son conceptos distintos que se complementan. Una [responsabilidad](/handbook/architecture/contracts/) es el trabajo que corresponde a una parte del software; un contrato define operaciones y resultados; una dependencia existe cuando una parte necesita otra para funcionar.

| Pieza | Responsabilidad | Evita |
| --- | --- | --- |
| Modelo | Representar datos y reglas | SQL y controles gráficos |
| Vista FXML | Estructura visual | Consultas y credenciales |
| Controller | Coordinar interacción, validación y vista | SQL disperso en eventos |
| DAO | Acceso a datos y mapeo | Alert, TableView |
| DatabaseConnection | Crear conexiones configuradas | Reglas de negocio |

Dependencias habituales: vista → controlador → contrato DAO → implementación JDBC → base. Modelo compartido por controlador y DAO. No es obligatorio introducir una capa Service en un CRUD simple; incorpórala cuando exista lógica de negocio que justifique esa separación.

## Contrato DAO

```java
public interface LibroDao {
    List<Libro> listarTodos() throws SQLException;
    int insertar(Libro libro) throws SQLException;
    int actualizar(Libro libro) throws SQLException;
    int eliminar(int id) throws SQLException;
}
```

Define firmas, resultados y comportamiento ante errores. Este contrato educativo usa SQLException; en diseños que requieren independencia estricta de JDBC podría traducirse a una excepción propia.

## Implementación

```java
public class LibroDaoMySql implements LibroDao {

    @Override
    public List<Libro> listarTodos() throws SQLException {
        String sql = "SELECT id, titulo FROM libro ORDER BY id";
        List<Libro> libros = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo")
                );
                libros.add(libro);
            }
        }

        return libros;
    }

    // Implementar insertar, actualizar y eliminar según el contrato.
}
```

Es un fragmento parcial de clase: para compilar necesita los otros tres métodos (consulta la ficha CRUD), los imports y las clases del modelo y conexión.

## Consumo desde un Controller

```java
private final LibroDao dao = new LibroDaoMySql();

private void cargar() {
    try {
        tablaLibros.getItems().setAll(dao.listarTodos());
    } catch (SQLException e) {
        mostrarError("No se pudo cargar el listado.");
    }
}
```

El controlador conoce el contrato; en este ejemplo simple selecciona una implementación. Más adelante puede recibirla mediante inyección de dependencias: el consumidor recibe la implementación desde fuera, por ejemplo en un constructor, en lugar de construirla por sí mismo. Consulta [contratos y dependencias](/handbook/architecture/contracts/). El DAO no conoce controles JavaFX.

## Sustituir una implementación · complementario

El DAO es un [contrato](/handbook/java/interfaces/), no un nombre de clase obligatorio. Una implementación en memoria permite aislar el consumidor de la infraestructura.

```java
public class LibroDaoMemoria implements LibroDao {

    private final List<Libro> libros = new ArrayList<>();

    @Override
    public List<Libro> listarTodos() {
        return new ArrayList<>(libros);
    }

    @Override
    public int insertar(Libro libro) {
        libros.add(libro);
        return 1;
    }

    @Override
    public int actualizar(Libro libro) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getId() == libro.getId()) {
                libros.set(i, libro);
                return 1;
            }
        }

        return 0;
    }

    @Override
    public int eliminar(int id) {
        int anterior = libros.size();
        libros.removeIf(libro -> libro.getId() == id);
        return anterior - libros.size();
    }
}
```

Ejemplo educativo parcial de semántica: presupone IDs únicos asignados por el llamador; no reproduce generación de claves, restricciones SQL ni transacciones. No sustituye la prueba JDBC real. Utiliza [colecciones](/handbook/java/collections/) y una [lambda](/handbook/java/lambdas/) en removeIf. En un test del controlador, inyecta el contrato en lugar de acoplarlo a una implementación concreta.

## Refactorización en pasos verificables

1. Identifica SQL y mapeo dentro del controlador.
2. Define las operaciones del contrato.
3. Mueve la lógica JDBC a su implementación.
4. Sustituye el SQL del controlador por llamadas al DAO.
5. Ejecuta las mismas pruebas de lectura, escritura y selección.

Si el comportamiento cambia involuntariamente, revisa antes de continuar. Refactorizar es modificar estructura sin alterar el comportamiento observable esperado.

## Errores de diseño

No crees interfaces que no representan un contrato útil. No uses una clase DAO global que administra también ventanas. Evita que una implementación JDBC devuelva TableView o muestre Alert. Si dos operaciones forman una unidad transaccional, coordina la transacción explícitamente en lugar de asumir que DAOs con conexiones distintas comparten commit.

## Cómo fluye una operación entre responsabilidades

![La vista entrega eventos al controlador, que coordina un servicio opcional y el DAO; el DAO usa el motor. Los datos y errores retornan por las mismas responsabilidades, sin SQL en la vista.](/diagrams/mvc-dao-flow.svg)

En una aplicación con reglas, el controlador obtiene datos de la vista, convierte y valida formato, llama al servicio y presenta su resultado. El servicio protege reglas y coordina el acceso al DAO. El DAO traduce operaciones a SQL y mapea resultados; el motor protege restricciones propias del almacenamiento. En un CRUD sencillo el controlador puede utilizar directamente el contrato DAO, siempre que no se acumulen reglas de aplicación en eventos.

### Traza de un alta

1. El usuario solicita registrar; el controlador interpreta los campos y construye la entrada.
2. El responsable de la operación valida reglas del modelo y solicita persistencia.
3. El DAO ejecuta la sentencia y retorna resultado o error.
4. Solo cuando la operación se confirma se informa éxito. Si después falla el refresco de tabla, informa por separado que el guardado ocurrió pero no pudo actualizarse la vista.

| Caso | Responsable y resultado |
|---|---|
| Campo no convertible | Controller: informar error de formato, no escribir |
| Regla inválida | Modelo/Service: rechazar, sin cambio de estado válido |
| Restricción SQL incumplida | DAO: comunicar fallo según contrato |
| Escritura exitosa, refresco fallido | Controller: distinguir ambos resultados |

La dirección de llamadas y la dependencia de tipos no son necesariamente iguales. El punto de composición puede crear DAO y entregarlo a los consumidores; no es necesario que cada Controller conozca su clase concreta.

El DAO en memoria de esta ficha es una aproximación parcial: antes de utilizarlo como doble de prueba, define si los IDs son asignados por el cliente o generados por el motor y qué debe ocurrir con duplicados. No declarar equivalencia de implementaciones que no cumplen las mismas precondiciones y resultados.

## Conceptos relacionados

- [Java y POO](/handbook/java/fundamentals/)
- [CRUD con JDBC](/handbook/java/jdbc/crud/)
- [JavaFX Controller](/handbook/java/javafx/fxml-controller/)

## Documentación oficial

- [Interfaces Java](https://dev.java/learn/interfaces/)
- [JDBC Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/package-summary.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
