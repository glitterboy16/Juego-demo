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

    public Enemigo(int percepcion, int velocidad, String nombre, int salud, int fuerza, int defensa, int velocidadStat, int tipo) {
        this.percepcion = percepcion;
        this.velocidad = velocidad;
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidadStat = velocidadStat;
        this.tipo = tipo;
    }

    public Enemigo(int percepcion, int velocidad, int tipo) {
        this(percepcion, velocidad, "Enemigo", 100, 5, 5, 5, tipo);
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
    
    public String getImagenRutaEnemigo() {
        return imagenRutaEnemigo;
    }

    private void cambiarImagen(String direccion, int tipo) {
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo" + tipo + "_" + direccion + ".png";
    }

    public void moverAutomaticamente(Protagonista prota, Mapa mapa, List<Enemigo> enemigos) {
        // La comprobación de percepción ha sido eliminada para que los enemigos siempre intenten moverse
        int dx = prota.getPosicionX() - posicionX;
        int dy = prota.getPosicionY() - posicionY;

        int nextX = posicionX;
        int nextY = posicionY;
        String direccion = "";

        // Determinar la dirección según el movimiento
        if (Math.abs(dx) > Math.abs(dy)) {
            nextX += Integer.compare(dx, 0);
            direccion = (dx > 0) ? "derecha" : "izquierda";
        } else {
            nextY += Integer.compare(dy, 0);
            direccion = (dy > 0) ? "abajo" : "arriba";
        }
        // Verificar si la siguiente posición es válida
        if (nextX < 0 || nextY < 0 || nextY >= mapa.getNumeroDeFilas() || nextX >= mapa.getNumeroDeColumnas()) return;

        if (!mapa.esCeldaTransitable(nextY, nextX)) return;
        // Verificar colisión con otros enemigos
        for (Enemigo otro : enemigos) {
            if (otro != this && otro.getPosicionX() == nextX && otro.getPosicionY() == nextY) return;
        }
        // Verificar si el protagonista está en la siguiente posición (ataque)
        if (prota.getPosicionX() == nextX && prota.getPosicionY() == nextY) {
            int daño = 5;
            prota.setSaludMax(prota.getSaludMax() - daño);
            System.out.println("¡Enemigo atacó al protagonista! -5 salud");
            return;
        }

        posicionX = nextX;
        posicionY = nextY;
        cambiarImagen(direccion, tipo);
    }
}