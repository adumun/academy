
package cl.adumun.academy.timecapsule;

import cl.adumun.academy.timecapsule.model.MensajeFuturo;

public class PruebaMensaje {

    public static void main(String[] args) {

        MensajeFuturo mensaje = new MensajeFuturo(
                "Valentina",
                "Nunca dejes de perseguir tus sueños.",
                5
        );

        System.out.println("Autor: " + mensaje.getAutor());
        System.out.println("Mensaje: " + mensaje.getTexto());
        System.out.println("Leer dentro de: " + mensaje.getPlazoAnios() + " años");
    }
}
