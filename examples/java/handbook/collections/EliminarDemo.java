import java.util.ArrayList;
import java.util.List;

public class EliminarDemo {

    public static void main(String[] args) {
        List<Integer> valores = new ArrayList<>(List.of(1, 8, 1));
        valores.remove(1);
        System.out.println(valores);
        valores.remove(Integer.valueOf(1));
        System.out.println(valores);
    }
}
