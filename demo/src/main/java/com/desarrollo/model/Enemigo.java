package com.desarrollo.model;

public class Enemigo{

    private int percepcion;
    private Enemigo enemigo;

    private String imagenRutaEnemigo;
    
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
    
    public void moverAutomaticamente(){/*AQUI VA EL MÉTODO DE MOVER AUTOMÁTICAMENTE */}

    private void cambiarImagen(String direccion) {
        // Ruta relativa al archivo de la imagen, ajústala si estás usando otro sistema de rutas
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo1_" + direccion + ".png";
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo2_" + direccion + ".png";
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo3_" + direccion + ".png";
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo4_" + direccion + ".png";



    }
}
