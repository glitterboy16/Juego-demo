package com.desarrollo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
import com.desarrollo.model.Mapa;
import com.desarrollo.model.Protagonista;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Vista2Controller implements Initializable {

    @FXML
    private AnchorPane panel;

    @FXML
    private ImageView imagenfondo;

    @FXML
    private TextField nombre;

    @FXML
    private Slider saludSlider;

    @FXML
    private Slider velocidadSlider;

    @FXML
    private Slider fuerzaSlider;

    @FXML
    private Slider defensaSlider;

    @FXML
    private Label saludValueLabel;

    @FXML
    private Label velocidadValueLabel;

    @FXML
    private Label fuerzaValueLabel;

    @FXML
    private Label defensaValueLabel;

    @FXML
    private Button botonGuardar;

    @FXML
    private Button botonContinuar;

    @FXML
    private Label puntosRestantesLabel;

    private final int MAX_PUNTOS = 100;
    private Protagonista protagonista;
    private Mapa mapa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/imagenvista2.jpg").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panel.widthProperty());
        imagenfondo.fitHeightProperty().bind(panel.heightProperty());
        imagenfondo.setPreserveRatio(false);

        configurarSliders();
        actualizarLabel();

        botonGuardar.setOnAction(event -> guardarInformacionPersonaje());
        botonContinuar.setOnAction(event -> {
            if (protagonista != null) {
                SceneManager.getInstance().loadScene(SceneID.TABLERO);
            } else {
                System.out.println("Primero debes guardar la información del personaje.");
            }
        });
    }

    private void configurarSliders() {
        configurarSlider(saludSlider, saludValueLabel, 10);
        configurarSlider(velocidadSlider, velocidadValueLabel, 5);
        configurarSlider(fuerzaSlider, fuerzaValueLabel, 5);
        configurarSlider(defensaSlider, defensaValueLabel, 5);
    }

    private void configurarSlider(Slider slider, Label valueLabel, int minValue) {
        slider.setOnMouseReleased(event -> ajustarValorSlider(slider, valueLabel, minValue));
        valueLabel.setText(String.valueOf((int) slider.getValue())); // Valor inicial
    }

    private void ajustarValorSlider(Slider slider, Label valueLabel, int minValue) {
        int value = (int) slider.getValue();
        int total = getTotalPuntos();

        if (total > MAX_PUNTOS) {
            int exceso = total - MAX_PUNTOS;
            int nuevoValor = Math.max(value - exceso, minValue);
            slider.setValue(nuevoValor);
            value = nuevoValor;
        }

        valueLabel.setText(String.valueOf(value));
        actualizarLabel();
    }

    private int getTotalPuntos() {
        return (int) saludSlider.getValue() + (int) velocidadSlider.getValue() +
               (int) fuerzaSlider.getValue() + (int) defensaSlider.getValue();
    }

    private void actualizarLabel() {
        int puntosRestantes = MAX_PUNTOS - getTotalPuntos();
        if (puntosRestantesLabel != null) {
            puntosRestantesLabel.setText("Puntos restantes: " + puntosRestantes);
        }
    }

    private void guardarInformacionPersonaje() {
        try {
            if (nombre.getText().isEmpty()) {
                System.out.println("Por favor, introduce un nombre para el personaje.");
                return;
            }

            int saludValue = (int) saludSlider.getValue();
            int velocidadValue = (int) velocidadSlider.getValue();
            int fuerzaValue = (int) fuerzaSlider.getValue();
            int defensaValue = (int) defensaSlider.getValue();

            int total = saludValue + velocidadValue + fuerzaValue + defensaValue;
            if (total != MAX_PUNTOS) {
                System.out.println("Debes distribuir exactamente " + MAX_PUNTOS + " puntos.");
                return;
            }

            try {
                this.mapa = new Mapa("demo/ficheros/tablero.txt");
                protagonista = new Protagonista(
                    nombre.getText(),
                    saludValue,
                    fuerzaValue,
                    defensaValue,
                    velocidadValue,
                    1,
                    1,
                    this.mapa
                );

                TableroController tableroController = (TableroController) SceneManager.getInstance().getController(SceneID.TABLERO);
                tableroController.recibirDatosProtagonista(
                    nombre.getText(),
                    saludValue,
                    fuerzaValue,
                    defensaValue,
                    velocidadValue,
                    "/com/desarrollo/imagenes/personaje_abajo.png",
                    1,
                    1
                );

                SceneManager.getInstance().removeScene(SceneID.SECONDARY);
                System.out.println("Información del personaje guardada correctamente.");
            } catch (IOException e) {
                System.out.println("Error al cargar el mapa: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al guardar la información del personaje: " + e.getMessage());
        }
    }

    public Protagonista getProtagonista() {
        return protagonista;
    }
}