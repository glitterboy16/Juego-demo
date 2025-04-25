package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Vista4Controller {
    
    @FXML
    private AnchorPane panel;

    @FXML
    private ImageView imagenfondo;

    @FXML
    private Label info;

    @FXML
    private Button botonsi;

    @FXML
    private Button botonno;

    @FXML
    public void initialize(){
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/imagenvista4.jpg").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panel.widthProperty());
        imagenfondo.fitHeightProperty().bind(panel.heightProperty());
        imagenfondo.setPreserveRatio(false);

        // Ajustar el ImageView al tamaño del Pane
        imagenfondo.fitWidthProperty().bind(panel.widthProperty());
        imagenfondo.fitHeightProperty().bind(panel.heightProperty());

        botonsi.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.SECONDARY);
        });
    }
}
