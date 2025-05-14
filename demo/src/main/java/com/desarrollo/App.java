package com.desarrollo;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    /** La escena principal de la aplicación*/
    private static Scene scene;

    /**
     * Punto de entrada para la aplicación JavaFX.
     * Este método es invocado para iniciar la aplicación.
     * Inicializa el {@link SceneManager}, configura el escenario principal y
     * define las escenas con sus respectivos diseños FXML y dimensiones.
     *
     * @param stage es el escenario principal de la aplicación.
     * @throws IOException si ocurre un error al cargar archivos FXML o recursos
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Inicializa la instancia singleton de SceneManager
        SceneManager sm = SceneManager.getInstance();
        // Configura SceneManager con el escenario y la hoja de estilos
        sm.init(stage, "styles");
        sm.setScene(SceneID.MAIN, "vista1", 900, 750);
        sm.loadScene(SceneID.MAIN);   
        sm.setScene(SceneID.SECONDARY, "vista2", 900, 750);
        sm.setScene(SceneID.TABLERO, "tablero", 900, 750);
        sm.setScene(SceneID.VISTAGAMEOVER, "vista4", 900, 750);
        sm.setScene(SceneID.VISTAGANADOR, "vista5", 900, 750);

    }

    /**
     * Método principal que lanza la aplicación JavaFX.
     */
    public static void main(String[] args) {
        launch();
    }

}