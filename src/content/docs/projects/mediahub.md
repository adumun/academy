---
title: "MediaHub"
description: "Proyecto de consola para integrar abstracción, herencia, interfaces y colecciones."
---

**Tipo:** proyecto integrador autónomo. **Entorno:** Java de consola; no requiere JavaFX, Maven ni base de datos. **Resultado:** biblioteca multimedia ejecutable.

## Requerimiento

MediaHub administra libros físicos, películas, ebooks y audiolibros. Todos tienen código, título y creador; cada tipo posee una política de préstamo distinta. Los digitales también pueden descargarse. La aplicación permite registrar, listar, buscar por código, consultar días de préstamo, prestar, devolver, descargar cuando corresponde, controlar entradas inválidas y salir del menú.

## Conocimientos previos recomendados

- [Clases y objetos](/handbook/java/classes-and-objects/)
- [Interfaces y polimorfismo](/handbook/java/interfaces/)
- [Colecciones](/handbook/java/collections/)
- [Excepciones](/handbook/java/exceptions/)
- [Paquetes e imports](/handbook/java/packages-and-imports/)

## Responsabilidades

```text
Main → LecturaEntrada (entrada validada)
Main → MediaHub → List<Recurso>
                     ├─ LibroFisico
                     ├─ Pelicula
                     ├─ Ebook (Descargable)
                     └─ Audiolibro (Descargable)
```

MediaHub administra y crea los recursos. El modelo expresa estado y comportamiento. Main solicita operaciones a MediaHub, sin decidir cómo construir cada subtipo. LecturaEntrada se ocupa de Scanner y del reintento de una entrada incorrecta.

## Incremento M01 — Modelar el problema

**Trabajo:** Identifica código, título y creador comunes; diferencia familias de recursos y capacidades. Dibuja dependencias de modelo, MediaHub, LecturaEntrada y Main.

**Checkpoint:** Justifica por qué el menú no crea directamente los objetos concretos.


**Proyecto:** Biblioteca MediaHub

### Requerimiento

MediaHub es una biblioteca multimedia que administra distintos recursos para sus usuarios.

Actualmente trabaja con:

- libros físicos;
- películas;
- ebooks;
- audiolibros.

Todos poseen un código, título y creador. Cada tipo tiene una política de préstamo distinta. Los recursos digitales además pueden descargarse.

La aplicación debe permitir:

1. registrar recursos;
2. listar todos los recursos;
3. buscar por código;
4. consultar los días de préstamo de cada recurso;
5. prestar y devolver recursos;
6. descargar un recurso cuando corresponda;
7. controlar entradas inválidas;
8. continuar funcionando hasta que el usuario decida salir.

### Antes de programar

Responder:

- ¿qué información comparten todos los recursos?
- ¿qué comportamiento cambia según el tipo?
- ¿qué diferencia hay entre un recurso físico y uno digital?
- ¿“Descargable” representa una familia o una capacidad?
- ¿quién debería administrar la lista?
- ¿quién debería crear los objetos de dominio?
- ¿quién debería hacerse responsable de leer y validar la entrada?
- ¿qué debería hacer Main y qué no?

### Modelo inicial

```text
dominio/
├── Recurso
├── Descargable
├── LibroFisico
├── Pelicula
├── Ebook
└── Audiolibro

MediaHub
├── crea objetos de dominio
└── administra List<Recurso>

LecturaEntrada
└── administra Scanner y protege la entrada

Main
└── coordina la interacción
```

### Estructura objetivo

```text
mediahub/
└── src/
    └── cl/adumun/academy/mediahub/
        ├── dominio/
        │   ├── Recurso.java
        │   ├── Descargable.java
        │   ├── LibroFisico.java
        │   ├── Pelicula.java
        │   ├── Ebook.java
        │   └── Audiolibro.java
        ├── MediaHub.java
        ├── LecturaEntrada.java
        └── Main.java
```

### Responsabilidades

