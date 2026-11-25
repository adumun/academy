---
title: MVC y DAO
description: Separación de responsabilidades en aplicaciones Java.
---

El modelo representa el estado del dominio; la vista presenta información; el controlador coordina la interacción. El DAO encapsula el acceso a datos.

```java
public interface LibroDao {

    List<Libro> listarTodos() throws SQLException;

    int insertar(Libro libro) throws SQLException;
}
```

El contrato no depende de JavaFX ni de un motor de datos concreto. La implementación JDBC prepara consultas y transforma resultados a objetos. El controlador consume el contrato.

[Referencia JDBC](/handbook/java/jdbc/)
