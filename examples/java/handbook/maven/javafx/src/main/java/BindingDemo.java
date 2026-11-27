import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BindingDemo {

    public static void main(String[] args) {
        StringProperty origen =
                new SimpleStringProperty("Java");
        StringProperty destino =
                new SimpleStringProperty();

        destino.bind(origen);
        System.out.println(destino.get());

        origen.set("JavaFX");
        System.out.println(destino.get());

        destino.unbind();
        destino.set("SQL");
        System.out.println(destino.get());
    }
}
