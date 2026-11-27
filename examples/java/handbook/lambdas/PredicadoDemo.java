import java.util.function.Predicate;

public class PredicadoDemo {

    private static boolean esPositivo(Integer valor) {
        return valor > 0;
    }

    public static void main(String[] args) {
        Predicate<Integer> primero = new Predicate<Integer>() {
            @Override
            public boolean test(Integer valor) {
                return valor > 0;
            }
        };
        Predicate<Integer> segundo = valor -> valor > 0;
        Predicate<Integer> tercero = PredicadoDemo::esPositivo;

        System.out.println(primero.test(2));
        System.out.println(segundo.test(-1));
        System.out.println(tercero.test(0));
    }
}
