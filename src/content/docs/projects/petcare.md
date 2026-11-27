---
title: "PetCare"
description: "Proyecto longitudinal de POO, colecciones y adaptación gráfica con JavaFX."
---

**Tipo:** proyecto longitudinal. **Entorno:** Java 21; Maven y JavaFX solo en el último incremento. **Alcance:** incrementos 02–09 documentados; no se certifica ejecución nueva ni una solución posterior a la UI inicial.

## Problema y resultado

Construye un sistema de registro y consulta de mascotas que evolucione desde una aplicación sencilla de consola hacia objetos con reglas, una colección administrada y una primera interfaz gráfica. Mantén tu implementación: no reinicies el proyecto en cada etapa. No utilices PetCare como requisito de otros laboratorios autónomos.

## Conocimientos previos recomendados

- [Programas y algoritmos](/handbook/foundations/programs/)
- [Variables y tipos Java](/handbook/java/variables/)
- [Métodos](/handbook/java/methods/)

## Diseño progresivo

El modelo protege las reglas del dominio; el servicio coordina operaciones sobre varios objetos; la CLI lee opciones y presenta resultados. JavaFX añade otro adaptador sin obligar a reescribir el core. No todas las estructuras se necesitan simultáneamente: List, Set y Map se eligen por el problema.

## Incremento P02 — Iniciar un programa ejecutable

**Trabajo:** Crea el proyecto, modela una operación útil mediante métodos y datos de una mascota. Introduce clase y encapsulamiento solo si ya los comprendes.

**Checkpoint:** Ejecuta el programa y demuestra al menos una entrada y resultado; explica parámetros y retorno.

Consulta [Métodos](/handbook/java/methods/). [Guía detallada](/projects/petcare/p02/) · [Modelo y checkpoint](/projects/petcare/p02/#modelo-y-checkpoint) · [Referencia (consultar tras intentar)](/projects/petcare/p02/#referencia)

## Incremento P03 — Trasladar comportamiento al objeto

**Trabajo:** Construye Mascota, mueve una operación desde main y protege una regla de estado. Crea dos instancias independientes.

**Checkpoint:** Muestra dos objetos con datos distintos, una operación válida y una inválida sin acceso directo a atributos privados.

Consulta [Clases y objetos](/handbook/java/classes-and-objects/). [Guía detallada](/projects/petcare/p03/) · [Modelo y checkpoint](/projects/petcare/p03/#modelo-y-checkpoint) · [Referencia (consultar tras intentar)](/projects/petcare/p03/#referencia)

## Incremento P04 — Construcción válida y colaboración

**Trabajo:** Define qué información es obligatoria, consolida constructores y this; incorpora Tutor si responde a una necesidad, no por obligación.

**Checkpoint:** Al menos tres instancias coherentes; prueba una regla de estado y explica la relación entre objetos si existe.

Consulta [Estado e invariantes](/handbook/foundations/objects/). [Guía detallada](/projects/petcare/p04/) · [Modelo y checkpoint](/projects/petcare/p04/#modelo-y-checkpoint) · [Referencia (consultar tras intentar)](/projects/petcare/p04/#referencia)

## Incremento P05 — Especializar comportamiento

**Trabajo:** Identifica lo común de Mascota, implementa dos subtipos con extends, super y sobrescritura; invoca mediante una referencia base.

**Checkpoint:** El comportamiento especializado cambia según el objeto real sin un if/switch por tipo para decidir esa operación.

Consulta [Polimorfismo](/handbook/java/interfaces/). [Guía detallada](/projects/petcare/p05/) · [Modelo y checkpoint](/projects/petcare/p05/#modelo-y-checkpoint) · [Referencia (consultar tras intentar)](/projects/petcare/p05/#referencia)

## Incremento P06 — Administrar múltiples mascotas

**Trabajo:** Prueba primero un array y su límite. Evoluciona a List/ArrayList; registra, busca, elimina o modifica; separa operaciones de main y comunica fallos mediante excepciones.

**Checkpoint:** Demuestra lista variable, búsqueda exitosa y fallo controlado. Explica quién detecta, lanza y captura.

Consulta [Colecciones](/handbook/java/collections/). [Guía detallada](/projects/petcare/p06/) · [Modelo y checkpoint](/projects/petcare/p06/#modelo-y-checkpoint) · [Referencia (consultar tras intentar)](/projects/petcare/p06/#referencia)

## Incremento P07 — Abstraer e integrar

**Trabajo:** Decide si Mascota debe ser abstracta; modela una capacidad con interfaz si aporta valor. Mantén jerarquía, lista y errores funcionando.

**Checkpoint:** Ejecuta un flujo de consola completo, muestra polimorfismo y un caso inválido. Justifica las abstracciones elegidas.

Consulta [Interfaces](/handbook/java/interfaces/). [Guía detallada](/projects/petcare/p07/) · [Modelo y checkpoint](/projects/petcare/p07/#modelo-y-checkpoint) · [Referencia (consultar tras intentar)](/projects/petcare/p07/#referencia)

## Incremento P08 — Elegir estructuras según necesidad

**Trabajo:** Incorpora Set para unicidad o Map para búsqueda por clave, solo cuando el requerimiento lo justifique; revisa equals/hashCode si utilizas objetos como claves o elementos de un conjunto.

**Checkpoint:** Caso válido y duplicado o clave inexistente. Explica qué alternativa descartaste; evita índices redundantes incoherentes.

Consulta [List, Set y Map](/handbook/java/collections/). [Guía detallada](/projects/petcare/p08/) · [Modelo y checkpoint](/projects/petcare/p08/#modelo-y-checkpoint) · [Referencia (consultar tras intentar)](/projects/petcare/p08/#referencia)

## Incremento P09 — Construir adaptador gráfico

**Trabajo:** Retoma el mismo core y PetCareService. Configura Maven/JavaFX, crea una vista FXML y conecta una operación de registro o búsqueda mediante Controller.

**Checkpoint:** Ventana funcional, caso válido e inválido; core y CLI continúan funcionando. No exige TableView, navegación compleja ni persistencia.

Consulta [JavaFX](/handbook/java/javafx/). [Guía detallada](/projects/petcare/p09/) · [Modelo y checkpoint](/projects/petcare/p09/#modelo-y-checkpoint) · [Referencia (consultar tras intentar)](/projects/petcare/p09/#referencia)

## Cierre de la experiencia

- Conserva código ejecutable y commits progresivos en tu repositorio.
- Registra resultados de pruebas, errores y decisiones descartadas; prueba tanto una operación correcta como una inválida.
- Explica cómo cambiar de CLI a JavaFX sin mover reglas del dominio a controles visuales.
- El último checkpoint documentado es P09. No se ha establecido aquí una evolución JSON/JDBC posterior. Para practicar esa integración, elige [Cápsula del Tiempo FX](/projects/time-capsule-fx/).

## Procedencia

Adaptación editorial de material didáctico propio. Se conservan los ocho incrementos documentados P02–P09. El origen y el corte están registrados en el manifiesto editorial de Academy. No se requiere acceder al repositorio original. Las referencias son fragmentos orientativos; no se afirma que exista un proyecto completo probado para cada incremento.
