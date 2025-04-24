package com.desarrollo.controllers;

import com.desarrollo.SceneID;
import com.desarrollo.SceneManager;
import com.desarrollo.interfaces.Observer;
import com.desarrollo.model.Model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Formulario2Controller implements Observer {

    @FXML
    private TextField nombre;

    @FXML
    private TextField edad;

    @FXML
    private TextField colorpelo;

    @FXML
    private Label nombre1;

    @FXML
    private Label edad1;

    @FXML
    private Label colorpelo1;

    @FXML
    private Button boton;

    @FXML
    private Button vista1;

    @FXML
    private Label etiqueta;

    private Model model;

    @Override
    public void onChange() {
        // Al ser notificado por el modelo, actualizamos los campos con los datos del modelo
        nombre.setText(model.getPersona().getNombre());
        edad.setText(String.valueOf(model.getPersona().getEdad()));
        colorpelo.setText(model.getPersona().getColorPelo());

        // También actualizamos las etiquetas con los datos del modelo
        nombre1.setText(model.getPersona().getNombre());
        edad1.setText(String.valueOf(model.getPersona().getEdad()));
        colorpelo1.setText(model.getPersona().getColorPelo());
    }


    @FXML
    public void initialize() {
        model = Model.getInstance(); // accede al modelo compartido
        model.suscribe(this);

        if (model.getPersona() != null) {
        onChange(); // solo si hay persona
        }

        vista1.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.MAIN);
        });
        nombre.setOnAction(event -> {
            model.getPersona().setNombre(nombre.getText());
            model.notifyObservers();
        });
        edad.setOnAction(event -> {
            try {
                model.getPersona().setEdad(Integer.parseInt(edad.getText()));
                model.notifyObservers();
            } catch (NumberFormatException ex) {
                etiqueta.setText("Edad inválida. Debe ser un número.");
            }
        });
        colorpelo.setOnAction(event -> {
            model.getPersona().setColorPelo(colorpelo.getText());
            model.notifyObservers();
        });
        boton.setOnAction(event -> {
            try{
            String nuevoNombre = nombre.getText();
            int nuevaEdad = Integer.parseInt(edad.getText());
            String nuevoColorPelo = colorpelo.getText();

            model.getPersona().setNombre(nuevoNombre);
            model.getPersona().setEdad(nuevaEdad);
            model.getPersona().setColorPelo(nuevoColorPelo);
            model.notifyObservers();

            etiqueta.setText("Información actualizada correctamente");
        } catch (NumberFormatException ex) {
        etiqueta.setText("Edad inválida. Debe ser un número.");
        }
        });
        etiqueta.setText("Cuando rellenes el formulario pulsa 'Actualizar' y si quieres puedes volver a la vista 1");
        nombre.setPromptText("Introduce el nombre");
        edad.setPromptText("Introdeuce la edad");
        colorpelo.setPromptText("Intruduce el color de pelo");
        boton.setText("Actualizar");
        vista1.setText("Ir a la vista 1");
    }
}