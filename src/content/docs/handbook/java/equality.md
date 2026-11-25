---
title: "Igualdad, identidad, equals y hashCode"
description: "Contrato de Object, valores estables y colecciones hash."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

La [identidad](/handbook/foundations/objects/) distingue objetos. Para referencias, == comprueba si ambos valores apuntan al mismo objeto (o son ambos null); equals comprueba igualdad lógica según el contrato de la clase. Object.equals utiliza identidad por defecto. hashCode entrega un entero utilizado por estructuras hash; dos objetos iguales deben tener el mismo hash, pero compartir hash no demuestra igualdad.

## Ejemplo ejecutable

Archivo `IgualdadDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 IgualdadDemo.java` y `java IgualdadDemo` con JDK 21. No requiere dependencias externas.

```java
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class IgualdadDemo {

    static final class Coordenada {
        private final int coordenadaX;
        private final int coordenadaY;

        Coordenada(int coordenadaX, int coordenadaY) {
            this.coordenadaX = coordenadaX;
            this.coordenadaY = coordenadaY;
        }

        @Override
        public boolean equals(Object otro) {
            if (this == otro) {
                return true;
            }
            if (!(otro instanceof Coordenada coordenada)) {
                return false;
            }
            return coordenadaX == coordenada.coordenadaX
                    && coordenadaY == coordenada.coordenadaY;
        }

        @Override
        public int hashCode() {
            return Objects.hash(coordenadaX, coordenadaY);
        }
    }

    public static void main(String[] args) {
        Coordenada primero = new Coordenada(2, 3);
        Coordenada segundo = new Coordenada(2, 3);
        Set<Coordenada> puntos = new HashSet<>();
        puntos.add(primero);
        puntos.add(segundo);
        System.out.println(primero == segundo);
        System.out.println(primero.equals(segundo));
        System.out.println(puntos.size());
    }
}
```

Salida esperada:

```text
false
true
1
```

## Reglas y pruebas

equals debe ser reflexivo, simétrico, transitivo y consistente bajo el contrato; un objeto no nulo no es igual a null. Sobrescribe ambos métodos juntos y utiliza campos de identidad estables. No uses hashCode como ID único. Si un campo usado en hashing cambia dentro de HashSet o como clave HashMap, puede dejar de encontrarse.

## Documentación oficial

- [Object.equals y hashCode](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-08/01-set.md); los ejemplos de esta ficha son independientes.
