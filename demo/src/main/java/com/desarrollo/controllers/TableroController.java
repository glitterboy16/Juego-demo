package com.desarrollo.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TableroController {

    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private ImageView imagenfondo;

    @FXML
    private ImageView imagenfondoVbox;

    @FXML
    private AnchorPane tableroPanel;

    @FXML
    private VBox datosPersonajesVBox;

    private final int FILAS = 15;
    private final int COLUMNAS = 15;
    private char[][] mapa = new char[FILAS][COLUMNAS];
    private GridPane tablero;

    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/fondos/Fondo_Provicional.png").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panelPrincipal.widthProperty());
        imagenfondo.fitHeightProperty().bind(panelPrincipal.heightProperty());
        imagenfondo.setPreserveRatio(false);

        Image image2 = new Image(getClass().getResource("/com/desarrollo/imagenes/fondos/Fondo_Vbox.png").toExternalForm());
        imagenfondoVbox.setImage(image2);
        imagenfondoVbox.fitWidthProperty().bind(datosPersonajesVBox.widthProperty());
        imagenfondoVbox.fitHeightProperty().bind(datosPersonajesVBox.heightProperty());
        imagenfondoVbox.setPreserveRatio(false);

        try {
            cargarMapaDesdeArchivo();

            tablero = new GridPane();
            tablero.setHgap(0);
            tablero.setVgap(0);

            Image suelo = new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/S.jpg"));
            Image pared = new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/P.jpg"));

            AnchorPane.setTopAnchor(tablero, 0.0);
            AnchorPane.setBottomAnchor(tablero, 0.0);
            AnchorPane.setLeftAnchor(tablero, 0.0);
            AnchorPane.setRightAnchor(tablero, 0.0);

            tableroPanel.widthProperty().addListener((obs, oldVal, newVal) -> actualizarTablero(suelo, pared));
            tableroPanel.heightProperty().addListener((obs, oldVal, newVal) -> actualizarTablero(suelo, pared));

            actualizarTablero(suelo, pared);
            tableroPanel.getChildren().add(tablero);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarMapaDesdeArchivo() throws IOException {
        String ruta = "ficheros/tablero.txt";
        System.out.println("Ruta del archivo: " + new File(ruta).getAbsolutePath());

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int fila = 0;
            while ((linea = br.readLine()) != null && fila < FILAS) {
                String lineaSinEspacios = linea.replaceAll("\\s+", "");
                if (lineaSinEspacios.length() < COLUMNAS) {
                    throw new IOException("La línea " + (fila + 1) + " tiene menos de " + COLUMNAS + " caracteres.");
                }
                for (int col = 0; col < COLUMNAS; col++) {
                    mapa[fila][col] = lineaSinEspacios.charAt(col);
                }
                fila++;
            }
        }
    }

    private void actualizarTablero(Image suelo, Image pared) {
        tablero.getChildren().clear();

// Establece tamaño fijo por celda
double anchoCelda = 35;
double altoCelda = 35;


        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                ImageView celda = new ImageView();
                celda.setFitWidth(anchoCelda);
                celda.setFitHeight(altoCelda);
                char tipo = mapa[fila][col];
                if (tipo == 'S') celda.setImage(suelo);
                else if (tipo == 'P') celda.setImage(pared);
                tablero.add(celda, col, fila);
            }
        }
    }
}
