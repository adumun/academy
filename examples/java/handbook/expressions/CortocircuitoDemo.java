public class CortocircuitoDemo {

    private static boolean comprobar() {
        System.out.println("Evaluado");
        return true;
    }

    public static void main(String[] args) {
        System.out.println(false && comprobar());
        System.out.println(true || comprobar());
        System.out.println(true && comprobar());
    }
}
