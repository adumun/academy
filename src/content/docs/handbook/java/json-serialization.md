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

<!-- example:jackson-typed-list -->
```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

public class DemoJackson {
    public record Medicion(String etiqueta, int valor) {
    }

    public static String escribir(List<Medicion> datos) throws Exception {
        return new ObjectMapper().writeValueAsString(datos);
    }

    public static List<Medicion> leer(String texto) throws Exception {
        return new ObjectMapper().readValue(
                texto, new TypeReference<List<Medicion>>() {});
    }

    public static void main(String[] args) throws Exception {
        String texto = escribir(List.of(new Medicion("A", 21)));
        List<Medicion> recuperadas = leer(texto);
        System.out.println(recuperadas.get(0).valor());
    }
}
```

Salida esperada: 21. TypeReference captura información del tipo genérico mediante clase anónima; no es una lambda. writeValueAsString no guarda un archivo. Para escribir a disco, usa writeValue con una ruta convertida a File y una política de guardado explícita.

## Gson: alternativa de biblioteca

En el proyecto de referencia se fija `com.google.code.gson:gson:2.11.0`. Gson y TypeToken permiten recuperar una lista tipada; Strictness.STRICT solicita sintaxis JSON estricta. No se necesitan ambas bibliotecas para un mismo ejemplo.

<!-- example:gson-typed-list -->
```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class DemoGson {
    public record Medicion(String etiqueta, int valor) {
    }

    private static final Gson GSON = new GsonBuilder()
            .setStrictness(Strictness.STRICT).create();
    private static final Type TIPO = new TypeToken<List<Medicion>>() {}.getType();

    public static String escribir(List<Medicion> datos) {
        return GSON.toJson(datos);
    }

    public static List<Medicion> leer(String texto) {
        return GSON.fromJson(texto, TIPO);
    }

    public static void main(String[] args) {
        String texto = escribir(List.of(new Medicion("A", 21)));
        List<Medicion> recuperadas = leer(texto);
        System.out.println(recuperadas.get(0).valor());
    }
}
```

Salida esperada: 21 con una versión de Gson compatible con records. El POM propio de Academy reúne ambas bibliotecas para comprobar los ejemplos, no porque una aplicación necesite las dos. El runner Java puro no incluye estas dependencias.

## Validación y límites

Revisa estructura, campos obligatorios, límites numéricos y política de claves duplicadas. No asumas que el parser aplica reglas de negocio. Diferencia archivo ausente, vacío, JSON malformado y datos inválidos. Para un DAO, [Path y Files](/handbook/java/files/) gestionan rutas y [persistencia](/handbook/foundations/persistence/) explica recuperación. En proyectos modulares, revisa opens según las necesidades de reflexión.

## Qué se transforma y qué se conserva

```text
Instancia Java ── serializar ──► texto JSON
Instancia Java ◄─ deserializar ─ texto JSON
                                     │
                            lectura/escritura opcional
                                     ▼
                                   archivo
```

El texto representa datos, no la identidad en memoria ni los métodos del objeto. Una instancia reconstruida puede tener los mismos valores sin ser la misma referencia. La biblioteca utiliza la estructura y el tipo esperado para decidir la conversión. Un record facilita expresar un portador de datos; no garantiza que sus componentes cumplan reglas de dominio.

### Comparar las operaciones

| Necesidad | Jackson | Gson |
|---|---|---|
| Crear texto | writeValueAsString | toJson |
| Leer un objeto | readValue con clase | fromJson con clase |
| Leer una lista tipada | TypeReference | TypeToken |
| Persistir | API de escritura o Files | Writer o Files |

Una lista genérica requiere conservar información sobre el tipo de sus elementos. Leer únicamente List.class puede producir representaciones genéricas en vez de instancias del tipo esperado. TypeReference y TypeToken capturan ese tipo para la biblioteca; no validan automáticamente sus valores.

## Probar la ida y vuelta

En los ejemplos anteriores, imprime también texto: se espera un arreglo JSON con una medición que contiene etiqueta A y valor 21. No dependas del orden de las propiedades. Comprueba lista vacía, campo ausente, valor de tipo incorrecto y texto malformado; la respuesta concreta depende de la configuración y versión de cada biblioteca. Declara qué entradas rechaza tu aplicación, en vez de aceptar implícitamente cualquier conversión. El proyecto Maven propio comprueba las listas tipadas, listas vacías, campos ausentes, estructuras incompatibles y texto truncado. Un campo int ausente se representa con 0 en estos ejemplos; si es obligatorio, la aplicación debe validar su presencia. No interpretes el valor por defecto como una medición válida.

## Verificación ejecutable

Fuentes canónicas: `examples/java/handbook/maven/json-junit/src/main/java/`. Desde `examples/java/handbook/maven/json-junit/` ejecuta `mvn -B -ntp clean test`; las suites DemoJacksonTest y DemoGsonTest comprueban seis casos cada una, incluida la salida publicada. No requieren una base de datos. Consulta los informes Surefire y los resultados CI del commit utilizado, no solo el build del sitio.

## Referencias

- [Jackson Databind](https://github.com/FasterXML/jackson-databind)
- [Gson User Guide](https://google.github.io/gson/UserGuide.html)
- [Práctica Gson y JavaFX](https://academic-labs.adumun.cl/activities/dsy1102-ep2-microdesafios/)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/docs/extras/puentes-unidad-02/03-java-a-json-con-jackson.md). Los ejemplos no requieren un proyecto de negocio específico.
