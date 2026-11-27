public class ComposicionDemo {

    static class Punto {
        private final int coordenadaX;
        private final int coordenadaY;

        Punto(int coordenadaX, int coordenadaY) {
            this.coordenadaX = coordenadaX;
            this.coordenadaY = coordenadaY;
        }

        String descripcion() {
            return coordenadaX + "," + coordenadaY;
        }
    }

    static class Circulo {
        private final Punto centro;

        Circulo(Punto centro) {
            if (centro == null) {
                throw new IllegalArgumentException("Centro obligatorio");
            }
            this.centro = centro;
        }

        String descripcion() {
            return "Centro: " + centro.descripcion();
        }
    }

    public static void main(String[] args) {
        Punto origen = new Punto(0, 0);
        Circulo circulo = new Circulo(origen);
        System.out.println(circulo.descripcion());
    }
}
