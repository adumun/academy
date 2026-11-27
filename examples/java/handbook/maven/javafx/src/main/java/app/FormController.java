package app;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FormController {

    @FXML
    private TextField texto;

    @FXML
    private void aceptar() {
        System.out.println(texto.getText());
    }
}
