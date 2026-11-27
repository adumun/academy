# JSON y JUnit — ejemplos del Handbook

Proyecto no modular Java 21 / Maven. Desde esta carpeta: `mvn -B -ntp clean test`. Las clases de demostración mantienen el mismo contrato de salida (21); JUnit prueba esas mismas fuentes, no algoritmos copiados.

16 pruebas esperadas: 6 Jackson, 6 Gson, 2 RangoTest y 2 RangoBoundaryTest. Verificar informes en target/surefire-reports. El caso de campo ausente documenta el valor por defecto del record; no equivale a validación de negocio. Gson configura Strictness.STRICT; Jackson utiliza su configuración predeterminada. Las dos bibliotecas son alternativas: ambas están en este POM únicamente para verificar las dos fichas.

Versiones didácticas fijadas según las fichas (Gson 2.11.0 elegido para records y Strictness); no son una certificación de seguridad ni una recomendación de usar las últimas versiones. No se necesita base de datos, credenciales ni interfaz gráfica. ADU-232 verifica runtime; ADU-224 y ADU-225 cubren otros entornos. El runner JDK puro no ejecuta este proyecto. CI comprueba además una regresión deliberada de Rango en una copia temporal sin cambiar el árbol fuente.
