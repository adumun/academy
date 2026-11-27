import java.util.HashSet;
import java.util.Set;

public class SetDemo {

    public static void main(String[] args) {
        Set<String> etiquetas = new HashSet<>();
        System.out.println(etiquetas.add("rojo"));
        System.out.println(etiquetas.add("rojo"));
        etiquetas.add("azul");
        System.out.println(etiquetas.contains("azul"));
        System.out.println(etiquetas.size());
    }
}
