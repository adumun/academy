package navegacion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.Objects;
import java.io.IOException;
public class NavegacionDemo {
    private final Stage stage;
    private final Servicio servicio;
    public NavegacionDemo(Stage stage, Servicio servicio) { this.stage = stage; this.servicio = servicio; }
    public void abrirDetalle() throws IOException { mostrarDetalle(); }
    public void volverAlListado() { stage.setScene(new Scene(new Label("Listado: " + servicio.nombre()))); }
    // snippet:navigation:start
    private void mostrarDetalle() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                Objects.requireNonNull(getClass().getResource("/view/detalle.fxml")));
        Parent raiz = loader.load();
        DetalleController controlador = loader.getController();
        controlador.configurar(servicio, this::volverAlListado);
        stage.setScene(new Scene(raiz));
    }
    // snippet:navigation:end
}
