import java.util.List;

public class InterfacesTest {
    static class CatalogoVacio implements Catalogo {
        @Override public List<String> listar() { return List.of(); }
    }
    static class Circulo implements DespachoDemo.Describible {
        @Override public String describir() { return "Circulo"; }
    }
    public static void main(String[] args) {
        Catalogo lleno = new CatalogoMemoria();
        Catalogo vacio = new CatalogoVacio();
        if (!lleno.listar().equals(List.of("Java", "SQL")) || !vacio.listar().isEmpty()) {
            throw new AssertionError("Catalog contract");
        }
        DespachoDemo.Describible[] elementos = {new DespachoDemo.Punto(), new DespachoDemo.Linea(), new Circulo()};
        if (!List.of(elementos[0].describir(), elementos[1].describir(), elementos[2].describir()).equals(List.of("Punto", "Linea", "Circulo"))) {
            throw new AssertionError("Interface dispatch");
        }
        System.out.println("Interfaces PASS");
    }
}
