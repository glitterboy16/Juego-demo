package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;


public class Vista1Controller {

    @FXML
    private Pane fondo;
   
    @FXML
    private Button inicio;


    @FXML
    public void initialize() {
        // Cargar la imagen
        Image imagen = new Image(getClass().getResource("/imagenes/fondo1.jpg").toExternalForm());

        // Configurar la imagen de fondo
        BackgroundImage bgImage = new BackgroundImage(
            imagen,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(100, 100, true, true, true, false)
        );

        fondo.setBackground(new Background(bgImage));
        

        inicio.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.SECONDARY);
        });
    }
}