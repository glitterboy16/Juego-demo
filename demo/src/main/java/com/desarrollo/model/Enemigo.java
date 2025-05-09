package com.desarrollo.model;

public class Enemigo{

    private int percepcion;
    private int tipo;
    private int velocidad;
    private String imagenRutaEnemigo;
    
    public Enemigo(int percepcion, int velocidad) {
        this.percepcion = percepcion;
        this.velocidad=velocidad;
    }

    public void setVelocidad(int velocidad){
        this.velocidad = velocidad;
    }

    public int Velocidad(){
        return this.velocidad;
    }
    
    public int getPercepcion() {
        return this.percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }
    
    public void moverAutomaticamente(){/*AQUI VA EL MÉTODO DE MOVER AUTOMÁTICAMENTE */}

    private void cambiarImagen(String direccion) {
        // Ruta relativa al archivo de la imagen, ajústala si estás usando otro sistema de rutas
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo" + tipo + "_" + direccion + ".png";
    }
}
