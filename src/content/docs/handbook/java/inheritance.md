---
title: "Herencia y polimorfismo dinámico en Java"
description: "extends, super, Override, despacho de métodos y sustitución."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

La [generalización y especialización](/handbook/foundations/object-relationships/) permite expresar una relación de subtipos. `extends` declara la superclase; Java permite una sola superclase directa. Los constructores no se heredan: `super(...)` invoca el constructor de la clase padre. `@Override` verifica que la declaración realmente sobrescribe un método compatible.

La sobrecarga se resuelve según tipos y firmas durante compilación; la sobrescritura de un método de instancia selecciona la implementación según el objeto real en ejecución. Los métodos estáticos se ocultan, no se despachan del mismo modo.

## Ejemplo ejecutable

Archivo `HerenciaDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 HerenciaDemo.java` y `java HerenciaDemo` con JDK 21. No requiere dependencias externas.

```java
public class HerenciaDemo {

    static class Indicador {
        String mostrar() {
            return "Normal";
        }
    }

    static class IndicadorCritico extends Indicador {
        @Override
        String mostrar() {
            return "Critico";
        }
    }

    public static void main(String[] args) {
        Indicador primero = new Indicador();
        Indicador segundo = new IndicadorCritico();
        System.out.println(primero.mostrar());
        System.out.println(segundo.mostrar());
    }
}
```

Salida esperada:

```text
Normal
Critico
```

## Límites

Un miembro privado no es accesible directamente desde el subtipo; protege el estado mediante el contrato. No utilices herencia solo por compartir campos. Una [interfaz](/handbook/java/interfaces/) expresa capacidades y [composición](/handbook/java/composition/) permite delegar sin crear una familia artificial.

## Comprobación

Quita temporalmente la implementación sobrescrita: ambos objetos mostrarán Normal. Cambia la firma dejando @Override: la compilación debe rechazar la declaración. Restaura después el ejemplo.

## Documentación oficial

- [JLS 21: clases y sobrescritura](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.4.8)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/labs/semana-05/02-herencia.md); los ejemplos de esta ficha son independientes.
