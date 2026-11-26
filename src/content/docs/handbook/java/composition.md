---
title: "Colaboración y composición en Java"
description: "Referencias a otros objetos, delegación, propiedad y responsabilidades."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

Una clase puede almacenar una [referencia](/handbook/java/references/) a otra instancia y delegarle trabajo mediante métodos. La [asociación y composición](/handbook/foundations/object-relationships/) describen relaciones de diseño; Java no impone su ciclo de vida por declarar un campo. La composición no es sinónimo de cualquier relación «tiene un».

## Ejemplo ejecutable

Archivo `ComposicionDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 ComposicionDemo.java` y `java ComposicionDemo` con JDK 21. No requiere dependencias externas.

```java
public class ComposicionDemo {

    private static class Punto {
        private final int coordenadaX;
        private final int coordenadaY;

        Punto(int coordenadaX, int coordenadaY) {
            this.coordenadaX = coordenadaX;
            this.coordenadaY = coordenadaY;
        }

        String descripcion() {
            return coordenadaX + "," + coordenadaY;
        }
    }

    private static class Circulo {
        private final Punto centro;

        Circulo(Punto centro) {
            if (centro == null) {
                throw new IllegalArgumentException("Centro obligatorio");
            }
            this.centro = centro;
        }

        String descripcion() {
            return "Centro: " + centro.descripcion();
        }
    }

    public static void main(String[] args) {
        Punto origen = new Punto(0, 0);
        Circulo circulo = new Circulo(origen);
        System.out.println(circulo.descripcion());
    }
}
```

Salida esperada:

```text
Centro: 0,0
```

El ejemplo agrupa clases anidadas para ejecutar en un archivo; normalmente se separan en archivos si son responsabilidades reutilizables. Punto es inmutable en este ejemplo. El círculo recibe una referencia al punto: no lo copia y no prueba propiedad exclusiva. Si necesitas esa garantía, diseña construcción/copia y controla mutabilidad.

## Errores habituales

- Heredar solo para reutilizar un campo: comprueba si existe sustitución real.
- Duplicar datos de un objeto colaborador: puede generar dos estados contradictorios.
- Aceptar una referencia ausente cuando es obligatoria: valida al construir.

## Qué relación establece el campo

![Circulo guarda una referencia a Punto y delega descripcion; no copia coordenadas. Punto también podría ser referenciado por otro consumidor.](/diagrams/java-composition-reference.svg)

El campo centro almacena una referencia al Punto recibido; Circulo delega la representación de coordenadas a descripcion. Este ejemplo ilustra colaboración, no necesariamente composición fuerte en UML. La propiedad exclusiva y el ciclo de vida dependen del contrato del modelo. Un objeto colaborador inmutable puede compartirse sin permitir cambios de estado; uno mutable requiere evaluar alias y garantías.

## Propiedad frente a acceso

```java
// Fragmento dentro de una clase que administra datos propios.
private final java.util.List<String> datos = new java.util.ArrayList<>();

public java.util.List<String> consultarDatos() {
    return java.util.List.copyOf(datos);
}
```

Retornar una copia no modificable evita que el consumidor agregue elementos directamente a la lista interna. Como los String son inmutables, los elementos no exponen mutación; si fueran objetos mutables, una copia superficial no bastaría. No confundas final con propiedad exclusiva. Consulta [colecciones y mutabilidad](/handbook/java/collections/).

## Cuándo preferir delegación

Si un objeto necesita usar otra capacidad sin ser un subtipo de ella, delegar conserva contratos separados. Si cambia el colaborador, el consumidor debería depender de las operaciones que necesita, no de detalles internos. No agregues una capa intermedia sin un motivo de responsabilidad o sustitución.

## Documentación oficial

- [JLS 21: campos y clases](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-04/02-composicion-introductoria.md); los ejemplos de esta ficha son independientes.
