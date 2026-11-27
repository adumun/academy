import java.util.ArrayList;
import java.util.List;

public class ColeccionesDemo {

    public static void main(String[] args) {
        List<String> titulos = new ArrayList<>();
        titulos.add("Java");
        titulos.add("SQL");
        titulos.add("Java");

        for (String titulo : titulos) {
            System.out.println(titulo);
        }

        System.out.println("Total: " + titulos.size());
    }
}
