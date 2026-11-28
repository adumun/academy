# JDBC — conexión y permisos reales

ADU-225, primer incremento. JDK 21, MariaDB Connector/J 3.5.11 y JUnit Jupiter 5.11.4.

En un entorno Linux aislado con Docker, Maven y Python 3, desde la raíz de Academy:

```bash
bash scripts/test-jdbc-mariadb.sh
```

El script crea una instancia desechable de MariaDB 11.4, enlazada exclusivamente a 127.0.0.1:3306, con credenciales aleatorias no persistidas. La cuenta de aplicación tiene únicamente SELECT, INSERT, UPDATE y DELETE en el esquema de práctica. El host % se usa exclusivamente dentro del contenedor efímero para admitir el acceso desde el puente Docker; no es una configuración recomendada para un servidor compartido. No se monta un volumen de datos. La imagen se registra por digest y el servidor registra su versión. No usar contra una instancia existente ni ejecutar con el puerto ocupado.

Los cinco casos previstos comprueban sesión/esquema, salida de la ficha, rechazo de credenciales, permisos y lectura/cierre de recursos. No certifican CRUD, transacciones, conversiones ni integración JavaFX–DAO; esos son incrementos posteriores de ADU-225. El resultado se verifica en CI, no se presume.

## Segundo incremento — sentencias y resultados

Las fichas PreparedStatement y ResultSet añaden nueve fragmentos canónicos, incluido CursorDemo desconectado. Los ejemplos de sentencias y mapeo comparten un modelo Libro mínimo. El harness añade la tabla de práctica detalle_libro para probar NULL, DATE y DECIMAL, sin modificar el contrato de libro. Cada caso de JdbcStatementsTest abre una sesión exclusiva, desactiva auto-commit y revierte sus datos al terminar; no presupone IDs consecutivos ni orden de ejecución de JUnit. La lectura del fragmento que consulta ID 7 usa una fila explícita de prueba.

Se esperan doce casos adicionales: búsqueda presente/ausente, actualización, apóstrofos como datos, clave generada, fecha nula/válida, lectura de ID fijo, cero/una/múltiples filas, mapeo separado, NULL/cero y tipos, parámetros inválidos, alias/etiqueta inexistente, y cursor en memoria. El rollback del fixture es aislamiento de pruebas, no certificación de la ficha de transacciones. La evidencia de persistencia tras commit y de integración JavaFX–DAO sigue pendiente.
