import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

public class DemoJackson {
    public record Medicion(String etiqueta, int valor) {
    }

    public static String escribir(List<Medicion> datos) throws Exception {
        return new ObjectMapper().writeValueAsString(datos);
    }

    public static List<Medicion> leer(String texto) throws Exception {
        return new ObjectMapper().readValue(
                texto, new TypeReference<List<Medicion>>() {});
    }

    public static void main(String[] args) throws Exception {
        String texto = escribir(List.of(new Medicion("A", 21)));
        List<Medicion> recuperadas = leer(texto);
        System.out.println(recuperadas.get(0).valor());
    }
}
