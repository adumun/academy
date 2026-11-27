public class SobrecargaDemo {

    private int total = 0;

    public void agregar(int cantidad) {
        total += cantidad;
    }

    public void agregar(int primera, int segunda) {
        agregar(primera + segunda);
    }

    public int consultar() {
        return total;
    }

    public static void main(String[] args) {
        SobrecargaDemo acumulador = new SobrecargaDemo();
        acumulador.agregar(2);
        acumulador.agregar(3, 4);
        System.out.println(acumulador.consultar());
    }
}
