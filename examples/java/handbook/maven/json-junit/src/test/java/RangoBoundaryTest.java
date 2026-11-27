import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RangoBoundaryTest {
    @Test
    void limitesIguales() {
        assertEquals(0L, Rango.amplitud(-4, -4));
    }

    @Test
    void rangoCompletoInt() {
        assertEquals(4294967295L, Rango.amplitud(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }
}
