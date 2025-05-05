package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
import com.desarrollo.model.Protagonista;
import com.desarrollo.model.Proveedor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Vista2Controller implements Initializable {

    @FXML
    private AnchorPane panel;

    @FXML
    private ImageView imagenfondo;

    @FXML
    private TextField nombre;

    @FXML
    private Spinner<Integer> salud;

    @FXML
    private Spinner<Integer> velocidad;

    @FXML
    private Spinner<Integer> fuerza;

    @FXML
    private Spinner<Integer> defensa;

    @FXML
    private Button botonGuardar;

    @FXML
    private Button botonContinuar;

    @FXML
    private Label puntosRestantesLabel;

    private final int MAX_PUNTOS = 100;

    private Protagonista protagonista;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/imagenvista2.jpg").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panel.widthProperty());
        imagenfondo.fitHeightProperty().bind(panel.heightProperty());
        imagenfondo.setPreserveRatio(false);

        inicializarSpinners();
        actualizarLabel();
        addListeners();

        botonGuardar.setOnAction(event -> guardarInformacionPersonaje());

        botonContinuar.setOnAction(event -> {
            if (protagonista != null) {
                SceneManager.getInstance().loadScene(SceneID.TABLERO);
            } else {
                System.out.println("Primero debes guardar la información del personaje.");
            }
        });
    }

    private void inicializarSpinners() {
        configurarSpinner(salud);
        configurarSpinner(velocidad);
        configurarSpinner(fuerza);
        configurarSpinner(defensa);
    }

    private void configurarSpinner(Spinner<Integer> spinner) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, MAX_PUNTOS, 0));
        spinner.setEditable(true);
    }

    private void addListeners() {
        addListener(salud);
        addListener(velocidad);
        addListener(fuerza);
        addListener(defensa);
    }

    private void addListener(Spinner<Integer> spinner) {
        spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            int total = getTotalPuntos();
            if (total > MAX_PUNTOS) {
                spinner.getValueFactory().setValue(oldValue);
            }
            actualizarLabel();
        });
    }

    private int getTotalPuntos() {
        return salud.getValue() + velocidad.getValue() + fuerza.getValue() + defensa.getValue();
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

            if (getTotalPuntos() < MAX_PUNTOS) {
                System.out.println("Debes distribuir exactamente " + MAX_PUNTOS + " puntos antes de continuar.");
                return;
            }

            String nombreProta = nombre.getText();
            int saludProta = salud.getValue();
            int velocidadProta = velocidad.getValue();
            int fuerzaProta = fuerza.getValue();
            int defensaProta = defensa.getValue();

            protagonista = new Protagonista(nombreProta, saludProta, fuerzaProta, defensaProta, velocidadProta, "/com/desarrollo/imagenes/personaje_abajo.png");
            Proveedor.getInstance().setProtagonista(protagonista);

            System.out.println("Información del personaje guardada correctamente.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al guardar la información del personaje: " + e.getMessage());
        }
    }

    public Protagonista getProtagonista() {
        return protagonista;
    }
}
