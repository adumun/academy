---
title: "Patrón Observer"
description: "Suscripción, notificación y límites de la observabilidad."
---

**Tipo:** explicación. **Nivel:** complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Interfaces](/handbook/java/interfaces/)
- [Lambdas](/handbook/java/lambdas/)

## Problema

Cuando un objeto cambia, varios consumidores pueden necesitar reaccionar. El patrón Observer permite suscribir observadores a un sujeto y notificarlos sin que el sujeto conozca cada consumidor concreto.

## Ejemplo completo en Java

```java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ObservableDemo {

    private final List<Consumer<String>> observadores =
            new ArrayList<>();

    public void suscribir(Consumer<String> observador) {
        observadores.add(observador);
    }

    public void retirar(Consumer<String> observador) {
        observadores.remove(observador);
    }

    public void publicar(String valor) {
        for (Consumer<String> observador :
                List.copyOf(observadores)) {
            observador.accept(valor);
        }
    }

    public static void main(String[] args) {
        ObservableDemo sujeto = new ObservableDemo();
        Consumer<String> observador = System.out::println;

        sujeto.suscribir(observador);
        sujeto.publicar("Cambio");
        sujeto.retirar(observador);
    }
}
```

Salida: Cambio. Es un ejemplo pedagógico síncrono, no una biblioteca segura para concurrencia ni eventos distribuidos. List.copyOf permite recorrer una instantánea de suscripciones, pero no resuelve acceso concurrente.

## Responsabilidades y límites

El sujeto posee la lista de suscripciones; el consumidor decide cómo reaccionar. Debes definir ciclo de vida, orden, manejo de errores y condiciones de concurrencia. Notificar no equivale a persistir ni garantiza entrega durable. Una excepción en un observador podría interrumpir notificaciones posteriores en esta implementación.

## JavaFX y otros contextos

[ObservableList](/handbook/java/javafx/observable-list/) y las propiedades de JavaFX implementan mecanismos concretos de observación. El patrón es una idea de diseño, no una exigencia de utilizar java.util.Observable (API obsoleta). No confundas observación local con comunicación mediante un broker o eventos de dominio distribuidos.

## Comprobación

Suscribe dos consumidores, publica un valor, retira uno y publica otro. Verifica quién recibe cada notificación. Comprueba que retirar la suscripción evita efectos posteriores.

## Conceptos relacionados

- [Eventos JavaFX](/handbook/java/javafx/events/)
- [Observabilidad en TableView](/handbook/java/javafx/table-view-observability/)
- [MVC y DAO](/handbook/architecture/)

## Documentación oficial

- [ObservableValue, OpenJFX 21](https://openjfx.io/javadoc/21/javafx.base/javafx/beans/value/ObservableValue.html)
- [Consumer, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Consumer.html)
