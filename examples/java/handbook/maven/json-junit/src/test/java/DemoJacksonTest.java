import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DemoJacksonTest {
    @Test
    void salidaPublicada() throws Exception {
        assertEquals("21\n", SalidaPrueba.capturar(() -> DemoJackson.main(new String[0])));
    }

    @Test
    void listaTipadaIdaYVuelta() throws Exception {
        var datos = List.of(new DemoJackson.Medicion("A", 21), new DemoJackson.Medicion("B", -3));
        assertEquals(datos, DemoJackson.leer(DemoJackson.escribir(datos)));
    }

    @Test
    void listaVacia() throws Exception {
        assertEquals(List.of(), DemoJackson.leer(DemoJackson.escribir(List.of())));
    }

    @Test
    void campoAusenteNoEsValidacionDeDominio() throws Exception {
        assertEquals(List.of(new DemoJackson.Medicion("A", 0)), DemoJackson.leer("[{\"etiqueta\":\"A\"}]"));
    }

    @Test
    void estructuraIncompatible() {
        assertThrows(com.fasterxml.jackson.core.JsonProcessingException.class, () -> DemoJackson.leer("{\"etiqueta\":\"A\",\"valor\":21}"));
    }

    @Test
    void jsonTruncado() {
        assertThrows(com.fasterxml.jackson.core.JsonProcessingException.class, () -> DemoJackson.leer("[{"));
    }
}
