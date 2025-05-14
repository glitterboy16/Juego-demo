package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


/**
 * Controlador para la primera vista del juego, que muestra una pantalla inicial
 * con una imagen de fondo y un botón para iniciar el juego.
 * Gestiona la inicialización de la interfaz gráfica y la transición a la siguiente escena.
 *
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0
 */
public class Vista1Controller {
    /** Panel principal que contiene los elementos de la interfaz. */
    @FXML
    private Pane pane;
    /** Imagen de fondo de la pantalla inicial. */
    @FXML
    private ImageView fondo1;
   /** Botón para iniciar el juego y pasar a la siguiente escena. */
    @FXML
    private Button iniciar;

    /**
     * Inicializa la interfaz gráfica de la vista, configurando la imagen de fondo,
     * ajustando su tamaño al panel, y posicionando el botón de inicio de manera
     * relativa al tamaño del panel. También configura el evento para cambiar de escena
     * al hacer clic en el botón.
     */
    @FXML
    public void initialize() {
        // Cargar la imagen directamente
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/fondo1.jpg").toExternalForm());
    
        // Establecer la imagen al ImageView
        fondo1.setImage(image);

        // Ajustar el ImageView al tamaño del Pane
        fondo1.fitWidthProperty().bind(pane.widthProperty());
        fondo1.fitHeightProperty().bind(pane.heightProperty());

        // Configurar posición y tamaño del botón
        iniciar.setPrefWidth(132.0);
        iniciar.setPrefHeight(25.0);


        // Configurar el evento del botón para cambiar a la escena secundaria
        iniciar.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.SECONDARY);
        });
    }
}  