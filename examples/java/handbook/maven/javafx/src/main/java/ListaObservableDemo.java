import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class ListaObservableDemo {

    public static void main(String[] args) {
        ObservableList<String> titulos =
                FXCollections.observableArrayList();

        ListChangeListener<String> listener = cambio -> {
            while (cambio.next()) {
                if (cambio.wasAdded()) {
                    System.out.println(cambio.getAddedSubList());
                }
            }
        };

        titulos.addListener(listener);
        titulos.add("Java");
        titulos.removeListener(listener);
    }
}
