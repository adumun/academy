public class ControlFlowFragmentsTest {
    static void example0(int cantidad) {
        // snippet:decision:start
        if (cantidad <= 0) {
            System.out.println("Cantidad inválida");
        } else {
            System.out.println("Cantidad aceptada");
        }
        // snippet:decision:end
    }

    public static void main(String[] args) {
        example0(0);
        example0(-1);
        example0(1);
        System.out.println("ControlFlow fragments PASS");
    }
}
