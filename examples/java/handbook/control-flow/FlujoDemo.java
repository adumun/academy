import java.util.List;

public class FlujoDemo {

    public static void main(String[] args) {
        List<String> titulos = List.of("Java", "SQL");

        for (String titulo : titulos) {
            System.out.println(titulo);
        }

        int contador = 0;

        while (contador < 2) {
            contador++;
        }

        System.out.println(contador);
    }
}
