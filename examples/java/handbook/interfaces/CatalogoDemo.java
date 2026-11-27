import java.util.List;

interface Catalogo {
    List<String> listar();
}

class CatalogoMemoria implements Catalogo {

    @Override
    public List<String> listar() {
        return List.of("Java", "SQL");
    }
}

public class CatalogoDemo {

    public static void main(String[] args) {
        Catalogo catalogo = new CatalogoMemoria();
        System.out.println(catalogo.listar());
    }
}
