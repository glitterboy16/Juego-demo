package com.desarrollo.controllers;

import com.desarrollo.model.DatosJugador;
import com.desarrollo.model.Protagonista;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class InterfazGrafica {

    @FXML
    AnchorPane panelRaiz;

    @FXML
    GridPane panelGrid;

    @FXML
    VBox estado;

    private Protagonista protagonista;

    public void initialize() {
        protagonista = DatosJugador.getInstance().getProtagonista();
        colocarProtagonistaEnTablero();
    }

    private void colocarProtagonistaEnTablero() {
        int x = protagonista.getPosicionX();
        int y = protagonista.getPosicionY();
        panelGrid.add(protagonista.getImagenPersonaje(), y, x); 
    }
    
}