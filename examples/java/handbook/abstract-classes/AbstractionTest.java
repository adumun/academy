public class AbstractionTest {
    static class Distancia extends AbstraccionDemo.Medicion {
        Distancia() { super("m"); }
        @Override int valor() { return 5; }
    }
    public static void main(String[] args) {
        AbstraccionDemo.Medicion a = new AbstraccionDemo.Temperatura();
        AbstraccionDemo.Medicion b = new Distancia();
        if (!a.mostrar().equals("21 C") || !b.mostrar().equals("5 m")) {
            throw new AssertionError("Abstract contract");
        }
        System.out.println("Abstraction PASS");
    }
}
