import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class DemoGson {
    public record Medicion(String etiqueta, int valor) {
    }

    private static final Gson GSON = new GsonBuilder()
            .setStrictness(Strictness.STRICT).create();
    private static final Type TIPO = new TypeToken<List<Medicion>>() {}.getType();

    public static String escribir(List<Medicion> datos) {
        return GSON.toJson(datos);
    }

    public static List<Medicion> leer(String texto) {
        return GSON.fromJson(texto, TIPO);
    }

    public static void main(String[] args) {
        String texto = escribir(List.of(new Medicion("A", 21)));
        List<Medicion> recuperadas = leer(texto);
        System.out.println(recuperadas.get(0).valor());
    }
}
