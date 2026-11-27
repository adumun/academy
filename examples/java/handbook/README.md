# Ejemplos ejecutables del Handbook

Lote fundamentos: métodos, variables, tipos, expresiones, control de flujo y referencias. Cada concepto tiene fuentes y pruebas; no un repositorio por cápsula. JDK 21 y Node.js. `npm run sync:snippets` regenera los bloques registrados; `npm run validate:snippets` detecta divergencias; `npm run test:java-sources` compila y ejecuta las clases y pruebas reales. La suite histórica de 34 ejemplos sigue disponible mediante `npm run test:java-examples`. `test-suites.json` registra ejecuciones por concepto; las clases de fragmentos ejecutan las regiones mostradas y verifican resultados o salidas. No se ha migrado el resto del Handbook. Los proyectos integradores permanecen en `examples/java/projects/`.

Los archivos de `negative/` son deliberadamente inválidos; no forman parte de las suites positivas. `negative-cases.json` vincula cada caso con la ficha y el diagnóstico esperado de javac 21. El runner exige código de salida 1 y exactamente el diagnóstico esperado; un fallo arbitrario de compilación no cuenta como prueba exitosa.

Lote POO: clases/objetos/encapsulamiento, constructores, colaboración/composición, herencia, clases abstractas e interfaces. Las pruebas adicionales comprueban invariantes, alias, límites, despacho y sustitución. El método amplitud devuelve long para preservar el rango completo de int.

Lote Java intermedio: arrays, colecciones, genéricos, lambdas, streams y Optional. Los fragmentos son regiones de clases compilables; las suites ejercitan contratos y casos de frontera.

Excepciones, fechas y archivos incluyen pruebas de límites de aplicación con DAO/JDBC simulados y recursos temporales reales. Los dobles de prueba no equivalen a integración con base de datos.
