public class PropagacionDemo {

    private static int convertir(String texto) {
        return Integer.parseInt(texto);
    }

    public static void main(String[] args) {
        try {
            System.out.println(convertir("12"));
            System.out.println(convertir("abc"));
            System.out.println("No se alcanza");
        } catch (NumberFormatException exception) {
            System.out.println("Formato invalido");
        } finally {
            System.out.println("Fin");
        }
    }
}
