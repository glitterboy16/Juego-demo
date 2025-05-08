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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
    private Mapa mapa;

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
            // Verificar que el nombre no esté vacío
            if (nombre.getText().isEmpty()) {
                System.out.println("Por favor, introduce un nombre para el personaje.");
                return;
            }
    
            // Verificar que se hayan distribuido todos los puntos
            if (getTotalPuntos() < MAX_PUNTOS) {
                System.out.println("Debes distribuir exactamente " + MAX_PUNTOS + " puntos antes de continuar.");
                return;
            }
    
            // Intentar cargar el mapa
            try {
                // Asegúrate de que la ruta es correcta y accesible
                this.mapa = new Mapa("demo/ficheros/tablero.txt");  // Ruta del archivo del mapa
    
                // Crear el protagonista con los valores obtenidos
                protagonista = new Protagonista(
                    nombre.getText(),
                    salud.getValue(),
                    fuerza.getValue(),
                    defensa.getValue(),
                    velocidad.getValue(),
                    0,
                    0,
                    this.mapa
                );
    
                // Si todo está bien, enviar los datos al TableroController
                TableroController tableroController = (TableroController) SceneManager.getInstance().getController(SceneID.TABLERO);
                tableroController.recibirDatosProtagonista(
                    nombre.getText(),
                    salud.getValue(),
                    fuerza.getValue(),
                    defensa.getValue(),
                    velocidad.getValue(),
                    "/com/desarrollo/imagenes/personaje_abajo.png",  // Ruta de la imagen del personaje
                    0,
                    0
                );
    
                // Eliminar la escena secundaria
                SceneManager.getInstance().removeScene(SceneID.SECONDARY);
                System.out.println("Información del personaje guardada correctamente.");
            } catch (IOException e) {
                // En caso de error al cargar el mapa
                System.out.println("Error al cargar el mapa: " + e.getMessage());
                e.printStackTrace();  // Para depuración
            }
    
        } catch (Exception e) {
            // Cualquier otro error que pueda ocurrir en el proceso
            System.out.println("Ocurrió un error al guardar la información del personaje: " + e.getMessage());
        }
    }
    
    
    public Protagonista getProtagonista() {
        return protagonista;
    }
}
