class PrivateFieldAccess {
    void run(LibroPrivado libro) { System.out.println(libro.titulo); }
}
class LibroPrivado { private String titulo; }
