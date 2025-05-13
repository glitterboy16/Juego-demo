package com.desarrollo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.desarrollo.SceneManager;
import com.desarrollo.SceneID;
import com.desarrollo.interfaces.Observer;
import com.desarrollo.model.Enemigo;
import com.desarrollo.model.Mapa;
import com.desarrollo.model.Protagonista;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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

    @FXML
    private StackPane datosStackPane;

    // Datos del protagonista
    @FXML
    private Label Pnombre;

    @FXML
    private ProgressBar PsaludBar;

    @FXML
    private Label Psalud;

    @FXML
    private Label Pfuerza;

    @FXML
    private Label Pdefensa;

    @FXML
    private Label Pvelocidad;

    // Datos de los enemigos
    @FXML
    private Label E1nombre, E2nombre, E3nombre, E4nombre;

    @FXML
    private ProgressBar E1saludBar, E2saludBar, E3saludBar, E4saludBar;

    @FXML
    private Label E1salud, E2salud, E3salud, E4salud;

    @FXML
    private Label E1fuerza, E2fuerza, E3fuerza, E4fuerza;

    @FXML
    private Label E1defensa, E2defensa, E3defensa, E4defensa;

    @FXML
    private Label E1velocidad, E2velocidad, E3velocidad, E4velocidad;

    private int protaX;
    private int protaY;

    @FXML
    private ImageView imagenProta;

    private Enemigo enemigo;

    @FXML
    private ImageView imagenEnemigo;

    private Protagonista protagonista;
    private Mapa mapa;
    private GridPane tablero;

    private List<Enemigo> enemigos = new ArrayList<>();
    private Map<Enemigo, ImageView> enemigosImagenes = new HashMap<>();

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

            AnchorPane.setTopAnchor(tablero, 0.0);
            AnchorPane.setBottomAnchor(tablero, 0.0);
            AnchorPane.setLeftAnchor(tablero, 0.0);
            AnchorPane.setRightAnchor(tablero, 0.0);

            tableroPanel.widthProperty().addListener((obs, oldVal, newVal) -> actualizarTablero());
            tableroPanel.heightProperty().addListener((obs, oldVal, newVal) -> actualizarTablero());

            actualizarTablero();
            tableroPanel.getChildren().add(tablero);

            tableroPanel.setFocusTraversable(true);
            tableroPanel.requestFocus();

            Platform.runLater(() -> {
                Scene scene = tableroPanel.getScene();
                if (scene != null) {
                    scene.setOnKeyPressed(event -> {
                        int newX = protagonista.getPosicionX();
                        int newY = protagonista.getPosicionY();
                        String direccion = "";
                        
                        switch (event.getCode()) {
                            case W:
                                newY -= 1;
                                direccion = "arriba";
                                break;
                            case S:
                                newY += 1;
                                direccion = "abajo";
                                break;
                            case A:
                                newX -= 1;
                                direccion = "izquierda";
                                break;
                            case D:
                                newX += 1;
                                direccion = "derecha";
                                break;
                            default:
                                return;
                        }

                        if (newX >= 0 && newX < mapa.getNumeroDeColumnas() && 
                            newY >= 0 && newY < mapa.getNumeroDeFilas() && 
                            mapa.esCeldaTransitable(newY, newX)) {
                            
                            if (estaOcupadaPorEnemigo(newX, newY)) {
                                Enemigo enemigo = getEnemigoEnPosicion(newX, newY);
                                if (enemigo != null) {
                                    int daño = protagonista.getFuerza() - enemigo.getDefensa();
                                    if (daño < 0) daño = 0; // El daño no puede ser negativo
                                    enemigo.setSalud(enemigo.getSalud() - daño);
                                    System.out.println("¡Protagonista atacó al enemigo! -" + daño + " salud");
                                    if (enemigo.getSalud() <= 0) {
                                        System.out.println("¡Enemigo derrotado!");
                                        enemigos.remove(enemigo);
                                        tableroPanel.getChildren().remove(enemigosImagenes.get(enemigo));
                                        enemigosImagenes.remove(enemigo);
                                        // Verificar si todos los enemigos han sido derrotados
                                        if (enemigos.isEmpty()) {
                                            SceneManager.getInstance().loadScene(SceneID.VISTAGANADOR);
                                            return; // Salir del manejador de eventos después de cambiar la escena
                                        }
                                    }
                                }
                            } else {
                                protagonista.setPosicionX(newX);
                                protagonista.setPosicionY(newY);
                                protagonista.cambiarImagen(direccion);
                            }
                        }
                        
                        actualizarPosicionPersonaje();
                        moverEnemigos();
                        onChange(); // Actualizar la interfaz después de cada acción
                    });
                    tableroPanel.requestFocus();
                } else {
                    System.err.println("La escena no se ha cargado correctamente.");
                }
            });

            inicializarEstadisticas();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al inicializar: " + e.getMessage());
        }
    }

    private void actualizarTablero() {
        Image suelo = new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/S.jpg"));
        Image pared = new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/P.jpg"));
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

        Image imagenProtagonista = new Image(getClass().getResource(rutaImagen).toExternalForm());
        imagenProta = new ImageView(imagenProtagonista);
        imagenProta.setFitWidth(35);
        imagenProta.setFitHeight(35);

        AnchorPane.setLeftAnchor(imagenProta, x * 35.0);
        AnchorPane.setTopAnchor(imagenProta, y * 35.0);
        tableroPanel.getChildren().add(imagenProta);
        imagenProta.toFront();

        Platform.runLater(() -> {
            Scene scene = tableroPanel.getScene();
            scene.setOnKeyPressed(event -> {
                int nuevoX = protagonista.getPosicionX();
                int nuevoY = protagonista.getPosicionY();
                String direccion = "";
                switch (event.getCode()) {
                    case W: nuevoY -= 1; direccion = "arriba"; break;
                    case S: nuevoY += 1; direccion = "abajo"; break;
                    case A: nuevoX -= 1; direccion = "izquierda"; break;
                    case D: nuevoX += 1; direccion = "derecha"; break;
                    default: return;
                }
                if (nuevoX >= 0 && nuevoX < mapa.getNumeroDeColumnas() && 
                    nuevoY >= 0 && nuevoY < mapa.getNumeroDeFilas() && 
                    mapa.esCeldaTransitable(nuevoY, nuevoX)) {
                    if (estaOcupadaPorEnemigo(nuevoX, nuevoY)) {
                        Enemigo enemigo = getEnemigoEnPosicion(nuevoX, nuevoY);
                        if (enemigo != null) {
                            int daño = protagonista.getFuerza() - enemigo.getDefensa();
                            if (daño < 0) daño = 0;
                            enemigo.setSalud(enemigo.getSalud() - daño);
                            System.out.println("¡Protagonista atacó al enemigo! -" + daño + " salud");
                            if (enemigo.getSalud() <= 0) {
                                System.out.println("¡Enemigo derrotado!");
                                enemigos.remove(enemigo);
                                tableroPanel.getChildren().remove(enemigosImagenes.get(enemigo));
                                enemigosImagenes.remove(enemigo);
                                // Verificar si todos los enemigos han sido derrotados
                                if (enemigos.isEmpty()) {
                                    SceneManager.getInstance().loadScene(SceneID.VISTAGANADOR);
                                    return; // Salir del manejador de eventos después de cambiar la escena
                                }
                            }
                        }
                    } else {
                        protagonista.setPosicionX(nuevoX);
                        protagonista.setPosicionY(nuevoY);
                        protagonista.cambiarImagen(direccion);
                    }
                }
                actualizarPosicionPersonaje();
                moverEnemigos();
                onChange(); // Actualizar la interfaz después de cada acción
            });
            tableroPanel.requestFocus();
        });

        agregarEnemigo(13, 1, "/com/desarrollo/imagenes/Enemigo1_abajo.png", 10, 5, "Enemigo 1", 25, 8, 5, 6, 1);
        agregarEnemigo(1, 13, "/com/desarrollo/imagenes/Enemigo2_abajo.png", 10, 5, "Enemigo 2", 25, 7, 4, 5, 2);
        agregarEnemigo(7, 6, "/com/desarrollo/imagenes/Enemigo3_abajo.png", 10, 5, "Enemigo 3", 25, 9, 6, 7, 3);
        agregarEnemigo(13, 13, "/com/desarrollo/imagenes/Enemigo4_abajo.png", 10, 5, "Enemigo 4", 100, 10, 5, 8, 4);
        inicializarEstadisticas();
    }

    public void agregarEnemigo(int x, int y, String rutaImagen, int percepcion, int velocidad, String nombre, int salud, int fuerza, int defensa, int velocidadStat, int tipo) {
    Enemigo nuevoEnemigo = new Enemigo(percepcion, velocidad, nombre, salud, fuerza, defensa, velocidadStat, tipo);
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
        // Actualizar la imagen según la dirección
        String imagenRuta = e.getImagenRutaEnemigo();
        if (imagenRuta != null && !imagenRuta.isEmpty()) {
            img.setImage(new Image(getClass().getResourceAsStream(imagenRuta)));
        } else {
            System.err.println("Ruta de imagen del enemigo es null o vacía para " + e.getNombre());
        }
    }
}

    private void moverEnemigos() {
        for (Enemigo e : enemigos) {
            e.moverAutomaticamente(protagonista, mapa, enemigos);
            // Verificar si el enemigo está adyacente al protagonista
            if (Math.abs(e.getPosicionX() - protagonista.getPosicionX()) + Math.abs(e.getPosicionY() - protagonista.getPosicionY()) == 1) {
                int daño = e.getFuerza() - protagonista.getDefensa();
                if (daño < 0) daño = 0;
                protagonista.setSaludMax(protagonista.getSaludMax() - daño);
                System.out.println("¡Enemigo atacó al protagonista! -" + daño + " salud");
                if (protagonista.getSaludMax() <= 0) {
                    SceneManager.getInstance().loadScene(SceneID.VISTAGAMEOVER);
                    return; // Salir del método después de cambiar la escena
                }
            }
        }
        actualizarPosicionesEnemigos();
        onChange();
    }

    @Override
    public void onChange() {
        if (protagonista != null) {
            Pnombre.setText(protagonista.getNombre());
            double saludProgreso = protagonista.getSaludMax() / 100.0;
            PsaludBar.setProgress(saludProgreso > 0 ? saludProgreso : 0); // Evita el rebote
            Psalud.setText(String.valueOf(protagonista.getSaludMax()));
            Pfuerza.setText(String.valueOf(protagonista.getFuerza()));
            Pdefensa.setText(String.valueOf(protagonista.getDefensa()));
            Pvelocidad.setText(String.valueOf(protagonista.getVelocidad()));
        } else {
            Pnombre.setText("Nombre: N/A");
            PsaludBar.setProgress(0.0);
            Psalud.setText("0");
            Pfuerza.setText("0");
            Pdefensa.setText("0");
            Pvelocidad.setText("0");
        }

        for (int i = 0; i < enemigos.size() && i < 4; i++) {
            Enemigo e = enemigos.get(i);
            double saludEnemigoProgreso = e.getSalud() / 100.0;
            switch (i) {
                case 0:
                    E1nombre.setText(e.getNombre());
                    E1saludBar.setProgress(saludEnemigoProgreso > 0 ? saludEnemigoProgreso : 0); // Evita el rebote
                    E1salud.setText(String.valueOf(e.getSalud()));
                    E1fuerza.setText(String.valueOf(e.getFuerza()));
                    E1defensa.setText(String.valueOf(e.getDefensa()));
                    E1velocidad.setText(String.valueOf(e.getVelocidad()));
                    break;
                case 1:
                    E2nombre.setText(e.getNombre());
                    E2saludBar.setProgress(saludEnemigoProgreso > 0 ? saludEnemigoProgreso : 0); // Evita el rebote
                    E2salud.setText(String.valueOf(e.getSalud()));
                    E2fuerza.setText(String.valueOf(e.getFuerza()));
                    E2defensa.setText(String.valueOf(e.getDefensa()));
                    E2velocidad.setText(String.valueOf(e.getVelocidad()));
                    break;
                case 2:
                    E3nombre.setText(e.getNombre());
                    E3saludBar.setProgress(saludEnemigoProgreso > 0 ? saludEnemigoProgreso : 0); // Evita el rebote
                    E3salud.setText(String.valueOf(e.getSalud()));
                    E3fuerza.setText(String.valueOf(e.getFuerza()));
                    E3defensa.setText(String.valueOf(e.getDefensa()));
                    E3velocidad.setText(String.valueOf(e.getVelocidad()));
                    break;
                case 3:
                    E4nombre.setText(e.getNombre());
                    E4saludBar.setProgress(saludEnemigoProgreso > 0 ? saludEnemigoProgreso : 0); // Evita el rebote
                    E4salud.setText(String.valueOf(e.getSalud()));
                    E4fuerza.setText(String.valueOf(e.getFuerza()));
                    E4defensa.setText(String.valueOf(e.getDefensa()));
                    E4velocidad.setText(String.valueOf(e.getVelocidad()));
                    break;
            }
        }
    }

    private void inicializarEstadisticas() {
        if (protagonista != null) {
            Pnombre.setText(protagonista.getNombre());
            Pfuerza.setText(String.valueOf(protagonista.getFuerza()));
            Pdefensa.setText(String.valueOf(protagonista.getDefensa()));
            Pvelocidad.setText(String.valueOf(protagonista.getVelocidad()));
            PsaludBar.setProgress(protagonista.getSaludMax() / 100.0);
            Psalud.setText(String.valueOf(protagonista.getSaludMax()));
        }

        for (int i = 0; i < enemigos.size() && i < 4; i++) {
            Enemigo e = enemigos.get(i);
            switch (i) {
                case 0:
                    E1nombre.setText(e.getNombre());
                    E1fuerza.setText(String.valueOf(e.getFuerza()));
                    E1defensa.setText(String.valueOf(e.getDefensa()));
                    E1velocidad.setText(String.valueOf(e.getVelocidad()));
                    E1saludBar.setProgress(e.getSalud() / 100.0);
                    E1salud.setText(String.valueOf(e.getSalud()));
                    break;
                case 1:
                    E2nombre.setText(e.getNombre());
                    E2fuerza.setText(String.valueOf(e.getFuerza()));
                    E2defensa.setText(String.valueOf(e.getDefensa()));
                    E2velocidad.setText(String.valueOf(e.getVelocidad()));
                    E2saludBar.setProgress(e.getSalud() / 100.0);
                    E2salud.setText(String.valueOf(e.getSalud()));
                    break;
                case 2:
                    E3nombre.setText(e.getNombre());
                    E3fuerza.setText(String.valueOf(e.getFuerza()));
                    E3defensa.setText(String.valueOf(e.getDefensa()));
                    E3velocidad.setText(String.valueOf(e.getVelocidad()));
                    E3saludBar.setProgress(e.getSalud() / 100.0);
                    E3salud.setText(String.valueOf(e.getSalud()));
                    break;
                case 3:
                    E4nombre.setText(e.getNombre());
                    E4fuerza.setText(String.valueOf(e.getFuerza()));
                    E4defensa.setText(String.valueOf(e.getDefensa()));
                    E4velocidad.setText(String.valueOf(e.getVelocidad()));
                    E4saludBar.setProgress(e.getSalud() / 100.0);
                    E4salud.setText(String.valueOf(e.getSalud()));
                    break;
            }
        }
    }

    private void actualizarPosicionPersonaje() {
        if (protagonista != null) {
            AnchorPane.setLeftAnchor(imagenProta, protagonista.getPosicionX() * 35.0);
            AnchorPane.setTopAnchor(imagenProta, protagonista.getPosicionY() * 35.0);
            String imagenRuta = protagonista.getImagenRutaPronta();
            if (imagenRuta != null && !imagenRuta.isEmpty()) {
                imagenProta.setImage(new Image(getClass().getResourceAsStream(imagenRuta)));
            } else {
                System.err.println("Ruta de imagen del protagonista es null o vacía, usando imagen por defecto.");
                imagenProta.setImage(new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/prota_default.png")));
            }
        }
    }

    private boolean estaOcupadaPorEnemigo(int x, int y) {
        for (Enemigo e : enemigos) {
            if (e.getPosicionX() == x && e.getPosicionY() == y) {
                return true;
            }
        }
        return false;
    }

    private Enemigo getEnemigoEnPosicion(int x, int y) {
        for (Enemigo e : enemigos) {
            if (e.getPosicionX() == x && e.getPosicionY() == y) {
                return e;
            }
        }
        return null;
    }
}