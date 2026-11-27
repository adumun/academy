import javafx.scene.control.*;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
public class BindingFragmentos {
    final Button btnGuardar = new Button();
    final TextField txtTitulo = new TextField();
    void vincular() {
        // snippet:form:start
        btnGuardar.disableProperty().bind(
                txtTitulo.textProperty().isEmpty()
        );
        // snippet:form:end
    }
    static void total() {
        // snippet:total:start
        SimpleIntegerProperty cantidad = new SimpleIntegerProperty(2);
        SimpleIntegerProperty precio = new SimpleIntegerProperty(5);
        IntegerBinding total = cantidad.multiply(precio);
        System.out.println(total.get()); // 10
        cantidad.set(3);
        System.out.println(total.get()); // 15
        // snippet:total:end
    }
}
