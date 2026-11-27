public class BusquedaDemo {

    private static int buscarPrimeroPositivo(int[] valores) {
        for (int indice = 0; indice < valores.length; indice++) {
            if (valores[indice] > 0) {
                return indice;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(buscarPrimeroPositivo(new int[] {-2, 0, 4, 8}));
        System.out.println(buscarPrimeroPositivo(new int[] {-2, 0}));
        System.out.println(buscarPrimeroPositivo(new int[] {}));
    }
}
