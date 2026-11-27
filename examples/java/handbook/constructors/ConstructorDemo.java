public class ConstructorDemo {

    private final int minimo;
    private final int maximo;

    public ConstructorDemo(int maximo) {
        this(0, maximo);
    }

    public ConstructorDemo(int minimo, int maximo) {
        if (minimo > maximo) {
            throw new IllegalArgumentException("Rango invalido");
        }

        this.minimo = minimo;
        this.maximo = maximo;
    }

    public long amplitud() {
        return (long) maximo - minimo;
    }

    public static void main(String[] args) {
        ConstructorDemo primero = new ConstructorDemo(10);
        ConstructorDemo segundo = new ConstructorDemo(3, 8);
        System.out.println(primero.amplitud());
        System.out.println(segundo.amplitud());

        try {
            new ConstructorDemo(9, 2);
        } catch (IllegalArgumentException exception) {
            System.out.println("Rechazado");
        }
    }
}
