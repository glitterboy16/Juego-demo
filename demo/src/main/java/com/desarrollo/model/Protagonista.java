package com.desarrollo.model;

import com.desarrollo.interfaces.Observer;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class Protagonista {
    private String nombre;
    private int salud;
    private int fuerza;
    private int defensa;
    private int velocidad;
    private List<Observer> observers = new ArrayList<>();
    private String imagenRutaPronta; 

    public Protagonista() {
        // Constructor por defecto
    }

    public Protagonista(String nombre, int salud, int fuerza, int defensa, int velocidad) {
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.imagenRutaPronta = "src/main/resources/com/desarrollo/imagenes/protagonista.png"; // Ruta de la imagen por defecto
    }

    public String getImagenRutaPronta() {
        return imagenRutaPronta;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyObservers();
    }

    public void setSalud(int salud) {
        this.salud = salud;
        notifyObservers();
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
        notifyObservers();
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
        notifyObservers();
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
        notifyObservers();
    }

    // Observer pattern
    public void suscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsuscribe(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.onChange();
        }
    }
}