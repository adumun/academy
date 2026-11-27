public class DatosPropios {
    // snippet:snapshot:start
    // Fragmento dentro de una clase que administra datos propios.
    private final java.util.List<String> datos = new java.util.ArrayList<>();

    public java.util.List<String> consultarDatos() {
        return java.util.List.copyOf(datos);
    }
    // snippet:snapshot:end

    public void agregar(String dato) {
        datos.add(java.util.Objects.requireNonNull(dato));
    }
}
