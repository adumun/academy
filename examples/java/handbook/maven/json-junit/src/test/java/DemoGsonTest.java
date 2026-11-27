import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DemoGsonTest {
    @Test
    void salidaPublicada() throws Exception {
        assertEquals("21\n", SalidaPrueba.capturar(() -> DemoGson.main(new String[0])));
    }

    @Test
    void listaTipadaIdaYVuelta() throws Exception {
        var datos = List.of(new DemoGson.Medicion("A", 21), new DemoGson.Medicion("B", -3));
        assertEquals(datos, DemoGson.leer(DemoGson.escribir(datos)));
    }

    @Test
    void listaVacia() throws Exception {
        assertEquals(List.of(), DemoGson.leer(DemoGson.escribir(List.of())));
    }

    @Test
    void campoAusenteNoEsValidacionDeDominio() throws Exception {
        assertEquals(List.of(new DemoGson.Medicion("A", 0)), DemoGson.leer("[{\"etiqueta\":\"A\"}]"));
    }

    @Test
    void estructuraIncompatible() {
        assertThrows(com.google.gson.JsonSyntaxException.class, () -> DemoGson.leer("{\"etiqueta\":\"A\",\"valor\":21}"));
    }

    @Test
    void jsonTruncado() {
        assertThrows(com.google.gson.JsonSyntaxException.class, () -> DemoGson.leer("[{"));
    }
}
