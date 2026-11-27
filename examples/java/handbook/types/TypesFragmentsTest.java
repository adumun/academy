public class TypesFragmentsTest {
    static void example0() {
        // snippet:declaration:start
        int edad = 21;
        Integer edadOpcional = null;
        String titulo = "Java";
        boolean disponible = true;
        // snippet:declaration:end
        if (edad != 21 || edadOpcional != null || !titulo.equals("Java") || !disponible) throw new AssertionError("types");
    }

    static void example2() {
        // snippet:conversions:start
        System.out.println(5 / 2);          // 2
        System.out.println(5 / 2.0);        // 2.5
        System.out.println((double) (5 / 2)); // 2.0
        System.out.println((int) -2.9);     // -2
        System.out.println((byte) 130);     // -126
        // snippet:conversions:end
        if (5 / 2 != 2 || (int) -2.9 != -2 || (byte) 130 != -126) throw new AssertionError("conversion");
    }

    public static void main(String[] args) {
        example0();
        example2();
        System.out.println("Types fragments PASS");
    }
}
