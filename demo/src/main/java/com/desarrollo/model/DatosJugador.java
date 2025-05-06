package com.desarrollo.model;

public class DatosJugador {

    private static DatosJugador instance;
    private Protagonista protagonista;

    private DatosJugador() {}

    public static DatosJugador getInstance() {
        if (instance == null) {
            instance = new DatosJugador();
        }
        return instance;
    }

    public void crearProtagonista(int salud, int fuerza, int defensa, int velocidad, String nombre) {
        System.out.println("Creando protagonista con salud: " + salud);
        this.protagonista = new Protagonista(salud, fuerza, defensa, velocidad);
    }
    
    public Protagonista getProtagonista() {
        System.out.println("Obteniendo protagonista: " + protagonista);
        return protagonista;
    }
    
}
