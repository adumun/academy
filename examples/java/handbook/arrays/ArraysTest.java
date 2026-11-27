public class ArraysTest {

    public static void main(String[] args) {
        // snippet:alias-copy:start
        int[] original = {10, 20, 30};
        int[] alias = original;
        alias[1] = 99;
        System.out.println(original[1]); // 99
        int[] copia = java.util.Arrays.copyOf(original, 5);
        System.out.println(copia.length); // 5
        System.out.println(copia[4]); // 0
        // snippet:alias-copy:end
        if (original == copia || copia[1] != 99 || copia[4] != 0 || original.length != 3) throw new AssertionError("copy contract");
        copia[1] = 7;
        if (original[1] != 99) throw new AssertionError("copy independence");
        try { int x = original[3]; throw new AssertionError("index accepted"); } catch (ArrayIndexOutOfBoundsException expected) {}
        System.out.println("Arrays PASS");
    }
}
