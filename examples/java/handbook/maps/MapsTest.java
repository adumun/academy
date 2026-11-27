public class MapsTest {

    public static void main(String[] args) {
        // snippet:entries:start
        java.util.Map<String, Integer> medidas = new java.util.LinkedHashMap<>();
        medidas.put("A", 10);
        medidas.put("B", 10);
        for (java.util.Map.Entry<String, Integer> entrada : medidas.entrySet()) {
            System.out.println(entrada.getKey() + ": " + entrada.getValue());
        }
        // snippet:entries:end
        if (medidas.put("A", 15) != 10 || medidas.size() != 2 || medidas.containsKey("C")) throw new AssertionError("map contract");
        medidas.put("N", null);
        if (!medidas.containsKey("N") || medidas.get("N") != null || medidas.containsKey("X")) throw new AssertionError("absence vs null");
        System.out.println("Maps PASS");
    }
}
