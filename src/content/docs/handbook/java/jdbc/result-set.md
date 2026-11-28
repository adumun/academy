---
title: "ResultSet"
description: "Cursor, lectura tipada, NULL, mapeo y colecciones Java."
---

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Colecciones](/handbook/java/collections/)
- [Genéricos](/handbook/java/generics/)
- [Excepciones](/handbook/java/exceptions/)

## Modelo mental

ResultSet permite recorrer [filas](/handbook/sql/relational-model/) obtenidas por una consulta. Cada fila contiene valores de columnas. Un cursor es una posición de lectura que indica qué fila está disponible; no es un índice de una lista Java. Un cursor forward-only (habitual por defecto) avanza en un solo sentido. next() retorna un booleano: true si hay fila disponible, false si terminó. No es una [List](/handbook/java/collections/) ni contiene automáticamente objetos del dominio. Mapear significa construir un objeto Java a partir de los valores de la fila, eligiendo [tipos](/handbook/java/types/) compatibles.

| Estado | ¿Se pueden leer columnas? |
| --- | --- |
| Antes de la primera fila | No |
| Sobre una fila válida | Sí |
| Después de la última fila | No |

Si hay dos filas, las llamadas a next() devuelven true, true y false. Con cero filas, la primera llamada devuelve false.

## API de referencia

| Firma simplificada | Retorno |
| --- | --- |
| boolean next() | Si avanzó a una fila válida |
| int getInt(String label) | Entero; cero ante NULL SQL |
| String getString(String label) | Texto o null |
| java.sql.Date getDate(String label) | Fecha SQL o null |
| BigDecimal getBigDecimal(String label) | Decimal exacto o null |
| boolean wasNull() | Si la última lectura obtuvo NULL SQL |
| T getObject(String label, `Class<T>` type) | Conversión tipada si el driver la admite |
| void close() | Libera recursos |

Los índices de columna también comienzan en 1; preferimos nombres o alias explícitos para reducir errores de mantenimiento.

## Mapeo fila por fila

<!-- example:jdbc-rs-map -->
```java
public List<Libro> mapear(ResultSet rs) throws SQLException {
    List<Libro> libros = new ArrayList<>();

    while (rs.next()) {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");

        Libro libro = new Libro(id, titulo);
        libros.add(libro);
    }

    return libros;
}
```

El método recibe un ResultSet abierto posicionado antes de la primera fila. Quien lo creó conserva la responsabilidad de cerrarlo. Las instancias Libro no dependen del cursor después de crearse.

## Separar el mapeo · complementario

El cursor y el modelo pertenecen a responsabilidades diferentes. Una función de mapeo puede transformar exclusivamente la fila actual y otra encargarse de iterar. El tipo `List<Libro>` utiliza [genéricos](/handbook/java/generics/) y el resultado es una [colección](/handbook/java/collections/) de objetos independientes.

<!-- example:jdbc-rs-separate -->
```java
private Libro mapearFila(ResultSet rs) throws SQLException {
    return new Libro(
            rs.getInt("id"),
            rs.getString("titulo")
    );
}

private List<Libro> mapearTodas(ResultSet rs)
        throws SQLException {

    List<Libro> libros = new ArrayList<>();

    while (rs.next()) {
        libros.add(mapearFila(rs));
    }

    return libros;
}
```

Son métodos de una clase DAO con imports java.sql y java.util correspondientes. mapearFila no llama next(): su precondición es que el cursor ya esté sobre una fila válida. mapearTodas recibe un cursor antes de la primera fila. Separar ambos evita avanzar dos veces y facilita reutilizar mapeo en consultas por ID. No cierres el cursor en un método que no es su propietario.

## Una fila frente a muchas filas

Para buscar por una [clave primaria](/handbook/sql/relational-model/), if (rs.next()) es suficiente cuando esperas como máximo una fila. Para listar, usa while; consulta [decisiones y bucles](/handbook/java/control-flow/) para distinguir una comprobación única de una repetición. No llames next() otra vez dentro del bucle salvo que quieras avanzar deliberadamente y omitir una fila.

## NULL y fechas

