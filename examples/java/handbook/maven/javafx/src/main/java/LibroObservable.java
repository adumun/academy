import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LibroObservable {

    private final StringProperty titulo;

    public LibroObservable(String titulo) {
        this.titulo = new SimpleStringProperty(titulo);
    }

    public String getTitulo() {
        return titulo.get();
    }

    public void setTitulo(String valor) {
        titulo.set(valor);
    }

    public StringProperty tituloProperty() {
        return titulo;
    }
}
