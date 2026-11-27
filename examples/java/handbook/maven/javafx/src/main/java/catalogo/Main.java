package catalogo;

import java.util.Locale;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public record Libro(int id, String titulo) {
    }

    @Override
    public void start(Stage stage) {
        ObservableList<Libro> fuente =
                FXCollections.observableArrayList(
                        new Libro(1, "Java"),
                        new Libro(2, "SQL"),
                        new Libro(3, "JavaFX")
                );

        TableView<Libro> tabla = new TableView<>();
        TableColumn<Libro, Integer> colId = new TableColumn<>("ID");
        TableColumn<Libro, String> colTitulo =
                new TableColumn<>("Título");

        colId.setCellValueFactory(
                celda -> new ReadOnlyObjectWrapper<>(
                        celda.getValue().id()
                )
        );
        colTitulo.setCellValueFactory(
                celda -> new ReadOnlyStringWrapper(
                        celda.getValue().titulo()
                )
        );
        tabla.getColumns().add(colId);
        tabla.getColumns().add(colTitulo);

        FilteredList<Libro> filtrada =
                new FilteredList<>(fuente, libro -> true);
        SortedList<Libro> ordenada = new SortedList<>(filtrada);

        ordenada.comparatorProperty().bind(
                tabla.comparatorProperty()
        );
        tabla.setItems(ordenada);

        TextField buscador = new TextField();
        buscador.setPromptText("Buscar por título");

        buscador.textProperty().addListener(
                (observable, anterior, actual) -> {
                    String texto = actual.strip()
                            .toLowerCase(Locale.ROOT);

                    filtrada.setPredicate(libro ->
                            libro.titulo()
                                    .toLowerCase(Locale.ROOT)
                                    .contains(texto)
                    );
                }
        );

        VBox root = new VBox(12, buscador, tabla);
        stage.setScene(new Scene(root, 520, 360));
        stage.setTitle("Catálogo filtrable");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
