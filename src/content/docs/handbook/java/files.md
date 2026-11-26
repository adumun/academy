---
title: "Archivos en Java: Path y Files (NIO.2)"
description: "Rutas, directorios, lectura/escritura, recursos y recuperación."
---

**Tipo:** explicación y referencia. **Nivel:** esencial a complementario.

## Qué hace cada API

La [persistencia](/handbook/foundations/persistence/) en archivos necesita una ubicación y una operación de E/S (entrada/salida). Path representa una ruta; Files opera sobre archivos y directorios; File es una abstracción histórica compatible. Una ruta relativa se resuelve respecto al directorio de trabajo del proceso; `toAbsolutePath()` ayuda a diagnosticar qué archivo utiliza la aplicación. No es el classpath.

## Ejemplo autónomo

Archivo `ArchivosDemo.java`. Compila con `javac --release 21 ArchivosDemo.java` y ejecuta `java ArchivosDemo`.

```java
import java.nio.file.Files;
import java.nio.file.Path;

public class ArchivosDemo {

    public static void main(String[] args) throws Exception {
        Path carpeta = Files.createTempDirectory("academy-");
        Path archivo = carpeta.resolve("mediciones.txt");
        try {
            Files.writeString(archivo, "21");
            System.out.println(Files.readString(archivo));
        } finally {
            Files.deleteIfExists(archivo);
            Files.deleteIfExists(carpeta);
        }
    }
}
```

Salida esperada:

```text
21
```

| Método | Propósito |
|---|---|
| Path.of(...) | Construir ruta |
| resolve / resolveSibling | Obtener otra ruta sin mover archivos |
| Files.createDirectories | Crear directorios necesarios |
| Files.readString / writeString | Leer o escribir texto UTF-8 por defecto |
| Files.copy / move | Copiar o mover; revisar opciones y permisos |
| Files.list | Stream de entradas; cerrar con try-with-resources |

## Fallos y resguardo

IOException indica un problema de acceso. Files.exists y notExists pueden devolver ambos false cuando no se puede determinar existencia; no conviertas automáticamente un fallo de acceso en ausencia. Usa temporales y datos de prueba; `ATOMIC_MOVE` depende del proveedor del sistema de archivos y su interacción con destino existente es específica. No reemplaces archivos reales sin estrategia de recuperación.

## Ruta y operación de entrada/salida

![Path identifica una ubicación de archivo. Files usa esa ruta para leer o escribir bytes o texto; el resultado se representa en memoria.](/diagrams/java-path-files.svg)

Path no abre un archivo por sí mismo. resolve construye una nueva ruta a partir de otra; no crea carpetas ni mueve archivos. Una ruta relativa depende del directorio de trabajo, que puede variar entre terminal e IDE. El classpath localiza clases y recursos empaquetados y no es el mismo mecanismo.

### Diagnosticar sin modificar datos

```java
java.nio.file.Path archivo = java.nio.file.Path.of("data", "mediciones.txt");
System.out.println(archivo.toAbsolutePath().normalize());
System.out.println(java.nio.file.Files.isRegularFile(archivo));
```

Fragmento de método. La salida depende del entorno. normalize elimina componentes redundantes de la ruta según reglas sintácticas; no verifica existencia ni resuelve enlaces simbólicos. Si necesitas determinar el destino real, utiliza las APIs adecuadas y contempla sus excepciones.

## Lectura frente a escritura

readString resulta adecuado para archivos de texto pequeños; para volúmenes grandes conviene procesamiento incremental. writeString sin opciones específicas crea o trunca el archivo según su contrato. Para anexar datos se requieren opciones explícitas. No escribas directamente sobre el único archivo valioso si debes preservar el estado anterior: valida datos, usa temporal y aplica una política de reemplazo y respaldo. Consulta [persistencia](/handbook/foundations/persistence/). El ejemplo autónomo trabaja en una carpeta temporal y la elimina al terminar; no utiliza datos reales.

## Documentación oficial

- [Path](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html)
- [Files](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/docs/GUIA-NIO2-PATH-FILES-R06.md). Los ejemplos no requieren un proyecto de negocio específico.
