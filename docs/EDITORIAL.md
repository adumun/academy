# Política editorial inicial

Las fichas se organizan por el alcance propio del conocimiento (fundamentos, disciplina, lenguaje, tecnología o aplicación), no por asignatura ni caso de negocio. Las rutas enlazan fichas sin duplicarlas. Cada ficha declara título, descripción, propósito, contexto, sintaxis, ejemplos con llaves e indentación, errores y fuentes cuando corresponda. Distinguir fragmento de programa completo. No copiar material institucional reservado, credenciales ni datos personales.

El contenido inicial es redacción original basada en APIs públicas y documentación oficial enlazada. La revisión de procedencia/licencias y el inventario ADU-86 siguen pendientes. No publicar una licencia global sin autorización del titular. Revisión mediante PR y verificación de enlaces/build.

## Navegación por propósito

Handbook y referencias API son recursos de consulta no lineal: búsqueda, menú jerárquico, enlaces directos y conceptos relacionados. No mostrar paginación automática anterior/siguiente. Las fichas incluyen referencias oficiales y actividades pertinentes cuando existan. Los tutoriales o rutas de aprendizaje pueden incorporar secuencias explícitas cuando aporten valor pedagógico; no dependen del orden accidental del menú. La paginación global de Starlight queda desactivada para el MVP.

## Estándar editorial v1.1 — aprobado como dirección de producto

