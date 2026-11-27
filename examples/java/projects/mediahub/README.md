# MediaHub · ADÜMÜN Academy

Proyecto de referencia Java de consola. No requiere Maven ni bibliotecas externas. Requiere JDK 21.

Desde la carpeta `examples/java/projects/mediahub`:

```bash
mkdir -p out
javac -encoding UTF-8 --release 21 -d out $(find src -name '*.java')
java -cp out cl.adumun.academy.mediahub.Main
```

En Windows o IntelliJ IDEA, abre la carpeta, marca `src` como Sources Root, configura JDK 21 y ejecuta `Main`. Los archivos están organizados por responsabilidades de dominio, lectura de entrada y coordinación.

Consulta la guía del proyecto en https://academy.adumun.cl/projects/mediahub/. Esta adaptación conserva el comportamiento de la referencia; aún no certifica compilación ni ejecución de este nuevo snapshot. Las pruebas automatizadas y revisión pedagógica completa se abordarán en ADU-236.
