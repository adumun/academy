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

## Documentación oficial

- [Path](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Path.html)
- [Files](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html)

## Procedencia didáctica

Adaptación original de [material docente versionado](https://github.com/cmartinezs/DSY1102-DESARROLLO-ORIENTADO-OBJETOS-2026-2/blob/a28dbca877a9ef6e0105777cae48ba4fa252f10c/clases/unidad-02/capsula-tiempo-fx/docs/GUIA-NIO2-PATH-FILES-R06.md). Los ejemplos no requieren un proyecto de negocio específico.
