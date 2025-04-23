package com.desarrollo.model;

import java.util.ArrayList;

import com.desarrollo.interfaces.Observer;

public class Model {
    private static Model instance;

    private Persona persona;
    private ArrayList<Observer> observers;

    public Model() {
        this.persona = null;
        this.observers = new ArrayList<>();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public Persona getPersona() {
        return persona;
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

    public void setPersona(String nombre, int edad, String colorPelo) {
        this.persona = new Persona(nombre, edad, colorPelo);
        notifyObservers();
    }
}

