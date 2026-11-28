import java.sql.Types;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetMetaDataImpl;
import javax.sql.rowset.RowSetProvider;

public class CursorDemo {

    public static void main(String[] args) throws Exception {
        RowSetMetaDataImpl metadatos = new RowSetMetaDataImpl();
        metadatos.setColumnCount(1);
        metadatos.setColumnName(1, "cantidad");
        metadatos.setColumnType(1, Types.INTEGER);

        try (CachedRowSet resultado = RowSetProvider.newFactory().createCachedRowSet()) {
            resultado.setMetaData(metadatos);
            resultado.moveToInsertRow();
            resultado.updateInt(1, 0);
            resultado.insertRow();
            resultado.moveToCurrentRow();
            resultado.beforeFirst();
            System.out.println(resultado.next());
            System.out.println(resultado.getInt(1));
            System.out.println(resultado.wasNull());
            System.out.println(resultado.next());
        }
    }
}
