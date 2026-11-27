public class AbstraccionDemo {

    abstract static class Medicion {
        private final String unidad;

        Medicion(String unidad) {
            this.unidad = unidad;
        }

        abstract int valor();

        String mostrar() {
            return valor() + " " + unidad;
        }
    }

    static class Temperatura extends Medicion {
        Temperatura() {
            super("C");
        }

        @Override
        int valor() {
            return 21;
        }
    }

    public static void main(String[] args) {
        Medicion medicion = new Temperatura();
        System.out.println(medicion.mostrar());
    }
}
