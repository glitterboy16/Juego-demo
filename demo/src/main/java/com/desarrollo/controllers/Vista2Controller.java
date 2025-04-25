package com.desarrollo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;




public class Vista2Controller {

    @FXML
    private AnchorPane panel;

    @FXML
    private ImageView imagenfondo;

    @FXML
    private Label info;
    
    @FXML
    private Label nomprota;

    @FXML
    private TextField nombre;

    @FXML 
    private Label saludprota;

    @FXML
    private TextField salud;

    @FXML
    private Label velocidadprota;

    @FXML
    private TextField velocidad;

    @FXML
    private Label fuerzaprota;
    
    @FXML
    private TextField fuerza;

    @FXML
    private Label defensaprota;

    @FXML
    private TextField defensa;

    @FXML
    private Button boton;

    
    @FXML
    private void initialize() {
        

    // Cargar la imagen directamente
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/imagenvista2.jpg").toExternalForm());
    
    // Establecer la imagen al ImageView
        imagenfondo.setImage(image);

    // Enlazar el tamaño del fondo al AnchorPane
    imagenfondo.fitWidthProperty().bind(panel.widthProperty());
    imagenfondo.fitHeightProperty().bind(panel.heightProperty());
    imagenfondo.setPreserveRatio(false); // o true si prefieres mantener proporción

    // Fijar el botón abajo a la derecha (20px de margen)
    panel.widthProperty().addListener((obs, oldWidth, newWidth) -> {
        boton.setLayoutX(newWidth.doubleValue() - boton.getWidth() - 20);
    });

    panel.heightProperty().addListener((obs, oldHeight, newHeight) -> {
        boton.setLayoutY(newHeight.doubleValue() - boton.getHeight() - 20);
    });

    } 
}