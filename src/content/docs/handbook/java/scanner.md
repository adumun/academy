---
title: "Scanner y entrada desde consola"
description: "Entrada estándar, tokens, líneas, conversiones y validación."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario. **Versión:** Java 21.

## Modelo mental

System.in es la entrada estándar del proceso; normalmente proviene del teclado, pero puede redirigirse. Scanner interpreta texto mediante tokens (fragmentos delimitados) o líneas. nextInt y nextDouble consumen tokens numéricos; nextLine consume lo que queda de la línea, incluido su separador. Al mezclarlos es posible obtener una cadena vacía inesperada.

## Ejemplo ejecutable

Archivo `ScannerDemo.java`. Guarda el siguiente código y ejecuta `javac --release 21 ScannerDemo.java` y `java ScannerDemo` con JDK 21. No requiere dependencias externas.

```java
import java.util.Scanner;

public class ScannerDemo {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner("21\nSol\n")) {
            int cantidad = scanner.nextInt();
            scanner.nextLine();
            String nombre = scanner.nextLine();
            System.out.println(cantidad);
            System.out.println(nombre);
        }
    }
}
```

Salida esperada:

```text
21
Sol
```

El ejemplo usa una cadena de prueba para que pueda ejecutarse sin teclado. Para interacción real usa `new Scanner(System.in)` y solicita datos. No crees varios Scanner sobre la misma entrada ni cierres System.in si el resto del programa todavía la necesita.

## Validar antes de operar

Puedes leer una línea completa y convertir con Integer.parseInt, capturando NumberFormatException. Si usas hasNextInt, consume la entrada inválida antes de reintentar y trata fin de entrada. Distingue formato inválido de una regla de negocio (por ejemplo, rango permitido). El modelo debe proteger sus reglas aunque cambie la interfaz.

## Documentación oficial

- [Scanner Java 21](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Scanner.html)

## Procedencia didáctica

Adaptación original de temas trabajados en [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/semanas/semana-04/03-scanner-entrada-datos.md); los ejemplos de esta ficha son independientes.
