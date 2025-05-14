package com.desarrollo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
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

/**
 * Controlador para la vista del tablero de juego, que gestiona la interfaz gráfica y
 * la lógica de interacción entre el protagonista, los enemigos y el mapa.
 * Implementa el patrón Observer para actualizar la interfaz cuando cambian los datos
 * del protagonista o los enemigos.
 *
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0
 */

public class TableroController implements Observer {
    /** Panel principal que contiene todos los elementos de la interfaz. */
    @FXML
    private AnchorPane panelPrincipal;
    /** Imagen de fondo del panel principal. */
    @FXML
    private ImageView imagenfondo;
    /** Imagen de fondo del StackPane que muestra los datos. */
    @FXML
    private ImageView imagenfondoStackPane;
    /** Panel que contiene el tablero de juego. */
    @FXML
    private AnchorPane tableroPanel;
    /** Segunda imagen del panel del tablero (no utilizada activamente). */
    @FXML
    private ImageView tableroPanel2;
    /** StackPane que contiene los datos del protagonista y enemigos. */
    @FXML
    private StackPane datosStackPane;

    /** Etiqueta para el nombre del protagonista. */
    @FXML
    private Label Pnombre;
    /** Barra de progreso para la salud del protagonista. */
    @FXML
    private ProgressBar PsaludBar;
    /** Etiqueta para la salud numérica del protagonista. */
    @FXML
    private Label Psalud;
    /** Etiqueta para la fuerza del protagonista. */
    @FXML
    private Label Pfuerza;
    /** Etiqueta para la defensa del protagonista. */
    @FXML
    private Label Pdefensa;
    /** Etiqueta para la velocidad del protagonista. */
    @FXML
    private Label Pvelocidad;

    /** Etiquetas para los nombres de los enemigos (hasta 4). */
    @FXML
    private Label E1nombre, E2nombre, E3nombre, E4nombre;
    /** Barras de progreso para la salud de los enemigos (hasta 4). */
    @FXML
    private ProgressBar E1saludBar, E2saludBar, E3saludBar, E4saludBar;
    /** Etiquetas para la salud numérica de los enemigos (hasta 4). */
    @FXML
    private Label E1salud, E2salud, E3salud, E4salud;
    /** Etiquetas para la fuerza de los enemigos (hasta 4). */
    @FXML
    private Label E1fuerza, E2fuerza, E3fuerza, E4fuerza;
    /** Etiquetas para la defensa de los enemigos (hasta 4). */
    @FXML
    private Label E1defensa, E2defensa, E3defensa, E4defensa;
    /** Etiquetas para la velocidad de los enemigos (hasta 4). */
    @FXML
    private Label E1velocidad, E2velocidad, E3velocidad, E4velocidad;

    /** Coordenada X del protagonista en el tablero. */
    private int protaX;
    /** Coordenada Y del protagonista en el tablero. */
    private int protaY;

    /** Imagen del protagonista en el tablero. */
    @FXML
    private ImageView imagenProta;

    /** Instancia de un enemigo (no utilizada activamente). */
    private Enemigo enemigo;

    /** Imagen de un enemigo (no utilizada activamente). */
    @FXML
    private ImageView imagenEnemigo;

    /** Instancia del protagonista del juego. */
    private Protagonista protagonista;
    /** Mapa del juego que define las celdas transitables y no transitables. */
    private Mapa mapa;
    /** GridPane que representa el tablero de juego. */
    private GridPane tablero;

    /** Lista de enemigos presentes en el juego. */
    private List<Enemigo> enemigos = new ArrayList<>();
    /** Mapa que asocia cada enemigo con su imagen en el tablero. */
    private Map<Enemigo, ImageView> enemigosImagenes = new HashMap<>();