Un [entero primitivo](/handbook/java/types/) no puede representar [null](/handbook/java/references/). NULL SQL expresa ausencia de un valor en una columna, no un número cero. getInt devuelve cero si la columna es NULL SQL; consulta wasNull inmediatamente después de leer esa columna.

<!-- example:jdbc-rs-null -->
```java
int cantidad = rs.getInt("cantidad");
Integer cantidadOpcional = rs.wasNull() ? null : cantidad;

java.sql.Date fechaSql = rs.getDate("fecha_apertura");
LocalDate fecha = fechaSql == null ? null : fechaSql.toLocalDate();
```

Si la columna es NOT NULL, la validación del esquema simplifica el mapeo. No uses cero como sustituto de ausencia sin definir esa regla.

Consulta [LocalDate y conversiones JDBC](/handbook/java/dates/) para distinguir fecha sin hora, instante y NULL.

## Alias y proyecciones

```sql
SELECT id, titulo AS nombre_libro FROM libro ORDER BY id;
```

Lee rs.getString("nombre_libro") porque el alias es la etiqueta de columna. Seleccionar columnas explícitamente evita dependencias accidentales de SELECT *.

## Diagnóstico

- Antes de next(): no existe fila actual.
- Column not found: revisa nombres y alias del SELECT.
- Lista con el mismo objeto repetido: crea una instancia por fila.
- Fecha nula: valida antes de toLocalDate().
- ResultSet cerrado: revisa el alcance de recursos y el orden de lectura.

## Comprobación

Ejecuta consultas con cero, uno y dos registros. Compara IDs y títulos contra el SELECT directo. Un resultado vacío es una lista vacía, no un objeto ficticio.

## Estado del cursor y traza de ejecución

![El cursor comienza antes de la primera fila. next devuelve true para fila 1, true para fila 2 y false después de la última fila.](/diagrams/jdbc-cursor.svg)

| Llamada | Retorno | Fila disponible | Elementos agregados |
|---|---|---|---|
| Antes de next | — | Ninguna | 0 |
| Primera next | true | 1: Java | 1 |
| Segunda next | true | 2: SQL | 2 |
| Tercera next | false | Ninguna | 2 |

Los getters no avanzan el cursor. Si se utiliza if en lugar de while para listar, solo se procesa la primera fila. Si se llama next dentro del mapeo y también en el bucle, pueden perderse filas. No asumas que las filas están ordenadas sin ORDER BY.

## Ejemplo autónomo sin servidor: cursor en memoria

Archivo CursorDemo.java, JDK 21. Usa CachedRowSet, una implementación desconectada de ResultSet, para practicar el contrato del cursor sin necesitar MariaDB. No sustituye las pruebas del driver.

<!-- example:jdbc-rs-memory -->
```java
import java.sql.Types;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetMetaDataImpl;
import javax.sql.rowset.RowSetProvider;

public class CursorDemo {

    public static void main(String[] args) throws Exception {
        RowSetMetaDataImpl metadatos = new RowSetMetaDataImpl();
        metadatos.setColumnCount(1);
        metadatos.setColumnName(1, "cantidad");
        metadatos.setColumnType(1, Types.INTEGER);

        try (CachedRowSet resultado = RowSetProvider.newFactory().createCachedRowSet()) {
            resultado.setMetaData(metadatos);
            resultado.moveToInsertRow();
            resultado.updateInt(1, 0);
            resultado.insertRow();
            resultado.moveToCurrentRow();
            resultado.beforeFirst();
            System.out.println(resultado.next());
            System.out.println(resultado.getInt(1));
            System.out.println(resultado.wasNull());
            System.out.println(resultado.next());
        }
    }
}
```

Salida esperada: true, 0, false, false. El valor cero existe y wasNull es false. Cambia updateInt por updateNull(1): la lectura sigue devolviendo 0, pero wasNull debe ser true. El ejemplo separa la semántica del cursor de la conexión y de un caso de negocio específico.

## Conceptos relacionados

- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [Clases y colecciones](/handbook/java/fundamentals/)
- [DAO y MVC](/handbook/architecture/)
- [JavaFX TableView](/handbook/java/javafx/table-view/)

## Documentación oficial

- [ResultSet, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/ResultSet.html)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
