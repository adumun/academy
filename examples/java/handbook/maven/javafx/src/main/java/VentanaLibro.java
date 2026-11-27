import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;
import java.io.IOException;
public class VentanaLibro {
    void mostrar(Stage stage) throws IOException {
        // snippet:loader:start
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(
                        getClass().getResource("/view/libro.fxml")
                )
        );

        Scene scene = new Scene(loader.load(), 480, 320);
        stage.setScene(scene);
        stage.show();
        // snippet:loader:end
    }
}
