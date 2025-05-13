package com.desarrollo.model;

import java.util.List;

public class Enemigo {

    private int percepcion;
    private int tipo;
    private int velocidad;
    private int posicionX;
    private int posicionY;
    private String imagenRutaEnemigo;
    private String nombre;
    private int salud;
    private int fuerza;
    private int defensa;
    private int velocidadStat;

    public Enemigo(int percepcion, int velocidad, String nombre, int salud, int fuerza, int defensa, int velocidadStat) {
        this.percepcion = percepcion;
        this.velocidad = velocidad;
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidadStat = velocidadStat;
    }

    public Enemigo(int percepcion, int velocidad) {
        this(percepcion, velocidad, "Enemigo", 100, 5, 5, 5);
    }

    public void setPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidadStat;
    }

    private void cambiarImagen(String direccion) {
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo" + tipo + "_" + direccion + ".png";
    }

    public void moverAutomaticamente(Protagonista prota, Mapa mapa, List<Enemigo> enemigos) {
        // La comprobación de percepción ha sido eliminada para que los enemigos siempre intenten moverse
        int dx = prota.getPosicionX() - posicionX;
        int dy = prota.getPosicionY() - posicionY;

        int nextX = posicionX;
        int nextY = posicionY;
        String direccion = "";
        
        if (Math.abs(dx) > Math.abs(dy)) {
            nextX += Integer.compare(dx, 0);
        } else {
            nextY += Integer.compare(dy, 0);
        }

        if (nextX < 0 || nextY < 0 || nextY >= mapa.getNumeroDeFilas() || nextX >= mapa.getNumeroDeColumnas()) return;

        if (!mapa.esCeldaTransitable(nextY, nextX)) return;

        for (Enemigo otro : enemigos) {
            if (otro != this && otro.getPosicionX() == nextX && otro.getPosicionY() == nextY) return;
        }

        if (prota.getPosicionX() == nextX && prota.getPosicionY() == nextY) {
            int daño = 5;
            prota.setSaludMax(prota.getSaludMax() - daño);
            System.out.println("¡Enemigo atacó al protagonista! -5 salud");
            return;
        }

        posicionX = nextX;
        posicionY = nextY;
        cambiarImagen(direccion);
    }
}