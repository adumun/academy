package cl.adumun.academy.timecapsule.dao;

import cl.adumun.academy.timecapsule.model.MensajeFuturo;
import java.sql.SQLException;
import java.util.List;

public interface MensajeDao {
    List<MensajeFuturo> listar() throws SQLException;
    void registrar(MensajeFuturo mensaje) throws SQLException;
}
