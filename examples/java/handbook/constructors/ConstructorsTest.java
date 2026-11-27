public class ConstructorsTest {
    private static void check(boolean condition) {
        if (!condition) throw new AssertionError("Range contract");
    }
    public static void main(String[] args) {
        check(new ConstructorDemo(10).amplitud() == 10L);
        check(new ConstructorDemo(3, 8).amplitud() == 5L);
        check(new ConstructorDemo(-5, -5).amplitud() == 0L);
        check(new ConstructorDemo(Integer.MIN_VALUE, Integer.MAX_VALUE).amplitud() == 4294967295L);
        boolean rejected = false;
        try { new ConstructorDemo(9, 2); } catch (IllegalArgumentException expected) { rejected = true; }
        check(rejected);
        System.out.println("Constructors PASS");
    }
}
