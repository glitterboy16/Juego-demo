package com.desarrollo.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.desarrollo.model.Protagonista;
import com.desarrollo.interfaces.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class TableroController implements Observer {

    @FXML
    private AnchorPane panelPrincipal;

    @FXML
    private ImageView imagenfondo;

    @FXML
    private ImageView imagenfondoStackPane;

    @FXML
    private AnchorPane tableroPanel;

    @FXML
    private ImageView tableroPanel2;

    // Datos y Estadísticas de personajes
    @FXML
    private StackPane datosStackPane;

    @FXML
    private Label Pnombre;

    @FXML
    private Label Psalud;

    @FXML
    private Label Pfuerza;

    @FXML
    private Label Pdefensa;

    @FXML
    private Label Pvelocidad;

    private int protaX;

    private int protaY;

    @FXML
    private ImageView imagenProta;

    // Protagonista
    private Protagonista protagonista; // No inicializar aquí

    private final int FILAS = 15;
    private final int COLUMNAS = 15;
    private char[][] mapa = new char[FILAS][COLUMNAS];
    private GridPane tablero;

    private final int FILAS2 = 15;
    private final int COLUMNAS2 = 15;
    private char[][] mapa2 = new char[FILAS2][COLUMNAS2];
    private GridPane tablero2;

    @FXML
    public void initialize() {
        // No inicializar Protagonista aquí
        // Actualizar etiquetas iniciales para evitar null
        onChange();

        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/fondos/Fondo_Tablero.png").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panelPrincipal.widthProperty());
        imagenfondo.fitHeightProperty().bind(panelPrincipal.heightProperty());
        imagenfondo.setPreserveRatio(false);

        Image image2 = new Image(getClass().getResource("/com/desarrollo/imagenes/fondos/Fondo_Vbox.png").toExternalForm());
        imagenfondoStackPane.setImage(image2);
        imagenfondoStackPane.fitWidthProperty().bind(datosStackPane.widthProperty());
        imagenfondoStackPane.fitHeightProperty().bind(datosStackPane.heightProperty());
        imagenfondoStackPane.setPreserveRatio(false);

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

            // Crear un tablero visual sin imágenes para el StackPane
            tablero2 = new GridPane();
            tablero2.setHgap(0);
            tablero2.setVgap(0);
            tablero2.setPickOnBounds(false); // Deja pasar los eventos

            for (int fila = 0; fila < FILAS2; fila++) {
                for (int col = 0; col < COLUMNAS2; col++) {
                AnchorPane celda = new AnchorPane();
                celda.setPrefSize(35, 35);
                char tipo = mapa2[fila][col];
                if (tipo == 'P' || tipo == 'S') {
                tablero2.add(celda, col, fila);
            }

            AnchorPane.setTopAnchor(tablero2, 0.0);
            AnchorPane.setBottomAnchor(tablero2, 0.0);
            AnchorPane.setLeftAnchor(tablero2, 0.0);
            AnchorPane.setRightAnchor(tablero2, 0.0);




    }
}

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    

    private void cargarMapaDesdeArchivo() throws IOException {
        String ruta = "demo/ficheros/tablero.txt";
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

    @Override
    public void onChange() {
        if (protagonista != null) {
            Pnombre.setText(protagonista.getNombre());
            Psalud.setText("" + protagonista.getSalud());
            Pfuerza.setText("" + protagonista.getFuerza());
            Pdefensa.setText("" + protagonista.getDefensa());
            Pvelocidad.setText("" + protagonista.getVelocidad());
        } else {
            Pnombre.setText("Nombre: N/A");
            Psalud.setText("Salud: 0");
            Pfuerza.setText("Fuerza: 0");
            Pdefensa.setText("Defensa: 0");
            Pvelocidad.setText("Velocidad: 0");
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


    public void recibirDatosProtagonista(String nombre, int salud, int fuerza, int defensa, int velocidad, String rutaImagen, int posicionY, int posicionX) {

        // Crear el objeto Protagonista con los datos recibidos
        protagonista = new Protagonista(nombre, salud, fuerza, defensa, velocidad);

        // Suscribir este controlador como observador
        protagonista.suscribe(this);

        // Actualizar las etiquetas
        onChange();

        // Mostrar la imagen del protagonista en el tablero
        Image imagenProtagonista = new Image(getClass().getResource(rutaImagen).toExternalForm());
        ImageView imagenProta = new ImageView(imagenProtagonista);
        imagenProta.setFitWidth(35);
        imagenProta.setFitHeight(35);

        protaX = posicionX;
        protaY = posicionY;
        
        tablero2.add(imagenProta, protaX, protaY);
        

        /*imagenProta.setTranslateX(posicionX * 35); // Ajustar la posición gráfica*/
    }
}