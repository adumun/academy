public class VariablesFragmentsTest {
    static void example0() {
        // snippet:declaration:start
        int cantidad = 3;
        cantidad = 5;
        final int MAXIMO = 10;
        // snippet:declaration:end
        if (cantidad != 5 || MAXIMO != 10) throw new AssertionError("assignment");
    }

    static void example2() {
        // snippet:scope:start
        int total = 10;
        if (total > 0) {
            int incremento = 2;
            total += incremento;
        }
        // total vale 12; incremento no está en alcance aquí.
        // snippet:scope:end
        if (total != 12) throw new AssertionError("scope");
    }

    static void example3() {
        // snippet:final-reference:start
        final StringBuilder texto = new StringBuilder("A");
        texto.append("B");
        System.out.println(texto); // AB
        // texto = new StringBuilder("C"); // no compila
        // snippet:final-reference:end
        if (!texto.toString().equals("AB")) throw new AssertionError("final reference");
    }

    public static void main(String[] args) {
        example0();
        example2();
        example3();
        System.out.println("Variables fragments PASS");
    }
}
