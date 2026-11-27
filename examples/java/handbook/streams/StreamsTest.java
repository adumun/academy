public class StreamsTest {

    public static void main(String[] args) {
        // snippet:loop-equivalence:start
        java.util.List<Integer> resultado = new java.util.ArrayList<>();
        for (Integer valor : java.util.List.of(1, -2, 3)) {
            if (valor > 0) {
                resultado.add(valor * 2);
            }
        }
        System.out.println(resultado); // [2, 6]
        // snippet:loop-equivalence:end
        var source = java.util.List.of(1, -2, 3);
        var actual = source.stream().filter(v -> v > 0).map(v -> v * 2).toList();
        if (!resultado.equals(actual) || !source.equals(java.util.List.of(1, -2, 3))) throw new AssertionError("pipeline equivalence");
        var llamadas = new java.util.concurrent.atomic.AtomicInteger();
        var stream = source.stream().filter(v -> { llamadas.incrementAndGet(); return v > 0; });
        if (llamadas.get() != 0 || stream.findFirst().orElseThrow() != 1 || llamadas.get() != 1) throw new AssertionError("lazy short circuit");
        try { actual.add(4); throw new AssertionError("mutable toList"); } catch (UnsupportedOperationException expected) {}
        System.out.println("Streams PASS");
    }
}
