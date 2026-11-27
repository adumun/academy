public class ReferencesFragmentsTest {
    static void example0() {
        // snippet:alias:start
        StringBuilder primero = new StringBuilder("Java");
        StringBuilder segundo = primero;
        segundo.append("FX");

        System.out.println(primero);
        // snippet:alias:end
        if (primero != segundo || !primero.toString().equals("JavaFX")) throw new AssertionError("alias");
    }

    public static void main(String[] args) {
        example0();
        System.out.println("References fragments PASS");
    }
}
