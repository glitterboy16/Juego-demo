package com.desarrollo;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneManager {
    // Instancia única (singleton) de SceneManager
    private static SceneManager instance;

    private Stage stage; // La ventana principal de la aplicación
    private URL styles;
    private HashMap<SceneID, Scene> scenes; // Mapa para almacenar las escenas según su identificador

   
    private SceneManager() {
        scenes = new HashMap<>();
    }

   
    public static SceneManager getInstance(){
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    
    @SuppressWarnings("exports")
    public void init(Stage stage, String styles){
        this.stage = stage;
        this.styles = App.class.getResource("Styles/" + styles + ".css");
    }

    @SuppressWarnings("exports")
    public void init(Stage stage){
        this.stage = stage;
    }

    
    public void setScene(SceneID sceneID, String fxml, int width, int height){
        try {
            // Carga el archivo FXML
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, width, height); // Crea la escena con el tamaño especificado
            // Vincular el archivo CSS a la escena
            if (styles!=null) scene.getStylesheets().add(styles.toExternalForm()); // Añade la hoja de estilo
            scene.setUserData(fxmlLoader);
            scenes.put(sceneID, scene); // Almacena la escena en el mapa con el identificador correspondiente
        } catch (IOException e) {
            e.printStackTrace(); // En caso de error al cargar el FXML
        }
    }

    
    public void removeScene(SceneID sceneID){
        scenes.remove(sceneID); // Elimina la escena del mapa
    }

    
    public void loadScene(SceneID sceneID) {
        if (scenes.containsKey(sceneID)){
            stage.setScene(scenes.get(sceneID)); // Establece la escena en la ventana principal
            stage.show(); // Muestra la ventana con la nueva escena
        } 
    }


    // obtener el controlador de una escena y registrarlo como observador
    public Object getController(SceneID sceneID) {
        if (scenes.containsKey(sceneID)) {
            Scene scene = scenes.get(sceneID);
            FXMLLoader loader = (FXMLLoader) scene.getUserData(); // Recuperar el loader
            return loader.getController();
        }
        return null;
    }
} 