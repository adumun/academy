---
title: "Fechas con LocalDate y JDBC"
description: "Fechas sin hora, conversión y validación de entrada."
---

**Tipo:** explicación y referencia. **Nivel:** complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Excepciones](/handbook/java/exceptions/)

## Elegir el tipo correcto

| Tipo | Representa |
| --- | --- |
| LocalDate | Fecha de calendario sin hora ni zona |
| LocalDateTime | Fecha y hora local sin zona |
| Instant | Instante en la línea temporal |
| java.sql.Date | Representación JDBC tradicional de DATE |

Una fecha de nacimiento o vencimiento normalmente requiere LocalDate; un instante de auditoría puede requerir Instant. No agregues una hora o zona que el dominio no necesita.

## API y ejemplo autónomo

```java
import java.time.LocalDate;

public class FechasDemo {

    public static void main(String[] args) {
        LocalDate fecha = LocalDate.of(2026, 11, 25);
        LocalDate siguiente = fecha.plusDays(1);

        System.out.println(fecha);
        System.out.println(siguiente);
        System.out.println(fecha.isBefore(siguiente));
    }
}
```

Salida: 2026-11-25, 2026-11-26 y true en líneas distintas. LocalDate es inmutable: plusDays retorna otro objeto.

## Conversión para DATE

```java
java.sql.Date fechaSql = java.sql.Date.valueOf(fecha);
ps.setDate(1, fechaSql);

java.sql.Date recibida = rs.getDate("fecha");
LocalDate resultado = recibida == null
        ? null
        : recibida.toLocalDate();
```

Fragmento con fecha no nula, PreparedStatement y ResultSet válidos. Si el valor es opcional, usa setNull con Types.DATE. JDBC 4.2 también permite setObject/getObject tipados cuando el driver soporta esa conversión.

## Validación y diagnóstico

DatePicker.getValue() retorna LocalDate o null. Comprueba presencia y reglas del dominio antes de convertir. LocalDate.parse usa formato ISO por defecto; un formato local requiere DateTimeFormatter explícito. Una fecha imposible provoca una excepción, no se convierte automáticamente a una fecha válida.

## Conceptos relacionados

- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [ResultSet](/handbook/java/jdbc/result-set/)
- [Validación JavaFX](/handbook/java/javafx/validation/)

## Documentación oficial

- [LocalDate](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/LocalDate.html)
- [java.sql.Date](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/Date.html)
