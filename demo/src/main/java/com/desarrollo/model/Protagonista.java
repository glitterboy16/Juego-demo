package com.desarrollo.model;

public class Protagonista extends Personaje {
    

    public void setPersonaje(int salud, int fuerza, int defensa, int velocidad, int posicionX, int posicionY) {
        this.personaje = new Personaje(salud, fuerza, defensa, velocidad, posicionX, posicionY );
        notifyObservers();
    }
    public void desplazarse(){}

    public void introducirEstadistica(){}
}
