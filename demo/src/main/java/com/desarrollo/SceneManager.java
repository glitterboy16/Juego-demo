package com.desarrollo;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase singleton que gestiona las escenas de la aplicación, permitiendo su inicialización, configuración y carga.
 * 
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0
 */
public class SceneManager {
    // Instancia única (singleton) de SceneManager
    private static SceneManager instance;

    private Stage stage; // La ventana principal de la aplicación
    private URL styles;
    private HashMap<SceneID, Scene> scenes; // Mapa para almacenar las escenas según su identificador

    /**
     * Constructor privado que inicializa el mapa de escenas.
     */
    private SceneManager() {
        scenes = new HashMap<>();
    }

    /**
     * Obtiene la instancia única de SceneManager.
     * 
     * @return la instancia singleton
     */
    public static SceneManager getInstance(){
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * Inicializa SceneManager con el escenario principal y una hoja de estilos.
     * 
     * @param stage el escenario principal de la aplicación
     * @param styles el nombre de la hoja de estilos a usar
     */
    @SuppressWarnings("exports")
    public void init(Stage stage, String styles){
        this.stage = stage;
        this.styles = App.class.getResource("Styles/" + styles + ".css");
    }

    /**
     * Inicializa SceneManager con el escenario principal sin hoja de estilos.
     * 
     * @param stage el escenario principal de la aplicación
     */
    @SuppressWarnings("exports")
    public void init(Stage stage){
        this.stage = stage;
    }

    /**
     * Configura una escena con el identificador, archivo FXML y dimensiones especificadas.
     * 
     * @param sceneID el identificador de la escena
     * @param fxml el nombre del archivo FXML
     * @param width el ancho de la escena
     * @param height el alto de la escena
     */
    public void setScene(SceneID sceneID, String fxml, int width, int height){
        try {
            // Carga el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, width, height); // Crea la escena con el tamaño especificado
            // Vincular el archivo CSS a la escena
            if (styles!=null) scene.getStylesheets().add(styles.toExternalForm()); // Añade la hoja de estilo
            scene.setUserData(fxmlLoader);
            scenes.put(sceneID, scene); // Almacena la escena en el mapa con between identificador correspondiente
        } catch (IOException e) {
            e.printStackTrace(); // En caso de error al cargar el FXML
        }
    }

    /**
     * Elimina la escena con el identificador especificado.
     * 
     * @param sceneID el identificador de la escena a eliminar
     */
    public void removeScene(SceneID sceneID){
        scenes.remove(sceneID); // Elimina la escena del mapa
    }

    /**
     * Carga y muestra la escena con el identificador especificado.
     * 
     * @param sceneID el identificador de la escena a cargar
     */
    public void loadScene(SceneID sceneID) {
        if (scenes.containsKey(sceneID)){
            stage.setScene(scenes.get(sceneID)); // Establece la escena en la ventana principal
            stage.show(); // Muestra la ventana con la nueva escena
        } 
    }

    /**
     * Obtiene el controlador asociado a la escena con el identificador especificado.
     * 
     * @param sceneID el identificador de la escena
     * @return el controlador de la escena, o null si no se encuentra
     */
    public Object getController(SceneID sceneID) {
        if (scenes.containsKey(sceneID)) {
            Scene scene = scenes.get(sceneID);
            FXMLLoader loader = (FXMLLoader) scene.getUserData(); // Recuperar el loader
            return loader.getController();
        }
        return null;
    }
}