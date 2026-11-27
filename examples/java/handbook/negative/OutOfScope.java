class OutOfScope {
    void run() {
        if (true) {
            int incremento = 2;
        }
        System.out.println(incremento);
    }
}
