package com.desarrollo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Vista4Controller {

    @FXML
    private AnchorPane panel;

    @FXML
    private ImageView imagenfondo;

    @FXML
    private Button botonSalir1;

    @FXML
    public void initialize() {
        // Cargar imagen de fondo
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/imagenvista4.jpg").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panel.widthProperty());
        imagenfondo.fitHeightProperty().bind(panel.heightProperty());
        imagenfondo.setPreserveRatio(false); 

        // Centrar el botón horizontalmente
        botonSalir1.layoutXProperty().bind(
        panel.widthProperty().subtract(botonSalir1.widthProperty()).divide(2)
        );

        // Colocar al fondo (por ejemplo, 50 píxeles desde el borde inferior)
        botonSalir1.layoutYProperty().bind(
            panel.heightProperty().subtract(botonSalir1.heightProperty()).subtract(50)
        );
        // Acción del botón "Salir"
        botonSalir1.setOnAction(event -> {
            Stage stage = (Stage) botonSalir1.getScene().getWindow();
            stage.close();
        });
    }
}