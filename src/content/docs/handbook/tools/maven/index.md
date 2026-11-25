---
title: "Maven y entorno Java"
description: "Estructura, dependencias, plugins, configuración y diagnóstico del build."
---

## Qué hace Maven

Maven gestiona el build y resuelve dependencias según pom.xml. No instala el servidor MariaDB ni reemplaza al JDK. Un artefacto se identifica por groupId, artifactId y version.

## Estructura de proyecto

```text
libros-app/
├── pom.xml
├── src/main/java/
│   ├── app/Main.java
│   ├── model/Libro.java
│   ├── database/DatabaseConnection.java
│   ├── dao/
│   └── controller/
└── src/main/resources/
    └── view/libro.fxml
```

Maven incorpora los recursos al classpath. Por eso getResource("/view/libro.fxml") puede encontrar el FXML tras compilar.

## POM de referencia

Baseline del curso: Java 21, JavaFX 21.0.6, MariaDB Connector/J 3.5.11. El ejemplo es no modular. Las versiones se mantienen alineadas con las guías docentes; no son una afirmación de que sean las más recientes.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>cl.adumun.examples</groupId>
    <artifactId>libros-app</artifactId>
    <version>1.0.0</version>

    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <javafx.version>21.0.6</javafx.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${javafx.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mariadb.jdbc</groupId>
            <artifactId>mariadb-java-client</artifactId>
            <version>3.5.11</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
            </plugin>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>app.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## Comandos

| Comando | Propósito |
| --- | --- |
| java -version | Identificar Java disponible |
| mvn -version | Verificar Maven y JDK que utiliza |
| mvn clean compile | Limpiar y compilar fuentes |
| mvn dependency:tree | Inspeccionar dependencias |
| mvn test | Ejecutar pruebas configuradas |
| mvn javafx:run | Iniciar la aplicación JavaFX |
| mvn package | Empaquetar según el proyecto |

## Configurar credenciales locales

Configura DB_USER y DB_PASSWORD en la ejecución del IDE o proceso Java. Evita fijarlas en pom.xml y no subas archivos locales con secretos. La configuración debe estar disponible para el proceso que ejecuta Maven o Main.

## Diagnóstico

Invalid target release: Maven está usando un JDK incompatible. Package javafx does not exist: revisa dependencias, importación del proyecto y errores previos de resolución. No suitable driver: revisa el conector y prefijo JDBC. FXML no encontrado: verifica src/main/resources y el nombre exacto del archivo.

## Comprobación

Ejecuta mvn -version, mvn clean compile y mvn javafx:run con el ejemplo mínimo de JavaFX. Un build correcto no certifica que MariaDB esté disponible: prueba la conexión por separado.

## Conceptos relacionados

- [JavaFX](/handbook/java/javafx/)
- [Conexión JDBC](/handbook/java/jdbc/connection/)
- [Diagnóstico](/handbook/troubleshooting/)

## Documentación oficial

- [Introducción a Maven](https://maven.apache.org/guides/getting-started/index.html)
- [OpenJFX con Maven](https://openjfx.io/openjfx-docs/)
- [MariaDB Connector/J](https://mariadb.com/docs/connectors/mariadb-connector-j/installing-mariadb-connectorj)

## Practicar en Academic Labs

- [Laboratorio guiado JDBC y DAO](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-dao-guiado/)
- [Microdesafíos](https://academic-labs.adumun.cl/activities/dsy1102-jdbc-microdesafios/)
