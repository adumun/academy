public final class DaoProcesoDemo {
    private DaoProcesoDemo() {}

    public static void main(String[] args) throws Exception {
        LibroDao dao = new LibroDaoMySql();
        if (args.length == 2 && args[0].equals("insertar")) {
            System.out.println(dao.insertar(new Libro(0, args[1])));
        } else if (args.length == 1 && args[0].equals("listar")) {
            for (Libro libro : dao.listarTodos()) {
                System.out.println(libro.getId() + "\t" + libro.getTitulo());
            }
        } else {
            throw new IllegalArgumentException("Uso: insertar titulo | listar");
        }
    }
}
