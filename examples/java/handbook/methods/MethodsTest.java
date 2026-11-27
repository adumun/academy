import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class MethodsTest {
    private static int passed;

    private static void equal(String name, Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError(name + ": expected " + expected + ", got " + actual);
        }
        passed++;
        System.out.println("PASS " + name);
    }

    private static String output(Runnable example) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            example.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    public static void main(String[] args) {
        equal("instance sum", 7, new MetodosInstanciaDemo().sumar(3, 4));
        equal("instance zero", 0, new MetodosInstanciaDemo().sumar(0, 0));
        equal("static sum", 7, MetodosDemo.sumar(3, 4));
        equal("static negative", -2, MetodosDemo.sumar(-5, 3));
        equal("int overflow", Integer.MIN_VALUE, MetodosDemo.sumar(Integer.MAX_VALUE, 1));
        SobrecargaDemo first = new SobrecargaDemo();
        SobrecargaDemo second = new SobrecargaDemo();
        equal("initial state", 0, first.consultar());
        first.agregar(2);
        equal("one argument", 2, first.consultar());
        first.agregar(3, 4);
        equal("two arguments", 9, first.consultar());
        equal("independent instance", 0, second.consultar());
        first.agregar(-2);
        equal("negative update", 7, first.consultar());
        equal("published output methods", "7\n", output(() -> MetodosDemo.main(new String[0])));
        equal("published output overload", "9\n", output(() -> SobrecargaDemo.main(new String[0])));
        System.out.println("Methods: " + passed + "/12 PASS");
    }
}
