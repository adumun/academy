# ADÜMÜN Academy — lineamientos para contribuciones

- Respetar el Jira de origen, el alcance aprobado y la revisión por PR; no mezclar trabajos ni declarar verificaciones no ejecutadas.
- Leer [docs/EDITORIAL.md](docs/EDITORIAL.md) antes de crear o editar contenido. Es la autoridad editorial, incluida la cobertura semántica y la taxonomía Knowledge First / Consumer Agnostic.
- Definir conceptos generales en su alcance canónico. Los lenguajes, frameworks, herramientas, contextos de negocio y rutas deben consumirlos sin duplicar su autoridad. No exigir una implementación concreta para comprender un fundamento o disciplina.
- Distinguir prerrequisito recomendado, referencia contextual, aplicación/ejemplo y concepto relacionado. Conservar enlaces no lineales y URLs existentes. No introducir contratos de competencias ni dependencias obligatorias de aprendizaje.
- Ejecutar `npm run test:taxonomy`, `npm run validate:content`, `npm run validate:taxonomy` y `npm run build`. La suite `npm run test:java-examples` requiere JDK 21; distinguir pruebas ejecutadas de revisión estática.
- Revisar manualmente independencia semántica, cobertura de conceptos, corrección de ejemplos, fuentes y experiencia móvil. No relajar un gate para hacer pasar contenido acoplado; corregir el alcance, la relación o el contenido.

- Aplicar también v1.3: definición precisa, modelo mental, ejemplos contextualizados, contrastes y autocomprobación según propósito; incluir diagramas SVG locales accesibles cuando expliquen relaciones o comportamiento. El build no certifica profundidad ni revisión visual.

- Projects (/projects/) consume conocimiento del Handbook y contiene experiencias integradoras completas con incrementos, evidencia y referencias. No introducir reglas de negocio de un proyecto en una definición canónica.

- Autonomía editorial (ADU-235): repositorios docentes personales son fuentes de adaptación, no dependencias. Alojar los recursos necesarios bajo ADÜMÜN; registrar procedencia separada, conservar enlaces canónicos y validar independencia del recorrido. No declarar pruebas de runtime por build estático.

- ADU-236: editar primero las fuentes reales de los snippets migrados, sincronizar con `npm run sync:snippets`, ejecutar `npm run validate:snippets` y `npm run test:java-sources` con JDK 21. No confundir cobertura parcial con certificación global.