    /**
     * Inicializa la interfaz gráfica del tablero, configurando las imágenes de fondo,
     * el mapa, el tablero y los controles de teclado. También inicializa las estadísticas
     * del protagonista y los enemigos.
     */
    @FXML
    public void initialize() {
        onChange();
        // Configurar imagen de fondo del panel principal
        Image image = new Image(getClass().getResource("/com/desarrollo/imagenes/fondos/Fondo_Tablero.png").toExternalForm());
        imagenfondo.setImage(image);
        imagenfondo.fitWidthProperty().bind(panelPrincipal.widthProperty());
        imagenfondo.fitHeightProperty().bind(panelPrincipal.heightProperty());
        imagenfondo.setPreserveRatio(false);

        // Configurar imagen de fondo del StackPane de datos
        Image image2 = new Image(getClass().getResource("/com/desarrollo/imagenes/fondos/Fondo_Vbox.png").toExternalForm());
        imagenfondoStackPane.setImage(image2);
        imagenfondoStackPane.fitWidthProperty().bind(datosStackPane.widthProperty());
        imagenfondoStackPane.fitHeightProperty().bind(datosStackPane.heightProperty());
        imagenfondoStackPane.setPreserveRatio(false);

        try {
            // Inicializar y cargar el mapa
            mapa = new Mapa();
            mapa.cargarDesdeArchivo("demo/ficheros/tablero.txt");

            // Configurar el tablero como un GridPane
            tablero = new GridPane();
            tablero.setHgap(0);
            tablero.setVgap(0);

            // Anclar el tablero al panel
            AnchorPane.setTopAnchor(tablero, 0.0);
            AnchorPane.setBottomAnchor(tablero, 0.0);
            AnchorPane.setLeftAnchor(tablero, 0.0);
            AnchorPane.setRightAnchor(tablero, 0.0);

            // Actualizar el tablero cuando cambie el tamaño del panel
            tableroPanel.widthProperty().addListener((obs, oldVal, newVal) -> actualizarTablero());
            tableroPanel.heightProperty().addListener((obs, oldVal, newVal) -> actualizarTablero());

            actualizarTablero();
            tableroPanel.getChildren().add(tablero);

            // Configurar el foco para los eventos de teclado
            tableroPanel.setFocusTraversable(true);
            tableroPanel.requestFocus();

            // Configurar los eventos de teclado
            Platform.runLater(() -> {
                Scene scene = tableroPanel.getScene();
                if (scene != null) {
                    scene.setOnKeyPressed(event -> {
                        int newX = protagonista.getPosicionX();
                        int newY = protagonista.getPosicionY();
                        String direccion = "";
                        
                        // Determinar la nueva posición según la tecla presionada
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

                        // Verificar si la nueva posición es válida
                        if (newX >= 0 && newX < mapa.getNumeroDeColumnas() && 
                            newY >= 0 && newY < mapa.getNumeroDeFilas() && 
                            mapa.esCeldaTransitable(newY, newX)) {

                                // Verificar si hay un enemigo en la posición
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
    /**
     * Actualiza el tablero gráfico, dibujando las celdas según el estado del mapa
     * (suelo o pared).
     */
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

    /**
     * Inicializa los datos del protagonista y lo agrega al tablero.
     *
     * @param nombre      Nombre del protagonista.
     * @param salud       Salud inicial del protagonista.
     * @param fuerza      Fuerza del protagonista.
     * @param defensa     Defensa del protagonista.
     * @param velocidad   Velocidad del protagonista.
     * @param rutaImagen  Ruta de la imagen del protagonista.
     * @param y           Posición Y inicial en el tablero.
     * @param x           Posición X inicial en el tablero.
     */
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

        // Configurar eventos de teclado (duplicado, podría optimizarse)
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
        // Agregar enemigos al tablero
        agregarEnemigo(13, 1, "/com/desarrollo/imagenes/Enemigo1_abajo.png", 10, 5, "Enemigo 1", 25, 8, 5, 6, 1);
        agregarEnemigo(1, 13, "/com/desarrollo/imagenes/Enemigo2_abajo.png", 10, 5, "Enemigo 2", 25, 7, 4, 5, 2);
        agregarEnemigo(7, 6, "/com/desarrollo/imagenes/Enemigo3_abajo.png", 10, 5, "Enemigo 3", 25, 9, 6, 7, 3);
        
        inicializarEstadisticas();
    }

     /**
     * Agrega un enemigo al tablero con sus estadísticas e imagen.
     *
     * @param x            Posición X inicial del enemigo.
     * @param y            Posición Y inicial del enemigo.
     * @param rutaImagen   Ruta de la imagen del enemigo.
     * @param percepcion   Percepción del enemigo.
     * @param velocidad    Velocidad de movimiento del enemigo.
     * @param nombre       Nombre del enemigo.
     * @param salud        Salud inicial del enemigo.
     * @param fuerza       Fuerza del enemigo.
     * @param defensa      Defensa del enemigo.
     * @param velocidadStat Velocidad estadística del enemigo.
     * @param tipo         Tipo de enemigo.
     */
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

/**
     * Actualiza las posiciones gráficas de los enemigos en el tablero.
     */
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

    /**
     * Mueve todos los enemigos automáticamente y verifica si atacan al protagonista.
     * Si el protagonista muere, cambia a la escena de "Game Over".
     */
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

    /**
     * Actualiza la interfaz gráfica con los datos del protagonista y los enemigos
     * cuando cambian sus estados.
     */
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

    /**
     * Inicializa las etiquetas y barras de progreso con las estadísticas del
     * protagonista y los enemigos.
     */
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

    /**
     * Actualiza la posición gráfica del protagonista en el tablero.
     */
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

    /**
     * Verifica si una celda está ocupada por un enemigo.
     *
     * @param x Posición X de la celda.
     * @param y Posición Y de la celda.
     * @return true si la celda está ocupada por un enemigo, false en caso contrario.
     */
    private boolean estaOcupadaPorEnemigo(int x, int y) {
        for (Enemigo e : enemigos) {
            if (e.getPosicionX() == x && e.getPosicionY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el enemigo ubicado en una posición específica.
     *
     * @param x Posición X de la celda.
     * @param y Posición Y de la celda.
     * @return El enemigo en la posición especificada, o null si no hay enemigo.
     */
    private Enemigo getEnemigoEnPosicion(int x, int y) {
        for (Enemigo e : enemigos) {
            if (e.getPosicionX() == x && e.getPosicionY() == y) {
                return e;
            }
        }
        return null;
    }
}