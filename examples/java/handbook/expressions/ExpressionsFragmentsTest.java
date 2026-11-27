public class ExpressionsFragmentsTest {
    static void example0() {
        // snippet:boolean-range:start
        int cantidad = 3 + 2;
        boolean valida = cantidad > 0 && cantidad <= 10;
        // snippet:boolean-range:end
        if (cantidad != 5 || !valida) throw new AssertionError("range");
    }

    static void example1() {
        // snippet:null-guard:start
        String titulo = null;

        if (titulo != null && !titulo.isBlank()) {
            System.out.println(titulo);
        }
        // snippet:null-guard:end
        if (titulo != null) throw new AssertionError("guard");
    }

    public static void main(String[] args) {
        example0();
        example1();
        System.out.println("Expressions fragments PASS");
    }
}
