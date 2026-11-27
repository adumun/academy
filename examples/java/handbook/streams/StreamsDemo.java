import java.util.List;

public class StreamsDemo {

    public static void main(String[] args) {
        List<Integer> mediciones = List.of(1, -2, 3);
        boolean invalida = mediciones.stream().anyMatch(valor -> valor < 0);
        List<Integer> positivas = mediciones.stream()
                .filter(valor -> valor > 0)
                .map(valor -> valor * 2)
                .toList();

        System.out.println(invalida);
        System.out.println(positivas);
    }
}
