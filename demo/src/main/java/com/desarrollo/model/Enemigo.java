package com.desarrollo.model;

public class Enemigo extends Personaje{

    private int percepcion;
    private static Enemigo instance;
    private Enemigo enemigo;
    
    public Enemigo(int percepcion, Enemigo enemigo) {
        this.percepcion = percepcion;
    }


    public int getPercepcion() {
        return this.percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    public Enemigo getEnemigo() {
        return this.enemigo;
    }

    public void setEnemigo(Enemigo enemigo) {
        this.enemigo = enemigo;
    }
    
    public void moverAutomaticamente(){}
}