- `dominio`: contiene las entidades y contratos propios del problema.
- `MediaHub`: crea objetos de dominio y administra el conjunto de recursos.
- `LecturaEntrada`: encapsula `Scanner`, lee datos y repite la solicitud cuando la entrada no es válida.
- `Main`: muestra el menú y coordina las llamadas entre las demás clases.

### Idea de diseño importante

Main no debería conocer cómo se construye un `LibroFisico`, `Pelicula`, `Ebook` o `Audiolibro`.

En lugar de:

```java
new Pelicula(...)
```

Main delega:

```java
mediaHub.registrarPelicula(codigo, titulo, director);
```

### Avance consolidado esperado

Debes poder justificar las entidades, los packages y las responsabilidades. La creación de objetos del dominio queda encapsulada en MediaHub.


## Incremento M02 — Construir jerarquía

**Trabajo:** Declara Recurso abstracto y las clases LibroFisico, Pelicula, Ebook y Audiolibro; cada tipo define sus días de préstamo.

**Checkpoint:** La clase base no se instancia; se invoca la política de préstamo mediante Recurso.


### ¿Tiene sentido un Recurso genérico?

Todos los elementos de MediaHub son recursos, pero en la práctica siempre pertenecen a un tipo concreto.

Por eso:

```java
public abstract class Recurso
```

`Recurso` concentra:

- código;
- título;
- creador;
- estado disponible/no disponible;
- comportamiento común para prestar y devolver.

### Método abstracto

La cantidad de días permitidos cambia según el tipo de recurso.

```java
public abstract int obtenerDiasPrestamo();
```

Ejemplo conceptual:

```text
Libro físico → 14 días
Película     → 5 días
Ebook        → 21 días
Audiolibro   → 14 días
```

### Conocimiento que debes adquirir

Debes poder explicar:

- por qué Recurso es abstracta;
- qué comportamiento sí puede implementarse una sola vez;
- qué comportamiento debe quedar obligado para los subtipos;
- cómo `super(...)` inicializa la parte heredada.

### Avance consolidado esperado

Al finalizar:

- existe `Recurso.java`;
- contiene los atributos comunes;
- no puede instanciarse;
- declara `obtenerDiasPrestamo()` como abstracto;
- existen los cuatro subtipos;
- cada subtipo implementa su política de préstamo.


## Incremento M03 — Expresar capacidad

**Trabajo:** Define Descargable e impleméntala solo en Ebook y Audiolibro. Distingue capacidad de familia.

**Checkpoint:** Los recursos digitales permiten descargar; los físicos no ofrecen esa capacidad.


### Nuevo requisito

MediaHub ofrece descarga para sus recursos digitales.

Pregunta:

> ¿Descargable es otro tipo de Recurso?

No. Es una **capacidad**.

```java
public interface Descargable {
    void descargar();
}
```

Los recursos digitales implementan el contrato:

```text
Ebook       extends Recurso implements Descargable
Audiolibro  extends Recurso implements Descargable
```

Los recursos físicos siguen siendo Recursos, pero no son Descargables.

### Diferencia conceptual

```text
Ebook ES UN Recurso.
Ebook PUEDE SER Descargable.

extends     → generalización / especialización
implements  → capacidad / contrato
```

### Conocimiento que debes adquirir

Debes poder decidir si un nuevo requisito representa:

- una nueva especialización;
- una capacidad transversal;
- comportamiento común de la clase base.

### Avance consolidado esperado

- existe `Descargable.java`;
- Ebook y Audiolibro implementan la interfaz;
- LibroFisico y Pelicula no la implementan;
- puedes justificar la decisión sin hablar solamente de sintaxis.


## Incremento M04 — Administrar recursos

**Trabajo:** MediaHub crea objetos concretos y los administra en List<Recurso>; incorpora registro, listado, búsqueda, préstamo y devolución.

**Checkpoint:** Registra tipos distintos, recupera por código y verifica cambios válidos de estado.


