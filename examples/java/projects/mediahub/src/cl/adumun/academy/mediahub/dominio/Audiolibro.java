package cl.adumun.academy.mediahub.dominio;

public class Audiolibro extends Recurso implements Descargable {

    public Audiolibro(String codigo, String titulo, String creador) {
        super(codigo, titulo, creador);
    }

    @Override
    public int obtenerDiasPrestamo() {
        return 14;
    }

    @Override
    public void descargar() {
        System.out.println("Descargando audiolibro: " + getTitulo());
    }
}
