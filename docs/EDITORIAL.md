# Política editorial inicial

Las fichas se organizan por tecnología, no asignatura. Las rutas enlazan fichas sin duplicarlas. Cada ficha declara título, descripción, propósito, contexto, sintaxis, ejemplos con llaves e indentación, errores y fuentes cuando corresponda. Distinguir fragmento de programa completo. No copiar material institucional reservado, credenciales ni datos personales.

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
- [ ] No reproduce material de terceros ni contenido reservado.
- [ ] Revisión técnica, pedagógica, visual móvil y build antes de declarar publicación.

Primer piloto: TableView, con referencia, explicación de observabilidad y guía de filtrado/ordenamiento separadas. Mantener sus URLs existentes. No ampliar masivamente el catálogo hasta revisar el piloto.

## Relaciones de conocimiento — ADU-221

Usar enlaces Markdown dentro de la prosa en la primera aparición significativa de un concepto, preferir fichas canónicas independientes y añadir conocimientos previos recomendados cuando permitan identificar brechas. No enlazar cada repetición ni crear páginas vacías. Distinguir prerequisito, relación complementaria y aplicación; no imponer bloqueos ni dependencias circulares obligatorias. No introducir un nuevo schema de grafo o IDs hasta reconciliar ADR-011, ADU-86 y ADU-94. Mantener rutas, URLs y contenidos públicos; enlaces relativos al origen. La ficha de fundamentos es síntesis contextual, no una segunda autoridad de cada concepto.

### Límite entre documentación y aprendizaje evaluable

Los prerrequisitos editoriales son recomendaciones de lectura, no dependencias obligatorias entre competencias ni evidencia de dominio. Una URL canónica identifica una ficha documental; no representa automáticamente una competencia, una cápsula o una versión de un resultado de aprendizaje. La eventual asociación debe ser explícita, versionada y compatible con los contratos de sus respectivos propietarios. La reconciliación interna se registra en ADU-221; no se introducen campos nuevos en contratos externos ni se publica contenido interno de otros repositorios.

### Controles reproducibles

`npm run validate:content` comprueba frontmatter mínimo, rutas internas y delimitadores de bloques de código. `npm run test:java-examples` extrae nueve ejemplos autónomos de las fichas y compara su salida con expectativas usando JDK 21. El build ejecuta la validación de contenido; la suite Java es separada para no exigir Java al hosting estático. La comprobación no sustituye pruebas de JavaFX/MariaDB, validación de enlaces externos o revisión visual.

## Regla de cobertura semántica — ADU-221

Antes de aceptar una ficha, leerla desde la perspectiva de una persona que conoce menos que el autor. Preguntar por cada término técnico significativo: ¿qué es?, ¿qué conocimiento presupone?, ¿necesita una explicación aquí o una ficha reutilizable?

- Definición local: explica en una frase el término necesario para entender la oración; usa un ejemplo o contraste si evita ambigüedad (estado frente a comportamiento, valor frente a referencia, validación frente a conversión).
- Ficha canónica: crea una página útil y autónoma cuando el concepto se reutiliza o necesita profundidad; enlázala desde el texto y, si aporta orientación, desde los conocimientos previos recomendados. No crear páginas vacías ni duplicar una explicación extensa.
- Nivel: el lector puede seguir leyendo con el recordatorio o profundizar sin obligación de recorrer todos los enlaces. No enlazar palabras ordinarias ni cada repetición.
- Cierre: auditar introducciones, tablas de API, explicaciones de código, errores y rutas. Identificar acrónimos, términos en inglés y supuestos tácitos. Registrar brechas detectadas y soluciones; un build verde no demuestra comprensión pedagógica.
- Los enlaces de consulta pueden tener ciclos semánticos; no representan dependencias obligatorias de competencias. Mantener separación con Learning Roadmaps y Competency Engine.
