package com.desarrollo.model;

import java.util.ArrayList;
import java.util.List;

import com.desarrollo.interfaces.Observer;

public class Protagonista {
    private String nombre;
    private int salud;
    private int fuerza;
    private int defensa;
    private int velocidad;
    private int posicionX;
    private int posicionY;
    private List<Observer> observers = new ArrayList<>();
    private String imagenRutaPronta; 

   
    
    public Protagonista() {
        // Constructor por defecto
    }

    public Protagonista(String nombre, int salud, int fuerza, int defensa, int velocidad, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
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

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
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

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;  // Ya no es necesario verificar límites aquí
        notifyObservers();  // Notificar a los observadores cuando la posición cambia
    }
    
    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;  // Ya no es necesario verificar límites aquí
        notifyObservers();  // Notificar a los observadores cuando la posición cambia
    }
    

    public void moverArriba() {
        posicionY--;
        notifyObservers();
    }

    public void moverAbajo() {
        posicionY++;
        notifyObservers();
    }

    public void moverIzquierda() {
        posicionX--;
        notifyObservers();
    }

    public void moverDerecha() {
        posicionX++;
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