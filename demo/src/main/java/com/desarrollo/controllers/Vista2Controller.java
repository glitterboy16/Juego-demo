package com.desarrollo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;




public class Vista2Controller {

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
    private Label etiqueta;

    @FXML
    private Label defensaprota;

    @FXML
    private TextField defensa;

    @FXML
    private Label fuerzaprota;
    
    @FXML
    private TextField fuerza;

    @FXML
    private Button boton;

    @FXML
    private ImageView imagenfondo;


    @FXML
private void initialize() {
    nombre.setPromptText("Escribe el nombre del personaje");
    salud.setPromptText("Escribe la salud del personaje");
    velocidad.setPromptText("Escribe la velocidad del personaje");
    defensa.setPromptText("Escribe la defensa del personaje");
    fuerza.setPromptText("Escribe la fuerza del personaje");       
    }


   

}
