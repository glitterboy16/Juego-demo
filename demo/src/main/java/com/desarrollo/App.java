package com.desarrollo;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager sm = SceneManager.getInstance();
        sm.init(stage, "styles");
        sm.setScene(SceneID.MAIN, "vista1", 900, 600);
        sm.loadScene(SceneID.MAIN);   
        sm.setScene(SceneID.SECONDARY, "vista2", 900, 600);
        sm.setScene(SceneID.TABLERO, "tablero", 900, 600);
        sm.setScene(SceneID.VISTAGAMEOVER, "vista4", 900, 600);
        sm.setScene(SceneID.VISTAGANADOR, "vista5", 900, 600);

    }

    

    public static void main(String[] args) {
        launch();
    }

}