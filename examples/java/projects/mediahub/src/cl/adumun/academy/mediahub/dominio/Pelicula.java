package cl.adumun.academy.mediahub.dominio;

public class Pelicula extends Recurso {

    public Pelicula(String codigo, String titulo, String creador) {
        super(codigo, titulo, creador);
    }

    @Override
    public int obtenerDiasPrestamo() {
        return 5;
    }
}
