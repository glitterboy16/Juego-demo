package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Vista1Controller {
    @FXML
    private Pane pane;

    @FXML
    private ImageView fondo1;
   
    @FXML
    private Button iniciar;


    @FXML
    public void initialize() {
        // Cargar la imagen directamente
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/fondo1.jpg").toExternalForm());
    
        // Establecer la imagen al ImageView
        fondo1.setImage(image);

        // Ajustar el ImageView al tamaño del Pane
        fondo1.fitWidthProperty().bind(pane.widthProperty());
        fondo1.fitHeightProperty().bind(pane.heightProperty());

        // Configurar posición y tamaño del botón
        iniciar.setPrefWidth(132.0);
        iniciar.setPrefHeight(25.0);

        // Posición relativa del botón 
        double posXRelativa = 53.0 / 600.0; // 53px / ancho total
        double posYRelativa = 230.0 / 400.0; // 230px / alto total
        
        iniciar.layoutXProperty().bind(pane.widthProperty().multiply(posXRelativa));
        iniciar.layoutYProperty().bind(pane.heightProperty().multiply(posYRelativa));


        iniciar.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.SECONDARY);
        });
    }
}  