Ahora necesitamos administrar múltiples recursos.

### Clase responsable

```text
MediaHub
└── List<Recurso>
```

La lista usa el tipo padre:

```java
List<Recurso> recursos = new ArrayList<>();
```

y puede contener cualquier subtipo.

### MediaHub también crea los objetos

Para evitar que Main conozca las clases concretas del dominio, MediaHub ofrece operaciones de registro:

```java
public void registrarLibroFisico(String codigo, String titulo, String autor)
public void registrarPelicula(String codigo, String titulo, String director)
public void registrarEbook(String codigo, String titulo, String autor)
public void registrarAudiolibro(String codigo, String titulo, String creador)
```

MediaHub recibe datos simples y decide qué objeto crear.

### Operación polimórfica

```java
for (Recurso recurso : recursos) {
    System.out.println(recurso.obtenerDiasPrestamo());
}
```

Cada objeto responde con su propia política sin preguntar manualmente su tipo.

### Operaciones esperadas

`MediaHub` debe poder:

- crear y agregar recursos;
- listar;
- buscar por código;
- prestar;
- devolver;
- mostrar políticas de préstamo;
- descargar cuando el recurso cumpla el contrato.

### Conocimiento que debes adquirir

Debes poder explicar:

- por qué usamos `List<Recurso>`;
- cómo se almacenan distintos subtipos juntos;
- cómo aparece el polimorfismo;
- por qué Main no crea objetos concretos del dominio;
- por qué estas operaciones pertenecen a MediaHub.

### Avance consolidado esperado

Debe existir `MediaHub.java` con su `List<Recurso>`, operaciones de registro y administración.


## Incremento M05 — Integrar interacción

**Trabajo:** LecturaEntrada concentra Scanner y entradas inválidas; Main coordina el menú hasta salir y captura errores en el límite de interacción.

**Checkpoint:** Una entrada incorrecta no cierra el programa; el usuario puede corregirla.


### Responsabilidad de LecturaEntrada

`LecturaEntrada` encapsula `Scanner` y protege la interacción frente a entradas inválidas.

Esta clase se hace responsable de:

- leer texto;
- impedir valores vacíos;
- leer enteros;
- volver a solicitar cuando el usuario escribe texto donde se espera un número;
- validar rangos simples;
- cerrar Scanner al terminar.

### Responsabilidad de Main

Main mantiene una única instancia compartida de sus colaboradores de consola:

```java
private static final LecturaEntrada entrada = new LecturaEntrada();
private static final MediaHub mediaHub = new MediaHub();
```

Esto evita pasarlos como parámetros entre los métodos auxiliares del menú. No estamos estudiando el patrón Singleton; simplemente utilizamos atributos estáticos compartidos dentro de `Main` para mantener la clase simple y legible.

Main se concentra en:

- mostrar opciones;
- decidir qué operación ejecutar;
- pedir datos mediante `LecturaEntrada`;
- delegar la creación y gestión de recursos a `MediaHub`;
- capturar errores provenientes del dominio.

Main no utiliza `new LibroFisico(...)`, `new Pelicula(...)`, `new Ebook(...)` ni `new Audiolibro(...)`.

Ejemplo:

```java
private static void registrarPelicula() {
    mediaHub.registrarPelicula(
            entrada.leerTextoNoVacio("Código: "),
            entrada.leerTextoNoVacio("Título: "),
            entrada.leerTextoNoVacio("Director: ")
    );
}
```

### Menú objetivo

```text
=== BIBLIOTECA MEDIAHUB ===
1. Registrar libro físico
2. Registrar película
3. Registrar ebook
4. Registrar audiolibro
5. Listar recursos
6. Buscar recurso
7. Mostrar políticas de préstamo
8. Prestar recurso
9. Devolver recurso
10. Descargar recurso
0. Salir
```

### Dos tipos de errores

#### Errores de entrada
Los controla `LecturaEntrada`:

- opción no numérica;
- opción fuera de rango;
- texto vacío.

