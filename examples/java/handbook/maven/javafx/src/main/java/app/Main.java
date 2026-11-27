package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label saludo = new Label("Hola, JavaFX");
        VBox root = new VBox(12, saludo);
        Scene scene = new Scene(root, 480, 320);

        stage.setTitle("Primera ventana");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
