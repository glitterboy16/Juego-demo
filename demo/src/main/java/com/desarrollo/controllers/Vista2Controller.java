package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;

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
        
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/imagenvista2.jpg").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panel.widthProperty());
        imagenfondo.fitHeightProperty().bind(panel.heightProperty());
        imagenfondo.setPreserveRatio(false);

        // Ajustar el ImageView al tamaño del Pane
        imagenfondo.fitWidthProperty().bind(panel.widthProperty());
        imagenfondo.fitHeightProperty().bind(panel.heightProperty());
    
        boton.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.TABLERO);
        });

    } 
}