package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class Vista1Controller {

   
    @FXML
    private Button inicio;


    @FXML
    public void initialize() {
        inicio.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.SECONDARY);
        });
    }
}