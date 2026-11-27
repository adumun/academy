public class ObjectsTest {
    private static void check(boolean condition) {
        if (!condition) throw new AssertionError("Object contract");
    }

    private static void reject(String title) {
        try { new Libro(1, title); }
        catch (IllegalArgumentException expected) { return; }
        throw new AssertionError("Invalid title accepted");
    }

    public static void main(String[] args) {
        // snippet:alias:start
        Libro primero = new Libro(1, "Java");
        Libro segundo = primero;
        Libro tercero = new Libro(1, "Java");

        System.out.println(primero == segundo);
        System.out.println(primero == tercero);
        // snippet:alias:end
        check(primero == segundo && primero != tercero);
        check(primero.getId() == 1 && primero.getTitulo().equals("Java"));
        check(new Libro(2, " SQL ").getTitulo().equals("SQL"));
        reject(null);
        reject("");
        reject("   ");
        ContadorValidoDemo a = new ContadorValidoDemo();
        ContadorValidoDemo b = new ContadorValidoDemo();
        check(a.incrementar() && a.obtenerValor() == 1 && b.obtenerValor() == 0);
        check(a.decrementar() && a.obtenerValor() == 0);
        check(!a.decrementar() && a.obtenerValor() == 0);
        System.out.println("Objects PASS");
    }
}
