package com.desarrollo.model;

import java.util.List;

public class Enemigo {

    private int percepcion;
    private int tipo;
    private int velocidad;
    private int posicionX;
    private int posicionY;
    private String imagenRutaEnemigo;

    public Enemigo(int percepcion, int velocidad) {
        this.percepcion = percepcion;
        this.velocidad = velocidad;
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

    private void cambiarImagen(String direccion) {
        // Ruta relativa al archivo de la imagen, ajústala si estás usando otro sistema de rutas
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo" + tipo + "_" + direccion + ".png";
    }

    public void moverAutomaticamente(Protagonista prota, Mapa mapa, List<Enemigo> enemigos) {
        // Calcular distancia Manhattan
        int distancia = Math.abs(prota.getPosicionX() - posicionX) + Math.abs(prota.getPosicionY() - posicionY);

        // Solo se mueve si el protagonista está dentro de su rango de percepción
        if (distancia > percepcion) return;

        int dx = prota.getPosicionX() - posicionX;
        int dy = prota.getPosicionY() - posicionY;

        int nextX = posicionX;
        int nextY = posicionY;

        if (Math.abs(dx) > Math.abs(dy)) {
            nextX += Integer.compare(dx, 0);
        } else {
            nextY += Integer.compare(dy, 0);
        }

        // Límites del mapa
        if (nextX < 0 || nextY < 0 || nextY >= mapa.getNumeroDeFilas() || nextX >= mapa.getNumeroDeColumnas()) return;

        // Evitar paredes
        if (!mapa.esCeldaTransitable(nextY, nextX)) return;

        // Evitar otros enemigos
        for (Enemigo otro : enemigos) {
            if (otro != this && otro.getPosicionX() == nextX && otro.getPosicionY() == nextY) return;
        }

        // Atacar si está junto al protagonista
        if (prota.getPosicionX() == nextX && prota.getPosicionY() == nextY) {
            int daño = 5;
            prota.setSalud(prota.getSalud() - daño);
            System.out.println("¡Enemigo atacó al protagonista! -5 salud");
            return;
        }

        // Mover
        posicionX = nextX;
        posicionY = nextY;
    }
}
