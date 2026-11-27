class InvalidOverride extends IndicadorBase {
    @Override void mostrar(int valor) {}
}
class IndicadorBase { void mostrar() {} }
