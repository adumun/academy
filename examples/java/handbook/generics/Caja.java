public class Caja<T> {

    private final T valor;

    public Caja(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public static void main(String[] args) {
        Caja<String> texto = new Caja<>("Java");
        Caja<Integer> numero = new Caja<>(21);

        System.out.println(texto.getValor());
        System.out.println(numero.getValor());
    }
}
