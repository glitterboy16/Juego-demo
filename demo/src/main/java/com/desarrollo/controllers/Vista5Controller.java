package com.desarrollo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controlador para la quinta vista de la aplicación, que gestiona la pantalla de "Ganador".
 * Esta clase se encarga de inicializar la vista, cargar la imagen de fondo y
 * gestionar la acción del botón "Salir".
 * 
 * @author María Teresa Calvo
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @version 1.0
 */
public class Vista5Controller {
    @FXML
    private AnchorPane panel;

    @FXML
    private ImageView imagenfondo;

    @FXML
    private Button botonSalir2;

    /**
     * Inicializa la vista cargando la imagen de fondo y posicionando el botón de salir.
     */
    @FXML
    public void initialize() {
        // Cargar la imagen directamente
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/vista5.jpg").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panel.widthProperty());
        imagenfondo.fitHeightProperty().bind(panel.heightProperty());
        imagenfondo.setPreserveRatio(false);

        // Centrar horizontalmente
        botonSalir2.layoutXProperty().bind(
            panel.widthProperty().subtract(botonSalir2.widthProperty()).divide(2)
        );

        // Colocar al fondo (por ejemplo, 50 píxeles desde el borde inferior)
        botonSalir2.layoutYProperty().bind(
            panel.heightProperty().subtract(botonSalir2.heightProperty()).subtract(20)
        );

        // Acción del botón "Salir"
        botonSalir2.setOnAction(event -> {
            Stage stage = (Stage) botonSalir2.getScene().getWindow();
            stage.close();
        });
    }
}