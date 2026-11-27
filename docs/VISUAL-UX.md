# Academy — identidad visual y UX v0.1 (propuesta)
Jira: [ADU-233](https://adumun.atlassian.net/browse/ADU-233) · [GitHub #9](https://github.com/adumun/academy/issues/9)
Fecha: 2026-11-25. Baseline Academy: `3ad8604b54835ac29d4e269e00f80f914f78faf9`. Estado: propuesta para contrastar con auditoría visual; no estándar aprobado.

## Evidencia y límites
- master coincide con el merge de PR #7; PR #8 fusionado inmediatamente antes (f820e458). No PR abiertos al consultar.
- Se inspeccionaron astro.config.mjs, package.json, src/styles/academy.css, index.md, handbook/index.md, projects/index.md, PetCare, AGENTS.md, docs/EDITORIAL.md y docs/DEPLOYMENT.md.
- Academic Labs: se inspeccionaron README.md, index.html y assets/styles.css en master. Comparte enfoque público estático y separación portada/explorador. Sus colores reales incluyen #152839, #091927, #0d2941, #56edc0, #4d8ff9, #f6fafc y blanco. Usa jerarquía amplia, secciones, tarjetas de catálogo, container y breakpoints; su navegación se oculta a <=700px según CSS, no copiar ese comportamiento sin alternativa.
- Academy actualmente utiliza dos ternas de acento Starlight (teal) y una regla SVG de 720px. La portada es Markdown estándar, con una tabla de elección y listado textual; Handbook contiene navegación y categorías semánticas; Projects utiliza tabla de catálogo y guías Markdown con incrementos/checkpoints. La sidebar se configura globalmente y pagination=false es intencional.
- GitHub no devuelve estados de commit para master en ambos repositorios; esto no prueba ni éxito ni fallo de Cloudflare. El acceso web no pudo recuperar los dominios personalizados ni pages.dev; la sesión no ofrece control/captura de navegador. No se certifica disponibilidad, render, contraste efectivo, accesibilidad, build ni deployment. Capturar producción y verificar commit Cloudflare antes de editar la interfaz.
- PR #8 registra 80 páginas, 45 SVG y 34 casos Java; la suite completa desde clon y dependencias/gráficos reales tienen límites documentados. No afirmar que todos los casos fueron ejecutados en esta sesión. No intervenir ADU-223/224/225/232.

## Referencias y criterio
- [MDN](https://developer.mozilla.org/en-US/): descubrimiento por tecnología, referencia/guía, búsqueda y enlaces.
- [Dev.java](https://dev.java/learn/): agrupación por capacidades y acceso a especificaciones primarias.
- [JavaScript.info](https://javascript.info/): mapa de contenidos, progresión clara y lectura centrada.
- [Diátaxis](https://diataxis.fr/): tipos documentales según necesidad, ya adoptados por EDITORIAL.md.
- [Starlight CSS](https://starlight.astro.build/guides/css-and-tailwind/), [overrides](https://starlight.astro.build/guides/overriding-components/), [configuración](https://starlight.astro.build/reference/configuration/): personalización por CSS/variables, templates, componentes específicos y búsqueda Pagefind. Verificar compatibilidad con versión instalada antes de usar APIs. No reemplazar Starlight ni incorporar framework/dependencias por ahora.
- [WCAG 2.2](https://www.w3.org/TR/WCAG22/): objetivo AA, sin afirmar conformidad por paleta sola.

## Identidad: biblioteca tecnológica viva
Propuesta de valor: «Comprende la tecnología. Conecta el conocimiento. Construye soluciones.» Academy es editorial y serena: amplias superficies blancas, tinta azul, petróleo como acción/estructura, turquesa como relación/descubrimiento. Academic Labs conserva energía de práctica y mint. Reutilizar principios de jerarquía, no CSS completo ni terminal de hero. Sin logotipo nuevo obligatorio.

| Token semántico | Claro | Oscuro |
|---|---|---|
| canvas | #F4F8FA | #091927 |
| surface | #FFFFFF | #102534 |
| text | #142B3C | #EAF3F7 |
| muted | #526575 | #B4C6D2 |
| action | #0D5260 | #70DECF |
| on-action | #FFFFFF | #102534 |
| link | #086F66 | #70DECF |
| border (decorativo) | #DCE6EE | #314959 |
| focus (candidato) | #086F66 | #70DECF |
| info text | #1763A0 | #9BCBFF |
| warning text | #9D5110 | #F4C485 |
| danger text | #A72C3A | #FFB2B9 |
| success text | #12724F | #8CE5B7 |

Pares calculados con luminancia relativa WCAG: texto claro sobre blanco 14.58:1, muted 6.04:1, link 6.04:1, blanco sobre petróleo 8.78:1; texto oscuro sobre surface 13.97:1, muted 8.94:1, link 9.76:1, on-action 9.76:1. Estados claros sobre blanco: info 6.31, warning 5.80, danger 6.85, success 5.92. Son cálculos de pares de tokens, no medición del sitio. Validar estados oscuros, enlaces, foco, bordes funcionales, diagramas, código y hover en render real. No usar color como único indicador.

Tipografía inicial system-ui y monospace nativa (sin descarga de fuentes). H1 landing clamp(2.25rem, 5vw, 4rem), títulos artículo 2–2.75rem, H2 1.5–2rem, cuerpo 1–1.125rem, line-height 1.65–1.75, medida editorial 65–75ch. Escala 4/8/12/16/24/32/48/64/96px; radios 6/10/16px; sombras bajas solo para superficies elevadas. Iconos lineales nativos cuando existan, con texto y nombres accesibles. Controles preferentemente 44px; bordes funcionales y foco 3:1 donde corresponda. Motion reducido respetado.

## Tres experiencias, una navegación
- Home: cabecera global con Handbook, Projects, Rutas, Academic Labs (externo), buscador y tema; hero editorial sin terminal copiada; dos acciones a Handbook/Projects; áreas reales (Fundamentos, Java, SQL, Arquitectura, Herramientas y Diagnóstico); rutas existentes DSY1102 y Software Developer identificadas como consulta, no progreso; tres proyectos existentes; acceso a Labs y footer. Contenidos recientes únicamente si hay metadatos verificables; no popularidad ni estadísticas inventadas. Búsqueda nativa, no dos índices.
- Handbook: preservar rutas y Markdown, sidebar contextual/categorías, TOC, búsqueda, lectura con ancho controlado. Relaciones previas recomendadas, relacionadas y aplicaciones diferenciadas por títulos/enlaces. No convertir cada párrafo en card. Tablas con scroll local si necesario, código copiable nativo, SVG con alt y superficie de contraste adecuada en ambos temas; evaluar ampliación por enlace al asset original accesible. No agregar una capa de datos de competencias.
- Projects: catálogo con tarjetas descriptivas, ficha con título, propósito, stack y alcance reales, arquitectura si existe, incrementos y checkpoints visibles, criterios/evidencia/referencias y límites. No inventar diagramas ni pruebas realizadas. Mantener Markdown y los enlaces fuente pinneados. No registro de progreso ni evaluación.

## Implementación propuesta
1. Auditoría real: capturas de Academy home, Handbook index/ficha representativa (SVG/código/tabla), Projects index/PetCare y Labs home/explore; desktop 1440 y móvil 390/320, temas claro/oscuro donde soportados, búsqueda/teclado/navegación. Confirmar Pages deployment production commit, estado y logs; no cambiar DNS.
2. Tokens en src/styles (un origen semántico), adaptar variables Starlight y reglas de layout; preservar modo automático. No duplicar estilos.
3. Landing mediante plantilla splash Starlight y componentes nativos CardGrid/LinkCard cuando sirvan; override mínimo solo si necesario para header/footer. Verificar rutas, Pagefind y selección de tema; evitar migrar Markdown masivamente.
4. Handbook piloto con ficha que reúna código/diagrama/tabla y una explicación breve. Usar Aside nativo cuando corresponda sin alterar semántica editorial. CSS primero; nuevos componentes solo para repetición comprobada.
5. Projects catálogo y piloto PetCare; tratar incrementos como navegación documental, no progreso. Preservar enlaces y fuentes; extender a dos proyectos tras piloto.
6. Descubrimiento y navegación global, móvil y a11y. No usar recomendaciones editoriales como restricciones.
7. QA y documentación: comparar listado de URLs, anchors, diagramas y ejemplos con baseline; npm run test:taxonomy, validate:content, validate:taxonomy, build; test:java-examples con JDK21 cuando esté disponible. Registrar lockfile/dependencias como deuda separada; no relajar gates. Comprobar teclado, foco, skip link, búsqueda, tema, zoom/reflow 320, tablas/código sin overflow de página, SVG y enlaces externos, contraste WCAG AA. Comparar capturas antes/después y revisar 320/390/768/1440.
8. PR integrado, preview Cloudflare ligado al head, aceptación visual explícita antes de merge a master, producción ligada al merge y smoke posterior.

## Gate actual
Auditoría estática y propuesta preliminar realizadas; auditoría visual y despliegue no verificados. No comenzar cambios de interfaz hasta obtener esas evidencias. El PR documental puede permanecer en draft. No se declara ningún gate de build/Java ejecutado. ADU-226 permanece intacto.
