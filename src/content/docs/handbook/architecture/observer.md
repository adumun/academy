---
title: "Patrón Observer"
description: "Suscripción, notificación y límites de la observabilidad."
---

**Tipo:** explicación. **Nivel:** complementario. **Versión:** Java 21.

## Conocimientos previos recomendados

- [Objetos, estado y comportamiento](/handbook/foundations/objects/)
- [Operaciones y contratos](/handbook/foundations/operations/)

## Problema

Cuando cambia el [estado de un objeto](/handbook/java/classes-and-objects/), varios consumidores pueden necesitar reaccionar. Un patrón es una solución reutilizable a un problema de diseño. Observer permite suscribir observadores a un sujeto y notificarlos sin que el sujeto conozca cada consumidor concreto. El sujeto mantiene las suscripciones; suscribir es registrar un consumidor de notificaciones, notificar es comunicar un cambio y desuscribir es dejar de recibirlo. Un consumidor puede ser un objeto que cumple una [interfaz funcional](/handbook/java/lambdas/).

## Aplicación: ejemplo completo en Java

Para implementar este patrón en Java, consulta [interfaces](/handbook/java/interfaces/) y [lambdas](/handbook/java/lambdas/). Estos conocimientos son específicos del ejemplo, no del patrón.

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

Salida: Cambio. Es un ejemplo pedagógico síncrono (cada notificación se ejecuta antes de continuar con la siguiente operación), no una biblioteca segura para concurrencia (varias tareas pueden progresar de forma intercalada o simultánea) ni eventos distribuidos (comunicación entre procesos o servicios). List.copyOf permite recorrer una instantánea de suscripciones, pero no resuelve acceso concurrente.

## Responsabilidades y límites

El sujeto posee la lista de suscripciones; el consumidor decide cómo reaccionar. Debes definir ciclo de vida, orden, manejo de errores y condiciones de concurrencia. Notificar no equivale a persistir ni garantiza entrega durable. Una excepción en un observador podría interrumpir notificaciones posteriores en esta implementación.

## JavaFX y otros contextos

[ObservableList](/handbook/java/javafx/observable-list/) y las propiedades de JavaFX implementan mecanismos concretos de observación. El patrón es una idea de diseño, no una exigencia de utilizar java.util.Observable (API obsoleta). No confundas observación local con comunicación mediante un broker o eventos de dominio distribuidos.

## Comprobación

Suscribe dos consumidores, publica un valor, retira uno y publica otro. Verifica quién recibe cada notificación. Comprueba que retirar la suscripción evita efectos posteriores.

## Modelo del patrón y ciclo de vida

![El sujeto conoce contratos de observadores A y B. Al cambiar notifica a ambos; al retirar B, solo A sigue registrado.](/diagrams/observer-subscription.svg)

El patrón separa la fuente de un cambio de los consumidores que reaccionan. Una suscripción representa una relación temporal; un observador que ya no necesita recibir cambios debe retirarse cuando corresponda. La notificación puede transportar el nuevo valor, una descripción del evento o una señal que obliga a consultar la fuente. El contrato debe especificar cuál de ellas usa.

| Acción | Observador A | Observador B |
|---|---|---|
| Suscribir ambos | Registrado | Registrado |
| Publicar cambio 1 | Recibe | Recibe |
| Retirar B | Registrado | Retirado |
| Publicar cambio 2 | Recibe | No recibe |

## Comparación con consulta periódica

Consultar continuamente (polling) pregunta si hubo un cambio a intervalos. Observer notifica a consumidores registrados según el contrato, evitando consultas innecesarias, pero introduce gestión de suscripciones, orden y errores. No garantiza concurrencia, aislamiento entre observadores ni persistencia. Si un observador modifica otra vez el sujeto durante la notificación, puede producirse reentrancia: define si es admisible y cómo se trata. En el ejemplo Java, la lista copiada evita modificar el recorrido actual, no todas las posibles interacciones.

## Conceptos relacionados

- [Eventos JavaFX](/handbook/java/javafx/events/)
- [Observabilidad en TableView](/handbook/java/javafx/table-view-observability/)
- [MVC y DAO](/handbook/architecture/)

## Documentación oficial

- [ObservableValue, OpenJFX 21](https://openjfx.io/javadoc/21/javafx.base/javafx/beans/value/ObservableValue.html)
- [Consumer, Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/Consumer.html)
