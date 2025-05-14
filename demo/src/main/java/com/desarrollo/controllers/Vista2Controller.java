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

/**
 * Controlador de la vista de creación del protagonista del juego.
 * Permite al usuario configurar atributos como salud, fuerza, defensa y velocidad,
 * asegurando que el total no supere los 100 puntos.
 * También permite guardar los datos e inicializar al protagonista.
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0
 */

public class Vista2Controller implements Initializable {
    
    @FXML
    private AnchorPane panel;
    /** Panel principal que contiene los elementos de la interfaz. */
    @FXML
    private ImageView imagenfondo;
    /** Imagen de fondo de la pantalla de registro del jugador. */
    @FXML
    private TextField nombre;
    /*Campo a rellenar al crear el personaje */
    /*Al configurar estas barrar la puntuación entre todas será de 100. */
    @FXML
    private Slider saludSlider;
    /*Barra para elegir los puntos de salud que tendrá el personaje */
    @FXML
    private Slider velocidadSlider;
    /*Barra para elegir los puntos de salud que tendrá el personaje */
    @FXML
    private Slider fuerzaSlider;
    /*Barra para elegir los puntos de salud que tendrá el personaje */
    @FXML
    private Slider defensaSlider;
    /*Barra para elegir los puntos de salud que tendrá el personaje */
    @FXML
    private Label saludValueLabel;
    /*Barra para elegir los puntose de salud que tendrá el personaje */
    @FXML
    private Label velocidadValueLabel;
    /*Barra para elegir los puntos de salud que tendrá el personaje */
    @FXML
    private Label fuerzaValueLabel;
    /*Barra para elegir los puntos de salud que tendrá el personaje */
    @FXML
    private Label defensaValueLabel;
    /*Barra para elegir los puntos de salud que tendrá el personaje */
    @FXML
    private Button botonGuardar;
    /*Botón para guardar los datos del personaje creado. */
    @FXML
    private Button botonContinuar;
    /*Botón que nos envía a la vista donde jugaremos. */
    @FXML
    private Label puntosRestantesLabel;
    /*Este parámetro nos dirá los puntos restantes que faltan por asignar al jugador. */
    private final int MAX_PUNTOS = 100;
    private Protagonista protagonista;
    private Mapa mapa;

    /**
     * Inicializa los componentes de la vista.
     * Carga la imagen de fondo, configura los sliders y establece los manejadores de eventos.
     *
     * @param location  La ubicación del archivo FXML.
     * @param resources Recursos del archivo FXML.
     */

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

    /**
     * Configura los sliders con sus valores mínimos y etiquetas asociadas.
     */
    private void configurarSliders() {
        configurarSlider(saludSlider, saludValueLabel, 10);
        configurarSlider(velocidadSlider, velocidadValueLabel, 5);
        configurarSlider(fuerzaSlider, fuerzaValueLabel, 5);
        configurarSlider(defensaSlider, defensaValueLabel, 5);
    }

    /**
     * Configura un slider individualmente para ajustar sus valores y actualiza su etiqueta.
     *
     * @param slider   Slider a configurar.
     * @param valueLabel Etiqueta que muestra el valor actual.
     * @param minValue Valor mínimo permitido.
     */
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
    
    /**
     * Calcula la suma total de puntos distribuidos entre los atributos.
     *
     * @return Total de puntos usados.
     */
    private int getTotalPuntos() {
        return (int) saludSlider.getValue() + (int) velocidadSlider.getValue() +
               (int) fuerzaSlider.getValue() + (int) defensaSlider.getValue();
    }

    /**
     * Actualiza la etiqueta que indica los puntos restantes.
     */
    private void actualizarLabel() {
        int puntosRestantes = MAX_PUNTOS - getTotalPuntos();
        if (puntosRestantesLabel != null) {
            puntosRestantesLabel.setText("Puntos restantes: " + puntosRestantes);
        }
    }

    /**
     * Valida los datos introducidos por el usuario y guarda la información del protagonista.
     * Crea una instancia de {@link Protagonista} y la pasa al controlador del tablero.
     */
    /**
     * Guarda la información del personaje principal ingresada por el usuario.
     * 
     * Este método valida que el nombre del personaje no esté vacío y que la suma total de atributos
     * (salud, velocidad, fuerza y defensa) sea exactamente igual al máximo permitido (100 puntos).
     * 
     * Si las validaciones son exitosas:
     * - Crea un nuevo objeto `Mapa` a partir de un archivo de texto.
     * - Crea una instancia de `Protagonista` con los datos proporcionados.
     * - Envía los datos al `TableroController` para preparar la siguiente escena del juego.
     * - Elimina la escena secundaria para liberar memoria.
     * 
     * En caso de error (nombre vacío, puntos mal distribuidos o problemas al cargar el mapa),
     * se muestra un mensaje por consola.
     */
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

    /**
     * Devuelve el protagonista creado.
     *
     * @return Instancia del protagonista configurado por el usuario.
     */
    public Protagonista getProtagonista() {
        return protagonista;
    }
}