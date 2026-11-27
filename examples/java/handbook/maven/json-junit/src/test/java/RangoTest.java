import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RangoTest {
    @Test
    void rangoValido() {
        assertEquals(5L, Rango.amplitud(3, 8));
    }

    @Test
    void rechazaRangoInvertido() {
        assertThrows(IllegalArgumentException.class, () -> Rango.amplitud(8, 3));
    }
}
