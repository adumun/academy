public class SetsTest {

    public static void main(String[] args) {
        // snippet:insertion-order:start
        java.util.Set<String> etiquetas = new java.util.LinkedHashSet<>();
        etiquetas.add("B");
        etiquetas.add("A");
        etiquetas.add("B");
        System.out.println(etiquetas); // [B, A]
        // snippet:insertion-order:end
        if (!new java.util.ArrayList<>(etiquetas).equals(java.util.List.of("B", "A")) || etiquetas.add("B")) throw new AssertionError("set contract");
        System.out.println("Sets PASS");
    }
}
