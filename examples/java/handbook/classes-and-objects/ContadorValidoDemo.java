public class ContadorValidoDemo {

    private int valor;

    public boolean decrementar() {
        if (valor == 0) {
            return false;
        }
        valor--;
        return true;
    }

    public boolean incrementar() {
        if (valor == Integer.MAX_VALUE) {
            return false;
        }
        valor++;
        return true;
    }

    public int obtenerValor() {
        return valor;
    }

    public static void main(String[] args) {
        ContadorValidoDemo primero = new ContadorValidoDemo();
        ContadorValidoDemo segundo = new ContadorValidoDemo();
        primero.incrementar();
        System.out.println(primero.obtenerValor());
        System.out.println(segundo.obtenerValor());
        primero.decrementar();
        System.out.println(primero.decrementar());
        System.out.println(primero.obtenerValor());
    }
}
