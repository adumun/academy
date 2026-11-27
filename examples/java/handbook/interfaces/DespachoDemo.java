public class DespachoDemo {

    interface Describible {
        String describir();
    }

    static class Punto implements Describible {
        @Override
        public String describir() {
            return "Punto";
        }
    }

    static class Linea implements Describible {
        @Override
        public String describir() {
            return "Linea";
        }
    }

    private static void mostrar(Describible elemento) {
        System.out.println(elemento.describir());
    }

    public static void main(String[] args) {
        mostrar(new Punto());
        mostrar(new Linea());
    }
}
