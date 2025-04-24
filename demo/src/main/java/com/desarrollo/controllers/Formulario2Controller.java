package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
import com.desarrollo.interfaces.Observer;
import com.desarrollo.model.Model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Formulario2Controller implements Observer {

   
    @FXML
    private Button inicio;


    @FXML
    public void initialize() {
        inicio.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.SECONDARY);
        });
    }
}