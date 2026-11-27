# Cápsula del Tiempo FX · R08

Adaptación de R04 con archivos JDBC de referencia. Requiere JDK 21, Maven y MariaDB local. Ejecuta `sql/01-esquema.sql` con una cuenta administradora local y adapta `sql/02-usuario-local.sql` con una contraseña propia antes de ejecutarlo. Configura DB_USER, DB_PASSWORD y, si corresponde, DB_URL como variables de entorno. No almacenes credenciales. Desde esta carpeta: `mvn compile`, `mvn javafx:run`. Consulta la guía en Academy. R07 prueba INSERT, R08 añade SELECT al DAO. No se ha verificado la integración real en esta adaptación (ADU-236).
