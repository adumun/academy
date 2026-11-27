import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

final class SalidaPrueba {
    interface Accion { void ejecutar() throws Exception; }
    static String capturar(Accion accion) throws Exception {
        var original = System.out;
        var buffer = new ByteArrayOutputStream();
        try (var stream = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(stream);
            accion.ejecutar();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
