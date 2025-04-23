package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
import com.desarrollo.model.Model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Formulario1Controller {

    @FXML
    private TextField nombre;

    @FXML
    private TextField edad;

    @FXML
    private TextField colorpelo;

    @FXML
    private Label etiqueta;
    
    @FXML
    private Button boton;

    private Model model;

    @FXML
private void initialize() {
        boton.setOnAction(event -> {
            try {
                String nombreValue = nombre.getText();
                int edadValue = Integer.parseInt(edad.getText());
                String colorPeloValue = colorpelo.getText();

                Model.getInstance().setPersona(nombreValue, edadValue, colorPeloValue);
                SceneManager.getInstance().loadScene(SceneID.SECONDARY);
            } catch (NumberFormatException e) {
                etiqueta.setText("Por favor, introduce una edad válida.");
            }
    });
        etiqueta.setText("Rellena el formulario y pulsa 'Enviar' cuando lo tengas");
        nombre.setPromptText("Introduce el nombre");
        edad.setPromptText("Introduce la edad");
        colorpelo.setPromptText("Intruduce el color de pelo");
        boton.setText("Enviar");
    }
}
