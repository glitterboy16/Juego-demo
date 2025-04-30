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
        sm.init(stage);
        sm.setScene(SceneID.MAIN, "vista1", 600, 440);
        sm.loadScene(SceneID.MAIN);   
        sm.setScene(SceneID.SECONDARY, "Vista2", 700, 530);
        sm.setScene(SceneID.VISTAGAMEOVER, "Vista4", 700, 530);
        sm.setScene(SceneID.TABLERO, "Tablero", 830, 590);

        
    }

    

    public static void main(String[] args) {
        launch();
    }

}