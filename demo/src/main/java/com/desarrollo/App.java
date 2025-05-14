package com.desarrollo;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * Clase principal de la aplicación JavaFX.
 * Esta clase inicializa el escenario principal y configura la gestión de escenas
 * utilizando {@link SceneManager} para manejar múltiples escenas de la aplicación.
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0
 */
public class App extends Application {
    /** La escena principal de la aplicación */
    private static Scene scene;
    /** MediaPlayer estático para la música de fondo */
    private static MediaPlayer mediaPlayer;

    /**
     * Punto de entrada para la aplicación JavaFX.
     * Este método es invocado para iniciar la aplicación.
     * Inicializa el {@link SceneManager}, configura el escenario principal y
     * define las escenas con sus respectivos diseños FXML y dimensiones.
     * También inicializa la reproducción de música de fondo en bucle y añade un icono.
     *
     * @param stage es el escenario principal de la aplicación.
     * @throws IOException si ocurre un error al cargar archivos FXML o recursos
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Configurar la reproducción de música de fondo (solo si no está ya inicializada)
        if (mediaPlayer == null) {
            try {
                // Cargar el archivo desde los recursos usando el classpath
                java.net.URL recurso = getClass().getResource("/musica/LegendOfZelda3_ALinkToThePast.WAV");
                if (recurso == null) {
                    throw new IllegalArgumentException("No se encontró el archivo de música en el classpath: /musica/LegendOfZelda3_ALinkToThePast.WAV");
                }
                System.out.println("Archivo de música encontrado: " + recurso.toString());
                
                // Crear objeto Media con la URL del archivo
                Media media = new Media(recurso.toString());
                // Crear MediaPlayer para reproducir el audio
                mediaPlayer = new MediaPlayer(media);
                System.out.println("MediaPlayer creado correctamente.");

                // Ajustar volumen al 50%
                mediaPlayer.setVolume(0.5);
                // Configurar para repetir indefinidamente
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                // Añadir listener para depurar el estado del MediaPlayer
                mediaPlayer.statusProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println("Estado del MediaPlayer cambiado: " + oldValue + " -> " + newValue);
                    if (newValue == MediaPlayer.Status.STOPPED || newValue == MediaPlayer.Status.HALTED) {
                        System.out.println("La música se detuvo inesperadamente.");
                    }
                });

                // Añadir listener para errores
                mediaPlayer.setOnError(() -> {
                    System.out.println("Error en MediaPlayer: " + mediaPlayer.getError().getMessage());
                });

                // Iniciar la reproducción
                mediaPlayer.play();
                System.out.println("Reproducción iniciada.");
            } catch (Exception e) {
                // Manejar errores al cargar o reproducir el archivo de audio
                System.out.println("Error al reproducir la música: " + e.getMessage());
            }
        }

        // Inicializa la instancia singleton de SceneManager
        SceneManager sm = SceneManager.getInstance();
        // Configura SceneManager con el escenario y la hoja de estilos
        sm.init(stage, "styles");
        sm.setScene(SceneID.MAIN, "vista1", 1000, 750);
        sm.loadScene(SceneID.MAIN);   
        sm.setScene(SceneID.SECONDARY, "vista2", 1000, 750);
        sm.setScene(SceneID.TABLERO, "tablero", 1000, 750);
        sm.setScene(SceneID.VISTAGAMEOVER, "vista4", 1000, 750);
        sm.setScene(SceneID.VISTAGANADOR, "vista5", 1000, 750);

        // Cargar y establecer el icono
        try {
            Image icono = new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/icono.png"));
            stage.getIcons().add(icono);
        } catch (Exception e) {
            System.out.println("Error al cargar el icono: " + e.getMessage());
        }
    }

    /**
     * Método para detener la música (opcional, si quieres detenerla manualmente)
     */
    public static void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null; // Permitir que se vuelva a inicializar si es necesario
            System.out.println("Música detenida manualmente.");
        }
    }

    /**
     * Método principal que lanza la aplicación JavaFX.
     */
    public static void main(String[] args) {
        launch();
    }
}