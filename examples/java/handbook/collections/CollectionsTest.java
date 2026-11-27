public class CollectionsTest {

    public static void main(String[] args) {

        var lista = new java.util.ArrayList<>(java.util.List.of(1, 8, 1));
        if (lista.remove(1) != 8 || !lista.equals(java.util.List.of(1, 1))) throw new AssertionError("index removal");
        if (!lista.remove(Integer.valueOf(1)) || !lista.equals(java.util.List.of(1))) throw new AssertionError("value removal");
        var copia = java.util.List.copyOf(lista);
        lista.add(2);
        if (!copia.equals(java.util.List.of(1))) throw new AssertionError("snapshot");
        try { copia.add(3); throw new AssertionError("mutable snapshot"); } catch (UnsupportedOperationException expected) {}
        System.out.println("Collections PASS");
    }
}
