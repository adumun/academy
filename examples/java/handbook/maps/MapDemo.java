import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {
        Map<String, Integer> medidas = new HashMap<>();
        medidas.put("A", 10);
        medidas.put("B", 20);
        System.out.println(medidas.put("A", 15));
        System.out.println(medidas.get("A"));
        System.out.println(medidas.containsKey("C"));
        System.out.println(medidas.size());
    }
}
