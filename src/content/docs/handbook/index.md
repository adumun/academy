---
title: "Handbook"
description: "Índice de conocimiento técnico reutilizable y consulta no lineal."
---

El Handbook organiza conocimiento por tecnologías y disciplinas, independientemente de la asignatura que lo utilice. Busca una clase, método, error o problema mediante el buscador. No es necesario leer las páginas en orden.

## Cómo navegar el conocimiento

Las fichas contienen enlaces dentro de sus explicaciones y conocimientos previos recomendados. Si un término no te resulta familiar, abre su ficha canónica y vuelve al objetivo original. No necesitas seguir un orden fijo ni completar un curso.

## Elegir el nivel de consulta

| Necesidad | Dónde buscar |
|---|---|
| Entender un término o principio sin depender de un lenguaje | Fundamentos |
| Aprender sintaxis y comportamiento específico de Java | Java |
| Consultar un método de acceso a datos o un control gráfico | JDBC o JavaFX |
| Decidir cómo distribuir responsabilidades | Arquitectura |
| Configurar, construir o diagnosticar un proyecto | Herramientas |

En una ficha de referencia, el diagrama explica una relación, un flujo o un estado; el código muestra cómo se expresa el comportamiento en un lenguaje concreto. Las tablas de contraste ayudan a elegir una alternativa o reconocer un error. No es necesario que cada ficha tenga todos esos formatos: cada representación debe aportar comprensión. Diferencia una salida esperada de una prueba ejecutada. Sigue el enlace a la práctica cuando quieras comprobar el conocimiento en otro contexto.

## Fundamentos independientes del lenguaje

- [Programas y algoritmos](/handbook/foundations/programs/)
- [Valores, variables y tipos](/handbook/foundations/values-and-types/)
- [Objetos, estado y comportamiento](/handbook/foundations/objects/)
- [Operaciones, funciones y contratos](/handbook/foundations/operations/)
- [Expresiones, decisiones y repetición](/handbook/foundations/flow/)

- [Paradigmas de programación](/handbook/foundations/paradigms/)
- [Abstracción y relaciones OO](/handbook/foundations/object-relationships/)
- [Estructuras de datos](/handbook/foundations/data-structures/)

- [JSON](/handbook/foundations/json/)
- [Memoria y persistencia](/handbook/foundations/persistence/)
- [Pruebas y evidencia](/handbook/foundations/testing/)

- [Lógica proposicional](/handbook/foundations/boolean-logic/)
- [Matemáticas para programar](/handbook/foundations/programming-math/)
- [Del requerimiento al algoritmo](/handbook/foundations/problem-solving/)

## Java

- [Fundamentos Java y POO](/handbook/java/fundamentals/): clases, colecciones, interfaces y excepciones.
- [JDBC](/handbook/java/jdbc/): conexiones, consultas, mapeo y persistencia.
- [JavaFX](/handbook/java/javafx/): ciclo de vida, FXML, controles, TableView y validaciones.

### Fundamentos específicos de Java

Si necesitas partir desde el significado de los términos, estas fichas explican la sintaxis y el comportamiento de Java. Las definiciones generales se encuentran en Fundamentos.

- [Programas, código fuente y ejecución](/handbook/java/programming/)
- [Variables, valores y constantes](/handbook/java/variables/)
- [Tipos de datos y conversiones](/handbook/java/types/)
- [Referencias, identidad y null](/handbook/java/references/)
- [Métodos, parámetros y retorno](/handbook/java/methods/)
- [Expresiones y operadores](/handbook/java/expressions/)
- [Control de flujo](/handbook/java/control-flow/)
- [Paquetes e imports](/handbook/java/packages-and-imports/)

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Genéricos](/handbook/java/generics/)
- [Colecciones y List](/handbook/java/collections/)
- [Interfaces y polimorfismo](/handbook/java/interfaces/)
- [Excepciones y recursos](/handbook/java/exceptions/)
- [Lambdas](/handbook/java/lambdas/)

- [Constructores](/handbook/java/constructors/)
- [Colaboración y composición](/handbook/java/composition/)
- [Herencia y polimorfismo](/handbook/java/inheritance/)
- [Clases abstractas](/handbook/java/abstract-classes/)
- [Arrays](/handbook/java/arrays/)
- [Set](/handbook/java/sets/) y [Map](/handbook/java/maps/)
- [Igualdad y hashCode](/handbook/java/equality/)
- [Scanner y consola](/handbook/java/scanner/)

- [Archivos: Path y Files](/handbook/java/files/)
- [Streams](/handbook/java/streams/)
- [Serialización JSON: Jackson y Gson](/handbook/java/json-serialization/)
- [JUnit](/handbook/java/junit/)

## Datos y arquitectura

- [Datos, persistencia y modelo relacional](/handbook/sql/relational-model/)
- [Contratos, responsabilidades y dependencias](/handbook/architecture/contracts/)

- [SQL y CRUD](/handbook/sql/): esquema, tipos, restricciones y operaciones.
- [MVC y DAO](/handbook/architecture/): responsabilidades, contratos y acceso a datos.

## Herramientas y diagnóstico

- [Sistema de archivos](/handbook/tools/filesystem/)
- [Terminal](/handbook/tools/terminal/)
- [Entorno de desarrollo](/handbook/tools/development-environment/)
- [Git y GitHub](/handbook/tools/git/)
- [Estrategias de ramas](/handbook/tools/git-branching/)
- [Markdown](/handbook/tools/markdown/)

- [Maven y entorno Java](/handbook/tools/maven/): dependencias, estructura y build.
- [Diagnóstico de errores](/handbook/troubleshooting/): buscar síntomas y comprobar hipótesis.

## Cómo utilizar cada ficha

Consulta el propósito, [contrato](/handbook/architecture/contracts/) (entradas, resultados y reglas), ejemplo, diagnóstico y prueba observable. Una ficha canónica es la explicación principal de un concepto; las demás páginas pueden resumirlo y enlazarla. En los enlaces dentro del texto se explican términos que podrían ser nuevos, sin obligarte a seguir una ruta completa. Los fragmentos indican su contexto; no todos son programas independientes. Las referencias oficiales permiten profundizar. Las actividades prácticas se desarrollan en Academic Labs.

## Conceptos relacionados

- [Ruta DSY1102](/paths/dsy1102/)
- [Ruta Software Developer](/paths/software-developer/)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
