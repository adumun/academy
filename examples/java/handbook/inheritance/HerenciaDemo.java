public class HerenciaDemo {

    static class Indicador {
        String mostrar() {
            return "Normal";
        }
    }

    static class IndicadorCritico extends Indicador {
        @Override
        String mostrar() {
            return "Critico";
        }
    }

    public static void main(String[] args) {
        Indicador primero = new Indicador();
        Indicador segundo = new IndicadorCritico();
        System.out.println(primero.mostrar());
        System.out.println(segundo.mostrar());
    }
}
