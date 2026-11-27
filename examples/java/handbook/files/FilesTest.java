import java.nio.file.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FilesTest {
    private static void mostrarRuta() {
        // snippet:path:start
        java.nio.file.Path archivo = java.nio.file.Path.of("data", "mediciones.txt");
        System.out.println(archivo.toAbsolutePath().normalize());
        System.out.println(java.nio.file.Files.isRegularFile(archivo));
        // snippet:path:end
    }
    public static void main(String[] args) throws IOException {
        Path esperada = Path.of("data", "mediciones.txt");
        var buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (var captura = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(captura);
            mostrarRuta();
        } finally { System.setOut(original); }
        String salida = buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n").trimEnd();
        if (!salida.equals(esperada.toAbsolutePath().normalize() + "\n" + Files.isRegularFile(esperada))) throw new AssertionError("path display");
        Path dir = Files.createTempDirectory("academy-files-");
        Path file = dir.resolve("data.txt");
        try {
            if (Files.isRegularFile(file)) throw new AssertionError("absent file");
            Files.writeString(file, "21", StandardCharsets.UTF_8);
            if (!Files.isRegularFile(file) || !Files.readString(file, StandardCharsets.UTF_8).equals("21")) throw new AssertionError("file content");
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(dir);
        }
        if (Files.exists(dir)) throw new AssertionError("cleanup");
        System.out.println("Files PASS");
    }
}
