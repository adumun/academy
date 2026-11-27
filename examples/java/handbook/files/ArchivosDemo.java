import java.nio.file.Files;
import java.nio.file.Path;

public class ArchivosDemo {

    public static void main(String[] args) throws Exception {
        Path carpeta = Files.createTempDirectory("academy-");
        Path archivo = carpeta.resolve("mediciones.txt");
        try {
            Files.writeString(archivo, "21");
            System.out.println(Files.readString(archivo));
        } finally {
            Files.deleteIfExists(archivo);
            Files.deleteIfExists(carpeta);
        }
    }
}
