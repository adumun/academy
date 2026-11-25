---
title: "CRUD con JDBC"
description: "Operaciones completas de acceso a datos y comprobación de filas afectadas."
---

## Contrato básico

CRUD significa crear, leer, actualizar y eliminar registros. No confundir crear una fila con CREATE TABLE, que crea una estructura. En estos métodos se supone la tabla libro definida en la ficha SQL, el modelo Libro y DatabaseConnection de la ficha de conexión.

## INSERT

```java
public int insertar(Libro libro) throws SQLException {
    String sql = "INSERT INTO libro (titulo) VALUES (?)";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, libro.getTitulo());
        return ps.executeUpdate();
    }
}
```

La base genera el ID cuando la columna es AUTO_INCREMENT. Si necesitas recuperarlo inmediatamente, estudia Statement.RETURN_GENERATED_KEYS y getGeneratedKeys().

## SELECT

```java
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
```

## UPDATE

```java
public int actualizar(Libro libro) throws SQLException {
    String sql = "UPDATE libro SET titulo = ? WHERE id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, libro.getTitulo());
        ps.setInt(2, libro.getId());
        return ps.executeUpdate();
    }
}
```

## DELETE

```java
public int eliminar(int id) throws SQLException {
    String sql = "DELETE FROM libro WHERE id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        return ps.executeUpdate();
    }
}
```

## Matriz de pruebas

| Caso | Resultado esperado |
| --- | --- |
| Insertar título válido | Una fila nueva |
| Listar tabla vacía | Lista vacía |
| Actualizar ID existente con título diferente | Registro objetivo modificado |
| Actualizar ID inexistente | Cero filas afectadas |
| Eliminar ID existente | Una fila eliminada |
| Eliminar otra vez el mismo ID | Cero filas afectadas |
| Reiniciar programa | Los cambios confirmados persisten |

Verifica con SQL y no solamente observando TableView. Nunca ejecutes UPDATE o DELETE sin condición en un entorno con datos que debas conservar.

## Decisiones de diseño

Los métodos retornan conteos para permitir al controlador decidir qué comunicar. La excepción informa fallos de acceso. No devuelvas true incondicionalmente ni ocultes SQLException. Para varias escrituras que deben confirmarse juntas, utiliza una única conexión y transacción.

## Conceptos relacionados

- [SQL](/handbook/sql/)
- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [DAO y MVC](/handbook/architecture/)
- [Transacciones](/handbook/java/jdbc/transactions/)

## Documentación oficial

- [Connection](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/Connection.html)
- [PreparedStatement](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/PreparedStatement.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
