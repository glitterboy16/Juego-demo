package com.desarrollo.model;

import com.desarrollo.interfaces.Observer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Protagonista {

    private int salud, fuerza, defensa, velocidad;
    private int posicionX, posicionY;
    private ImageView imagenPersonaje;
    private List<Observer> observers = new ArrayList<>(); // 🧠 Observadores que se enteran de cambios

    public Protagonista(int salud, int fuerza, int defensa, int velocidad) {
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.posicionX = 0;
        this.posicionY = 0;
        this.imagenPersonaje = new ImageView(new Image(getClass().getResourceAsStream("/com/desarrollo/imagenes/protagonista.png"))); // Ajusta la ruta
        this.imagenPersonaje.setFitWidth(32); // o el tamaño que quieras
        this.imagenPersonaje.setFitHeight(32);
    }

    // 🔥 Métodos para Observer
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notificarObservers() {
        for (Observer o : observers) {
            o.onChange();
        }
    }


    public void moverDerecha() {
        posicionX++;
        notificarObservers();
    }

    public void moverIzquierda() {
        posicionX--;
        notificarObservers();
    }

    public void moverArriba() {
        posicionY--;
        notificarObservers();
    }

    public void moverAbajo() {
        posicionY++;
        notificarObservers();
    }

    // Getters
    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public ImageView getImagenPersonaje() {
        return imagenPersonaje;
    }
}
