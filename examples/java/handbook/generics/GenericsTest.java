import java.util.List;
import java.util.ArrayList;

public class GenericsTest {
    // snippet:first:start
    public static <T> T primero(List<T> elementos) {
        if (elementos.isEmpty()) {
            throw new IllegalArgumentException("Lista vacía");
        }

        return elementos.get(0);
    }
    // snippet:first:end

    // snippet:upper-bound:start
    public static double sumar(List<? extends Number> valores) {
        double total = 0;

        for (Number valor : valores) {
            total += valor.doubleValue();
        }

        return total;
    }
    // snippet:upper-bound:end

    // snippet:choose:start
    public static <T> T elegir(boolean usarPrimero, T primero, T segundo) {
        if (usarPrimero) {
            return primero;
        }
        return segundo;
    }
    // snippet:choose:end
    public static void main(String[] args) {
        // snippet:typed-list:start
        List<String> titulos = new ArrayList<>();
        titulos.add("Java");
        String primero = titulos.get(0);
        // snippet:typed-list:end
        if (!primero.equals("Java") || !primero(java.util.List.of("A")).equals("A") || primero(java.util.List.of(7)) != 7) throw new AssertionError("generic first");
        try { primero(java.util.List.of()); throw new AssertionError("empty accepted"); } catch (IllegalArgumentException expected) {}
        if (sumar(java.util.List.of(1, 2, 3)) != 6.0 || sumar(java.util.List.of()) != 0.0 || sumar(java.util.List.of(1.5, 2.5)) != 4.0) throw new AssertionError("upper bound");
        if (!elegir(true, "A", "B").equals("A") || elegir(false, 1, 2) != 2 || new Caja<>(21).getValor() != 21) throw new AssertionError("generic selection");
        System.out.println("Generics PASS");
    }
}
