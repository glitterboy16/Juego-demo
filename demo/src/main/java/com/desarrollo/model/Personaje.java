package com.desarrollo.model;

import java.util.ArrayList;

import com.desarrollo.interfaces.Observer;

public class Personaje {
    private static Personaje instance;
    private Personaje personaje;
    private ArrayList<Observer> observers;

    private int salud;
    private int fuerza;
    private int defensa;
    private int velocidad;
    private int posicionX;
    private int posicionY;


    public Personaje(int salud, int fuerza, int defensa, int velocidad, int posicionX, int posicionY) {
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public Personaje(){
        this.salud = 100;
        this.fuerza = 100;
        this.defensa = 50;
        this.velocidad = 10;
        this.posicionX = 0;
        this.posicionY = 0;
        this.observers= new ArrayList<>();
    }

    public static Personaje getInstance() {
        if (instance == null) {
            instance = new Personaje();
        }
        return instance;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void suscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsuscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(Observer::onChange);
    }

    public void setPersonaje(int salud, int fuerza, int defensa, int velocidad, int posicionX, int posicionY) {
        this.personaje = new Personaje(salud, fuerza, defensa, velocidad, posicionX, posicionY );
        notifyObservers();
    }

    public int getSalud() {
        return this.salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getFuerza() {
        return this.fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getDefensa() {
        return this.defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVelocidad() {
        return this.velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getPosicionX() {
        return this.posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return this.posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public static void mover(){}
    
    public static void atacar(){}
}
