package cl.adumun.academy.timecapsule.dao;

import cl.adumun.academy.timecapsule.model.MensajeFuturo;
import java.io.IOException;
import java.util.List;

public interface MensajeDao {
    List<MensajeFuturo> listar() throws IOException;
    void guardar(List<MensajeFuturo> mensajes) throws IOException;
}
