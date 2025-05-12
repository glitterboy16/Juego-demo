package com.desarrollo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.desarrollo.interfaces.Observer;
import com.desarrollo.model.Enemigo;
import com.desarrollo.model.Mapa;
import com.desarrollo.model.Protagonista;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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

    private Enemigo enemigo;

    @FXML
    private ImageView imagenEnemigo;


    // Protagonista
    private Protagonista protagonista;
    private Mapa mapa;
    private GridPane tablero;

    @FXML
    public void initialize() {
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
            mapa = new Mapa();
            mapa.cargarDesdeArchivo("demo/ficheros/tablero.txt");

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

            // Asegúrate de que el tableroPanel tenga el foco para capturar los eventos de teclado
            tableroPanel.setFocusTraversable(true);
            tableroPanel.requestFocus(); // Esto garantiza que el tablero capture las teclas

            // Manejo de teclas
            Platform.runLater(() -> {
                Scene scene = tableroPanel.getScene();  // Obtén la escena del tablero
                if (scene != null) {  // Verificar si la escena está cargada correctamente
                    scene.setOnKeyPressed(event -> {  // Aquí se capturan los eventos de teclas
                        switch (event.getCode()) {
                            case W:
                                protagonista.moverArriba();
                                break;
                            case S:
                                protagonista.moverAbajo();
                                break;
                            case A:
                                protagonista.moverIzquierda();
                                break;
                            case D:
                                protagonista.moverDerecha();
                                break;
                            default:
                                break;
                        }
                        // Después de mover al protagonista, actualizamos su posición en la interfaz
                        actualizarPosicionPersonaje();
                    });
                    tableroPanel.requestFocus();  // Asegurarse de que el tablero tenga el foco para detectar teclas
                } else {
                    System.err.println("La escena no se ha cargado correctamente.");
                }
            });
    
        } catch (Exception e) {
            // Manejo de excepciones para detectar errores durante la inicialización o el manejo de eventos
            e.printStackTrace();
            System.err.println("Error al recibir los datos del protagonista: " + e.getMessage());
        }
    }

    private void actualizarTablero(Image suelo, Image pared) {
        tablero.getChildren().clear();
        double tam = 35;

        for (int fila = 0; fila < mapa.getNumeroDeFilas(); fila++) {
            for (int col = 0; col < mapa.getNumeroDeColumnas(); col++) {
                ImageView celda = new ImageView();
                celda.setFitWidth(tam);
                celda.setFitHeight(tam);
                char tipo = mapa.getCelda(fila, col);
                if (tipo == 'S') celda.setImage(suelo);
                else if (tipo == 'P') celda.setImage(pared);
                tablero.add(celda, col, fila);
            }
        }
    }

    @FXML
public void recibirDatosProtagonista(String nombre, int salud, int fuerza, int defensa, int velocidad, String rutaImagen, int y, int x) {
    protagonista = new Protagonista(nombre, salud, fuerza, defensa, velocidad, x, y, mapa);
    protagonista.suscribe(this);
    onChange();

    // Crear la imagen del protagonista
    Image imagenProtagonista = new Image(getClass().getResource(rutaImagen).toExternalForm());
    imagenProta = new ImageView(imagenProtagonista);  // Inicializar la variable imagenProta
    imagenProta.setFitWidth(35);
    imagenProta.setFitHeight(35);

    // Añadir la imagen al tablero
    AnchorPane.setLeftAnchor(imagenProta, x * 35.0);
    AnchorPane.setTopAnchor(imagenProta, y * 35.0);
    tableroPanel.getChildren().add(imagenProta);
    imagenProta.toFront();  // Asegurarse de que la imagen esté en la capa superior

    // Capturar eventos de teclas para mover al protagonista
    Platform.runLater(() -> {
        Scene scene = tableroPanel.getScene();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W: protagonista.moverArriba(); break;
                case S: protagonista.moverAbajo(); break;
                case A: protagonista.moverIzquierda(); break;
                case D: protagonista.moverDerecha(); break;
                default: break;
            }
            // Actualizar la posición del protagonista en el tablero
            actualizarPosicionPersonaje();
            moverEnemigos();        });
        tableroPanel.requestFocus();
    });
    agregarEnemigo(13, 1, "/com/desarrollo/imagenes/Enemigo1_abajo.png", 10, 5);
    agregarEnemigo(1, 13, "/com/desarrollo/imagenes/Enemigo2_abajo.png", 10, 5);
    agregarEnemigo(7, 6, "/com/desarrollo/imagenes/Enemigo3_abajo.png", 10, 5);
    agregarEnemigo(13, 13, "/com/desarrollo/imagenes/Enemigo4_abajo.png", 10, 5);

}
private List<Enemigo> enemigos = new ArrayList<>();
private Map<Enemigo, ImageView> enemigosImagenes = new HashMap<>();

public void agregarEnemigo(int x, int y, String rutaImagen, int percepcion, int velocidad) {
    Enemigo nuevoEnemigo = new Enemigo(percepcion, velocidad);
    nuevoEnemigo.setPosicion(x, y);

    Image imagen = new Image(getClass().getResource(rutaImagen).toExternalForm());
    ImageView imagenEnemigo = new ImageView(imagen);
    imagenEnemigo.setFitWidth(35);
    imagenEnemigo.setFitHeight(35);

    AnchorPane.setLeftAnchor(imagenEnemigo, x * 35.0);
    AnchorPane.setTopAnchor(imagenEnemigo, y * 35.0);

    tableroPanel.getChildren().add(imagenEnemigo);
    imagenEnemigo.toFront();

enemigos.add(nuevoEnemigo);
enemigosImagenes.put(nuevoEnemigo, imagenEnemigo);
}

private void actualizarPosicionesEnemigos() {
    for (Enemigo e : enemigos) {
        ImageView img = enemigosImagenes.get(e);
        AnchorPane.setLeftAnchor(img, e.getPosicionX() * 35.0);
        AnchorPane.setTopAnchor(img, e.getPosicionY() * 35.0);
    }
}

private void moverEnemigos() {
    for (Enemigo e : enemigos) {
        e.moverAutomaticamente(protagonista, mapa, enemigos);
    }
    actualizarPosicionesEnemigos();
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
            Psalud.setText("0");
            Pfuerza.setText("0");
            Pdefensa.setText("0");
            Pvelocidad.setText("0");
        }
    }

    private void actualizarPosicionPersonaje() {
        // Asegurarse de que la posición se actualiza después de cada movimiento
        if (protagonista != null) {
            AnchorPane.setLeftAnchor(imagenProta, protagonista.getPosicionX() * 35.0);
            AnchorPane.setTopAnchor(imagenProta, protagonista.getPosicionY() * 35.0);
            // ACTUALIZA LA IMAGEN del protagonista según la dirección
            imagenProta.setImage(new Image(getClass().getResourceAsStream(protagonista.getImagenRutaPronta())));
        
        }
        
    }
}
