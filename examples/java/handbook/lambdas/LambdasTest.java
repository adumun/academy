import java.util.function.Predicate;

public class LambdasTest {

    public static void main(String[] args) {
        // snippet:predicate:start
        Predicate<String> noVacio = texto -> !texto.isBlank();
        System.out.println(noVacio.test("Java"));
        // snippet:predicate:end
        if (!noVacio.test("Java") || noVacio.test(" ") || noVacio.test("")) throw new AssertionError("predicate contract");
        System.out.println("Lambdas PASS");
    }
}
