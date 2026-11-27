import java.util.ArrayList;
import java.util.List;

public class LambdasDemo {

    public static void main(String[] args) {
        List<String> titulos = new ArrayList<>(
                List.of("Java", "", "SQL")
        );

        titulos.removeIf(titulo -> titulo.isBlank());
        titulos.forEach(System.out::println);
    }
}
