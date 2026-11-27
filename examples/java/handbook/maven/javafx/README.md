# JavaFX ejecutable — Handbook

JDK 21 / JavaFX 21.0.6 / JUnit 5.11.4. En Linux: `xvfb-run -a mvn -B -ntp clean test`; en escritorio con JavaFX/GTK disponible: `mvn clean test`. `mvn javafx:run` abre la ventana mínima.

Primera entrega: nueve fichas, 18 bloques Java y dos recursos FXML. Suite FxRuntimeTest, diez casos esperados, toolkit real y JavaFX Application Thread. DAO simulado; no MariaDB. No certifica revisión visual manual ni Scene Builder como herramienta. Pendientes: controles, TableView general, validación y XML/FXML conceptual. Las versiones son baseline didáctico, no certificación de seguridad.
