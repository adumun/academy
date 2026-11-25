---
title: JDBC
description: Conexión, consultas y transformación de datos en Java.
---

JDBC es la API estándar de Java para acceder a bases de datos mediante un driver compatible. Separa las operaciones Java de los detalles del protocolo de cada motor.

## Ciclo de una consulta

1. Obtener una Connection.
2. Preparar una sentencia.
3. Asignar parámetros.
4. Ejecutar la consulta o modificación.
5. Leer los resultados y cerrar recursos.

## Conceptos relacionados

- [PreparedStatement](/handbook/java/jdbc/prepared-statement/)
- [ResultSet y cursor](/handbook/java/jdbc/result-set/)
- [SQL y CRUD](/handbook/sql/)
- [MVC y DAO](/handbook/architecture/)

## Documentación oficial

[Java 21: java.sql](https://docs.oracle.com/en/java/javase/21/docs/api/java.sql/java/sql/package-summary.html)

## Practicar en Academic Labs

- [Laboratorio guiado](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Detective JDBC](https://academic-labs.adumun.cl/activities/dsy1102-detective-jdbc/)
