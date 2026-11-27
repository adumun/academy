import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class RecursosDemo {

    public static void main(String[] args) throws IOException {
        try (BufferedReader lector =
                     new BufferedReader(new StringReader("Java"))) {
            System.out.println(lector.readLine());
        }
    }
}
