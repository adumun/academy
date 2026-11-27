public final class Rango {
    private Rango() {
    }

    public static long amplitud(int minimo, int maximo) {
        if (minimo > maximo) {
            throw new IllegalArgumentException("Rango invalido");
        }
        return (long) maximo - minimo;
    }
}
