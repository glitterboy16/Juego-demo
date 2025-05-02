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

public class TableroController {
    @FXML
    private AnchorPane panelPrincipal;

    private final int FILAS = 15;
    private final int COLUMNAS = 15;
    private char[][] mapa = new char[FILAS][COLUMNAS];
    private GridPane tablero;
    @FXML
    public void initialize() {  // Cambiado de "inicializar" a "initialize" (estándar de JavaFX)
        try {
            // 1. Cargar el archivo (ignorando espacios)
            cargarMapaDesdeArchivo();
            
            // 2. Crear GridPane con bordes visibles (para depuración)
            tablero = new GridPane();
            tablero.setHgap(0);  // No hay espacio horizontal
            tablero.setVgap(0);  // No hay espacio vertical 

            // 3. Cargar imágenes (ruta relativa desde resources)
            Image suelo = new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/S.jpg"));
            Image pared = new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/P.jpg"));

            // Anclar el GridPane a los bordes del AnchorPane
            AnchorPane.setTopAnchor(tablero, 0.0);
            AnchorPane.setBottomAnchor(tablero, 0.0);
            AnchorPane.setLeftAnchor(tablero, 0.0);
            AnchorPane.setRightAnchor(tablero, 0.0);

            // Escuchar cambios de tamaño para redibujar el tablero dinámicamente
            panelPrincipal.widthProperty().addListener((obs, oldVal, newVal) -> actualizarTablero(suelo, pared));
            panelPrincipal.heightProperty().addListener((obs, oldVal, newVal) -> actualizarTablero(suelo, pared));

            // Construcción inicial del tablero
            actualizarTablero(suelo, pared);

            // Añadir al panel principal
            panelPrincipal.getChildren().add(tablero);

            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    
            // 5. Rellenar el GridPane
            /*for (int fila = 0; fila < FILAS; fila++) {
                for (int col = 0; col < COLUMNAS; col++) {
                    ImageView celda = new ImageView();
                    celda.setFitWidth(40);
                    celda.setFitHeight(40);
                    
                    // Asignar imagen según el carácter (ignorando espacios)
                    char tipo = mapa[fila][col];
                    if (tipo == 'S') celda.setImage(suelo);
                    else if (tipo == 'P') celda.setImage(pared);
                    
                    tablero.add(celda, col, fila);
                }
            }

            //Añadir al panel principal
            panelPrincipal.getChildren().add(tablero);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }*/
    

    private void cargarMapaDesdeArchivo() throws IOException {
        String ruta = "ficheros/tablero.txt";  // Ruta relativa desde la raíz del proyecto
        System.out.println("Ruta del archivo: " + new File(ruta).getAbsolutePath());

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int fila = 0;
            while ((linea = br.readLine()) != null && fila < FILAS) {
                // Eliminar espacios y verificar longitud
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
        // Obtiene el tamaño disponible en el AnchorPane
        double anchoDisponible = panelPrincipal.getWidth();
        double altoDisponible = panelPrincipal.getHeight();
        // Calcula el tamaño de las celdas según el espacio disponible
        double anchoCelda = anchoDisponible / COLUMNAS;
        double altoCelda = altoDisponible / FILAS;
        // Crea y añade las celdas al GridPane
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                ImageView celda = new ImageView();
                celda.setFitWidth(anchoCelda);
                celda.setFitHeight(altoCelda);
                // Asigna la imagen de la celda dependiendo del tipo
                char tipo = mapa[fila][col];
                if (tipo == 'S') celda.setImage(suelo);
                else if (tipo == 'P') celda.setImage(pared);
                // Añade la celda al GridPane
                tablero.add(celda, col, fila);
            }
        }
    }
    
}
