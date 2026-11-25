---
title: "Serialización JSON en Java: Jackson y Gson"
description: "Dependencias, conversiones, tipos genéricos y validación."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Modelo mental

[JSON](/handbook/foundations/json/) es un formato independiente de Java. Serializar transforma objetos a una representación; deserializar reconstruye objetos. La biblioteca no decide por sí sola la validez del dominio ni si se guardan los datos. [Maven](/handbook/tools/maven/) permite declarar dependencias.

## Jackson (Java 21, Jackson 2.x)

En el bloque dependencies del POM añade jackson-databind. La versión 2.18.3 es la fijada en la guía docente de ejemplo, no una recomendación de versión más reciente ni una certificación de seguridad.

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.18.3</version>
</dependency>
```

Archivo DemoJackson.java en un proyecto Maven Java 21 no modular. El record expresa datos de ejemplo; no exige utilizar records para todas las entidades.

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

public class DemoJackson {
    public record Medicion(String etiqueta, int valor) {
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String texto = mapper.writeValueAsString(List.of(new Medicion("A", 21)));
        List<Medicion> recuperadas = mapper.readValue(
                texto, new TypeReference<List<Medicion>>() {});
        System.out.println(recuperadas.get(0).valor());
    }
}
```

Salida esperada: 21. TypeReference captura información del tipo genérico mediante clase anónima; no es una lambda. writeValueAsString no guarda un archivo. Para escribir a disco, usa writeValue con una ruta convertida a File y una política de guardado explícita.

## Gson: alternativa de biblioteca

En un proyecto con `com.google.code.gson:gson` fijado en el POM (como el kit de prácticas EP2), utiliza Gson y TypeToken. No se necesitan ambas bibliotecas para un mismo ejemplo.

```java
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class DemoGson {
    public record Medicion(String etiqueta, int valor) {
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        String texto = gson.toJson(List.of(new Medicion("A", 21)));
        Type tipo = new TypeToken<List<Medicion>>() {}.getType();
        List<Medicion> recuperadas = gson.fromJson(texto, tipo);
        System.out.println(recuperadas.get(0).valor());
    }
}
```

Salida esperada: 21 con una versión de Gson compatible con records. Consulta el POM de la actividad antes de ejecutar. Estos ejemplos requieren bibliotecas externas y no están incluidos en el test de Java puro.

## Validación y límites

Revisa estructura, campos obligatorios, límites numéricos y política de claves duplicadas. No asumas que el parser aplica reglas de negocio. Diferencia archivo ausente, vacío, JSON malformado y datos inválidos. Para un DAO, [Path y Files](/handbook/java/files/) gestionan rutas y [persistencia](/handbook/foundations/persistence/) explica recuperación. En proyectos modulares, revisa opens según las necesidades de reflexión.

## Referencias

- [Jackson Databind](https://github.com/FasterXML/jackson-databind)
- [Gson User Guide](https://google.github.io/gson/UserGuide.html)
- [Práctica Gson y JavaFX](https://academic-labs.adumun.cl/activities/dsy1102-ep2-microdesafios/)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/puentes-unidad-02/03-java-a-json-con-jackson.md). Los ejemplos no requieren un proyecto de negocio específico.
