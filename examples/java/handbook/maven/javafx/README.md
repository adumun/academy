# JavaFX ejecutable — Handbook

JDK 21 / JavaFX 21.0.6 / JUnit 5.11.4. En Linux: `xvfb-run -a mvn -B -ntp clean test`; en escritorio con JavaFX/GTK disponible: `mvn clean test`. `mvn javafx:run` abre la ventana mínima.

Cobertura de 13 fichas: 33 bloques Java y 5 XML/FXML, 38 snippets. Suite FxRuntimeTest, 20 casos esperados, toolkit real y JavaFX Application Thread. Controles, tablas, validación, alertas modales, separación de escritura/refresco y casos XML/FXML negativos. DAO simulado; no MariaDB. No certifica revisión visual manual, distribución modular ni Scene Builder como herramienta. Las versiones son baseline didáctico, no certificación de seguridad.
