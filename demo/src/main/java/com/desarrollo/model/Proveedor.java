package com.desarrollo.model;

public class Proveedor {
    private static Proveedor instance;
    private Protagonista protagonista;

    private Proveedor() {
        // Constructor privado para singleton
    }

    public static Proveedor getInstance() {
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    public Protagonista getProtagonista() {
        return this.protagonista;
    }

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
    }
    
}
