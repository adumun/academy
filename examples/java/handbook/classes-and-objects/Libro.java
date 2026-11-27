public class Libro {

    private final int id;
    private final String titulo;

    public Libro(int id, String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título obligatorio");
        }

        this.id = id;
        this.titulo = titulo.strip();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public static void main(String[] args) {
        Libro primero = new Libro(1, "Java");
        Libro segundo = new Libro(2, "SQL");

        System.out.println(primero.getTitulo());
        System.out.println(segundo.getTitulo());
    }
}
