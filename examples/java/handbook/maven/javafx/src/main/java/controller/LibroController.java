package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LibroController {

    @FXML
    private TextField txtTitulo;

    @FXML
    private Label lblResultado;

    @FXML
    private void initialize() {
        lblResultado.setText("Esperando entrada");
    }

    @FXML
    private void mostrar() {
        lblResultado.setText(txtTitulo.getText());
    }
}