Inspirado en [Diátaxis](https://diataxis.fr/), [MDN](https://developer.mozilla.org/), [Dev.java](https://dev.java/learn/), [Baeldung](https://www.baeldung.com/) y [JavaScript.info](https://javascript.info/). Son referentes de organización y claridad, no fuentes para reproducir artículos. El contrato técnico se contrasta con documentación primaria versionada.

| Tipo | Necesidad | Estructura mínima |
| --- | --- | --- |
| Explicación | Comprender | Problema, modelo mental, relaciones, límites, alternativas y contraejemplos |
| Referencia | Consultar | Tipo/API, firma, argumentos, retorno, comportamiento, restricciones, ejemplos mínimos |
| Guía práctica | Resolver | Objetivo, requisitos, pasos, código contextualizado, resultado, diagnóstico |
| Tutorial | Aprender haciendo | Experiencia guiada, checkpoints y resultado tangible; enlazar Academic Labs cuando exista |

Una página puede comenzar como explicación o referencia; cuando un caso crece, extraerlo a una guía enlazada sin duplicar contenido. La arquitectura es temática; el tipo documental no es un orden de lectura obligatorio.

### Profundidad y audiencia

- **Esencial:** fundamentos y operaciones necesarias para comenzar.
- **Complementario:** alternativas, comportamientos, casos límite y diagnóstico.
- **Avanzado:** personalización, rendimiento, concurrencia y diseño, con límites explícitos.
- Las etiquetas son recomendaciones de alcance, no evaluaciones ni certificaciones.
- Documentar versiones, paquetes, prerrequisitos y diferencias entre código completo y fragmentos. No declarar un ejemplo probado hasta ejecutarlo.
- Usar ejemplos originales, código legible, llaves explícitas, imports claros, una instrucción por línea y resultados observables.
- No mezclar acciones de UI con persistencia ni simplificar contratos hasta volverlos incorrectos.
- Añadir diagramas o demostraciones cuando expliquen un comportamiento difícil; no por decoración.

### Definition of Done editorial

- [ ] La página responde a una necesidad concreta y declara su tipo o alcance.
- [ ] Explica el porqué, los límites y al menos un caso relevante, según el tipo.
- [ ] Los métodos y comportamientos se contrastan con la API de la versión indicada.
- [ ] Los ejemplos declaran contexto, dependencias y resultado; distinguir revisión estática de ejecución.
- [ ] Errores frecuentes, causas y comprobaciones están cubiertos cuando corresponde.
- [ ] Enlaces internos válidos, fuentes primarias pertinentes, práctica relacionada cuando existe.
- [ ] Cada término técnico necesario para comprender el contenido se define brevemente o enlaza en su primera aparición significativa, según nivel y contexto; no se presupone vocabulario desconocido.
- [ ] El conocimiento es independiente de sus consumidores; las dependencias respetan su alcance y las aplicaciones concretas están identificadas como ejemplos o integraciones.
- [ ] No reproduce material de terceros ni contenido reservado.
- [ ] Revisión técnica, pedagógica, visual móvil y build antes de declarar publicación.

Primer piloto: TableView, con referencia, explicación de observabilidad y guía de filtrado/ordenamiento separadas. Mantener sus URLs existentes. No ampliar masivamente el catálogo hasta revisar el piloto.

## Relaciones de conocimiento — ADU-221

Usar enlaces Markdown dentro de la prosa en la primera aparición significativa de un concepto, preferir fichas canónicas independientes y añadir conocimientos previos recomendados cuando permitan identificar brechas. No enlazar cada repetición ni crear páginas vacías. Distinguir prerequisito, relación complementaria y aplicación; no imponer bloqueos ni dependencias circulares obligatorias. No introducir un nuevo schema de grafo o IDs hasta reconciliar ADR-011, ADU-86 y ADU-94. Mantener rutas, URLs y contenidos públicos; enlaces relativos al origen. La ficha de fundamentos es síntesis contextual, no una segunda autoridad de cada concepto.

### Límite entre documentación y aprendizaje evaluable

Los prerrequisitos editoriales son recomendaciones de lectura, no dependencias obligatorias entre competencias ni evidencia de dominio. Una URL canónica identifica una ficha documental; no representa automáticamente una competencia, una cápsula o una versión de un resultado de aprendizaje. La eventual asociación debe ser explícita, versionada y compatible con los contratos de sus respectivos propietarios. La reconciliación interna se registra en ADU-221; no se introducen campos nuevos en contratos externos ni se publica contenido interno de otros repositorios.

### Controles reproducibles

`npm run validate:content` comprueba frontmatter mínimo, rutas internas y delimitadores de bloques de código. `npm run test:java-examples` extrae los ejemplos autónomos registrados en la suite de las fichas y compara su salida con expectativas usando JDK 21. El build ejecuta la validación de contenido; la suite Java es separada para no exigir Java al hosting estático. La comprobación no sustituye pruebas de JavaFX/MariaDB, validación de enlaces externos o revisión visual.

## Regla de cobertura semántica — ADU-221

Antes de aceptar una ficha, leerla desde la perspectiva de una persona que conoce menos que el autor. Preguntar por cada término técnico significativo: ¿qué es?, ¿qué conocimiento presupone?, ¿necesita una explicación aquí o una ficha reutilizable?

- Definición local: explica en una frase el término necesario para entender la oración; usa un ejemplo o contraste si evita ambigüedad (estado frente a comportamiento, valor frente a referencia, validación frente a conversión).
- Ficha canónica: crea una página útil y autónoma cuando el concepto se reutiliza o necesita profundidad; enlázala desde el texto y, si aporta orientación, desde los conocimientos previos recomendados. No crear páginas vacías ni duplicar una explicación extensa.
- Nivel: el lector puede seguir leyendo con el recordatorio o profundizar sin obligación de recorrer todos los enlaces. No enlazar palabras ordinarias ni cada repetición.
- Cierre: auditar introducciones, tablas de API, explicaciones de código, errores y rutas. Identificar acrónimos, términos en inglés y supuestos tácitos. Registrar brechas detectadas y soluciones; un build verde no demuestra comprensión pedagógica.
- Los enlaces de consulta pueden tener ciclos semánticos; no representan dependencias obligatorias de competencias. Mantener separación con Learning Roadmaps y Competency Engine.

## Estándar editorial v1.2 — independencia y taxonomía del conocimiento

**Knowledge First / Consumer Agnostic.** La autoridad canónica de un concepto vive en el alcance más general en que su definición sea correcta. Las aplicaciones, rutas, ejemplos y tecnologías consumen ese conocimiento; no lo condicionan. La regla aplica a toda contribución, humana o asistida, y complementa la cobertura semántica y el estándar v1.1.

| Alcance | Responsabilidad | Ejemplo |
| --- | --- | --- |
| Fundamentos | Definiciones independientes del lenguaje y negocio | Valor, tipo, variable, algoritmo, objeto, estado, comportamiento |
| Lenguaje | Sintaxis, semántica y APIs propias del lenguaje | Variables y referencias en Java |
| Disciplina | Modelos y principios de una materia | Modelo relacional, contratos, Observer |
| Tecnología/herramienta | API, configuración y límites de una tecnología | JDBC, JavaFX, Maven |
| Aplicación/integración | Combinar conocimientos para resolver un problema | Java + persistencia, MVC/DAO, diagnóstico integral |
| Navegación/ruta | Agrupar referencias para un propósito | DSY1102, Software Developer |

Los scopes editoriales se identifican por ruta en `scripts/knowledge-taxonomy.mjs`; las URLs históricas de guías combinadas conservan su dirección y se clasifican explícitamente como aplicación. No introducir IDs de competencias, esquemas de grafo o dependencias de dominio para representar esta clasificación. La autoridad de los contratos de aprendizaje permanece fuera del Handbook.

### Relaciones y dirección

- **Prerrequisito recomendado**: conocimiento necesario para entender el tema en su alcance. Declararlo en una sección Conocimientos previos recomendados. Un fundamento no requiere un lenguaje; un lenguaje no requiere un framework; una disciplina no requiere una implementación concreta; una referencia general de JavaFX no requiere JDBC. Las integraciones pueden combinar sus componentes.
- **Referencia contextual**: enlace a una definición que amplía el significado de un término. Mantener recordatorios breves y dirigir al concepto canónico; no convertir referencias inversas en requisitos.
- **Aplicación o implementación**: ejemplo particular de una definición general, identificado en Ejemplo, Aplicación o Implementación. Puede usar un dominio ilustrativo, biblioteca, comercio o videojuegos, pero la explicación general no debe depender de ese dominio.
- **Concepto relacionado**: navegación opcional, incluso de un fundamento hacia una implementación; no implica prerrequisito ni progreso obligatorio. Las referencias recíprocas son legítimas; no prohibir ciclos de navegación.

### Revisión de acoplamiento semántico

1. Prueba de sustitución: ¿la definición sigue siendo válida si cambiamos biblioteca por comercio o por un contador? Si no, extrae el concepto general o reclasifica la ficha como aplicación.
2. Prueba de tecnología: ¿el concepto existe sin Java/JDBC/JavaFX? Si existe, su definición principal no pertenece a una tecnología; enlaza la implementación específica.
3. Prueba de dependencia: ¿se necesita realmente leer la ficha destino para comprender el concepto, o solo para ejecutar el ejemplo? Ubica el enlace en la relación correcta.
4. Prueba de autoridad: evita definiciones incompatibles o repetidas como fuentes canónicas; un recordatorio breve con enlace está permitido.
5. Prueba de ejemplos: contexto, requisitos y salida claros; no presentar un caso de negocio como regla del lenguaje. Mantén URLs y pruebas existentes o migra con una estrategia explícita.

### Gate automatizado

- `npm run validate:taxonomy`: clasifica cada página; rechaza rutas desconocidas, autorreferencias de prerrequisito, prerrequisitos hacia scopes no permitidos y referencias de fundamentos a implementaciones fuera de secciones de aplicación/relacionados. Informa la ruta y línea.
- `npm run test:taxonomy`: pruebas positivas y negativas de las reglas, incluido rechazo de Java como requisito de un fundamento y JDBC como requisito de JavaFX general.
- `npm run build` ejecuta ambos controles y la validación de contenido antes de Astro. La matriz puede evolucionar mediante PR con pruebas y justificación; no agregar una excepción para ocultar una mala clasificación.

El control comprende enlaces Markdown internos raíz-relativos y secciones convencionales; no analiza por completo la semántica de la prosa, los ejemplos, los enlaces externos ni los metadatos de competencias. La revisión de acoplamiento y cobertura semántica sigue siendo obligatoria: un PASS estructural no certifica independencia conceptual absoluta.

## Estándar editorial v1.3 — profundidad conceptual y representación (ADU-228)

El objetivo de una ficha no es mencionar un término sino permitir comprenderlo, consultarlo y aplicarlo sin depender de otra asignatura o caso de negocio. El estándar v1.2 de independencia sigue vigente. Una ficha breve puede ser completa; no se fija una cantidad mínima de palabras ni una cuota de diagramas.

### Criterios de aceptación según propósito

| Dimensión | Evidencia esperada |
| --- | --- |
| Definición | Qué es, qué representa, de qué se distingue y bajo qué alcance resulta válida. Enlazar o definir vocabulario previo. |
| Modelo mental | Explicar componentes, relaciones, estados, flujo o mecanismos cuando sean necesarios; no limitarse a una tabla de nombres. |
| Ejemplo | Caso mínimo autónomo o fragmento contextualizado; pasos y valores concretos, interpretación del resultado. |
| Contraste | Caso incorrecto, límite, alternativa o error frecuente que permita distinguir el concepto. |
| Comprobación | Pregunta, traza, prueba local o resultado observable; indicar evidencia real frente a salida esperada. |
| Referencia técnica | API/especificación primaria versionada cuando corresponda; entradas, retorno, excepciones y restricciones. |

### Diagramas como parte de la explicación

Usar un diagrama cuando relaciones, estado, identidad, recorrido, tiempo, responsabilidades o arquitectura se comprendan mejor visualmente. Cada diagrama tiene un propósito, texto alternativo descriptivo, explicación textual cercana y elementos legibles también en móvil. Preferir SVG estático original versionado en public/diagrams, con viewBox, título y descripción, sin dependencias remotas, scripts ni datos sensibles. No usar capturas decorativas. Una traza tabular o un fragmento de código puede ser más adecuado que un diagrama. Los diagramas no reemplazan una definición ni certifican la ejecución.

La validación de contenido comprueba existencia de assets SVG locales y alt no vacío. La taxonomía distingue una ilustración de un enlace a conocimiento; las reglas de dependencias no se relajan. El gate automatizado no valida precisión semántica, legibilidad o calidad pedagógica. Revisar SVG y páginas en escritorio y móvil antes de aceptación.

### Auditoría de profundidad

Revisar páginas existentes, no solo fichas nuevas. Registrar qué fichas se revisaron, qué evidencia se añadió, qué ejemplos se ejecutaron y qué brechas quedan. No agregar secciones repetitivas ni introducir dependencias de negocio por aumentar longitud. Las referencias externas requieren comprobación pertinente; preservar URLs, fuentes y comportamiento de las fichas.
