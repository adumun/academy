public class OptionalTest {
    private static int calls;
    private static String alternativa() { calls++; return "B"; }

    public static void main(String[] args) {
        // snippet:fallback:start
        java.util.Optional<String> dato = java.util.Optional.of("A");
        System.out.println(dato.orElse(alternativa()));
        System.out.println(dato.orElseGet(() -> alternativa()));
        // snippet:fallback:end
        if (calls != 1) throw new AssertionError("eager vs lazy");
        if (!java.util.Optional.<String>empty().orElseGet(OptionalTest::alternativa).equals("B") || calls != 2) throw new AssertionError("empty fallback");
        if (!dato.map(String::length).equals(java.util.Optional.of(1))) throw new AssertionError("map");
        System.out.println("Optional PASS");
    }
}
