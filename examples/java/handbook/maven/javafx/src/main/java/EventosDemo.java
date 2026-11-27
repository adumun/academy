import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventosDemo extends Application {

    @Override
    public void start(Stage stage) {
        TextField entrada = new TextField();
        Label mensaje = new Label("Sin accion");
        Button aceptar = new Button("Aceptar");
        aceptar.setDisable(true);
        entrada.textProperty().addListener((observable, anterior, actual) -> {
            aceptar.setDisable(actual.isBlank());
        });
        aceptar.setOnAction(evento -> {
            mensaje.setText("Aceptado: " + entrada.getText());
        });
        stage.setScene(new Scene(new VBox(10, entrada, aceptar, mensaje), 340, 160));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
