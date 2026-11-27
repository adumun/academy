import java.time.LocalDate;

public class FechasDemo {

    public static void main(String[] args) {
        LocalDate fecha = LocalDate.of(2026, 11, 25);
        LocalDate siguiente = fecha.plusDays(1);

        System.out.println(fecha);
        System.out.println(siguiente);
        System.out.println(fecha.isBefore(siguiente));
    }
}
