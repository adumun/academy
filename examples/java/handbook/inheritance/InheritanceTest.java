public class InheritanceTest {
    public static void main(String[] args) {
        HerenciaDemo.Indicador normal = new HerenciaDemo.Indicador();
        HerenciaDemo.Indicador critico = new HerenciaDemo.IndicadorCritico();
        if (!normal.mostrar().equals("Normal") || !critico.mostrar().equals("Critico")) {
            throw new AssertionError("Dynamic dispatch");
        }
        System.out.println("Inheritance PASS");
    }
}
