import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {
        Optional<String> titulo = Optional.of("Java");
        Optional<String> ausente = Optional.empty();

        System.out.println(titulo.orElse("Sin título"));
        System.out.println(ausente.orElse("Sin título"));
    }
}