#### Errores del dominio
Los detectan las clases responsables y Main los informa:

- código duplicado;
- recurso inexistente;
- prestar un recurso no disponible;
- devolver uno ya disponible;
- descargar un recurso físico.

### Avance consolidado esperado

- existe `LecturaEntrada.java`;
- Scanner queda encapsulado;
- Main usa un `switch` clásico;
- `LecturaEntrada` y `MediaHub` son atributos `static final` de Main;
- los métodos auxiliares no necesitan recibirlos como parámetros;
- Main no instancia clases concretas del dominio;
- las operaciones delegan en MediaHub;
- los errores no terminan abruptamente la aplicación.


## Incremento M06 — Comprobar solución

**Trabajo:** Ejecuta registro, búsqueda, días de préstamo, prestar, devolver y descargar. Revisa dependencias y errores.

**Checkpoint:** Compila, permite completar el flujo y rechaza operaciones inválidas sin terminar inesperadamente.


### Mapa completo

```text
requerimiento
→ package dominio
→ Recurso abstracto
→ subtipos
→ interfaz Descargable
→ MediaHub crea y administra
→ List<Recurso>
→ polimorfismo
→ LecturaEntrada
→ Main coordina
→ excepciones
→ prueba
```

### Checklist

- [ ] el proyecto compila;
- [ ] las entidades están en `cl.adumun.academy.mediahub.dominio`;
- [ ] Recurso no puede instanciarse;
- [ ] cada subtipo define sus días de préstamo;
- [ ] Ebook y Audiolibro son Descargables;
- [ ] todos los recursos caben en una misma `List<Recurso>`;
- [ ] MediaHub crea los objetos concretos;
- [ ] Main no utiliza constructores del dominio;
- [ ] LecturaEntrada concentra Scanner;
- [ ] buscar por código funciona;
- [ ] prestar y devolver respetan el estado;
- [ ] descargar funciona solo donde corresponde;
- [ ] los errores no terminan la aplicación.

### Qué deberías poder explicar

- ¿por qué las entidades viven en un package de dominio?
- ¿por qué Recurso es abstracta?
- ¿por qué Descargable es interfaz?
- ¿por qué la lista usa Recurso?
- ¿por qué Main no crea objetos concretos?
- ¿qué responsabilidad tiene MediaHub?
- ¿qué responsabilidad tiene LecturaEntrada?
- ¿qué responsabilidad tiene Main?


## Pruebas de aceptación

| Escenario | Resultado esperado |
|---|---|
| Registrar cada tipo | Todos se listan por el mismo contrato Recurso |
| Buscar código | Se recupera el recurso que corresponde; ausencia tratada |
| Consultar préstamo | Cada subtipo devuelve su política |
| Prestar y devolver | Cambia el estado solo mediante operaciones válidas |
| Descargar recurso físico | No se ofrece una capacidad que no existe |
| Entrada inválida | Se informa el problema y continúa el menú |

## Implementación de referencia

Después de diseñar tu solución, consulta el [proyecto Java de referencia](https://github.com/adumun/academy/tree/master/examples/java/projects/mediahub) mantenido por ADÜMÜN. Clona Academy, abre `examples/java/projects/mediahub` con JDK 21 y marca `src` como carpeta de fuentes. Ejecuta `cl.adumun.academy.mediahub.Main`. También puedes usar la terminal desde esa carpeta:

```bash
mkdir -p out
javac -encoding UTF-8 --release 21 -d out $(find src -name '*.java')
java -cp out cl.adumun.academy.mediahub.Main
```

No requiere Maven, base de datos ni bibliotecas externas. Consulta el README del proyecto para la estructura. Los escenarios de aceptación son resultados esperados; no se certifica ejecución nueva de este snapshot.

## Procedencia

Adaptación editorial de material didáctico propio. La procedencia y el commit de origen se registran en el manifiesto editorial del repositorio de Academy; no son requisitos para completar el proyecto.
