public class ArraysDemo {

    public static void main(String[] args) {
        int[] mediciones = {10, 20, 30};
        int total = 0;

        for (int medicion : mediciones) {
            total += medicion;
        }

        System.out.println(mediciones.length);
        System.out.println(mediciones[1]);
        System.out.println(total);
    }
}
