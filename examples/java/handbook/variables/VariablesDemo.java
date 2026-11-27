public class VariablesDemo {

    private int total = 0;

    public void sumar(int cantidad) {
        int resultado = total + cantidad;
        total = resultado;
    }

    public static void main(String[] args) {
        VariablesDemo demo = new VariablesDemo();
        demo.sumar(3);
        demo.sumar(2);
        System.out.println(demo.total);
    }
}
