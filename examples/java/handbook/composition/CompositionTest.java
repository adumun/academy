public class CompositionTest {
    private static void check(boolean condition) {
        if (!condition) throw new AssertionError("Composition contract");
    }
    public static void main(String[] args) {
        var centro = new ComposicionDemo.Punto(2, -3);
        var a = new ComposicionDemo.Circulo(centro);
        var b = new ComposicionDemo.Circulo(centro);
        check(a.descripcion().equals("Centro: 2,-3"));
        check(b.descripcion().equals(a.descripcion()));
        boolean rejected = false;
        try { new ComposicionDemo.Circulo(null); } catch (IllegalArgumentException expected) { rejected = true; }
        check(rejected);
        DatosPropios owner = new DatosPropios();
        owner.agregar("Java");
        var snapshot = owner.consultarDatos();
        rejected = false;
        try { snapshot.add("SQL"); } catch (UnsupportedOperationException expected) { rejected = true; }
        check(rejected);
        owner.agregar("SQL");
        check(snapshot.equals(java.util.List.of("Java")));
        check(owner.consultarDatos().equals(java.util.List.of("Java", "SQL")));
        System.out.println("Composition PASS");
    }
}
