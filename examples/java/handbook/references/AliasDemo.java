public class AliasDemo {

    private static void modificar(StringBuilder texto) {
        texto.append("B");
        texto = new StringBuilder("C");
        texto.append("D");
    }

    public static void main(String[] args) {
        StringBuilder primero = new StringBuilder("A");
        StringBuilder segundo = primero;
        modificar(segundo);
        System.out.println(primero);
        System.out.println(segundo);
        System.out.println(primero == segundo);
    }
}
