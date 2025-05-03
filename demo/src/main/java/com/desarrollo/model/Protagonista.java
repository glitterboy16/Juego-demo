package com.desarrollo.model;

import javafx.scene.image.ImageView;

public class Protagonista extends Personaje {

    String nombre;
    int velocidad;
    int salud;
    int fuerza;
    int defensa;
    // Coordenadas del personaje en el tablero
    int x; 
    int y;
    ImageView imagen; // Representación del personaje


    public Protagonista() {
        this.nombre = "Desconocido";
        this.velocidad = 10;
        this.salud = 100;
        this.fuerza = 15;
        this.defensa = 5;
        this.x = 0; 
        this.y = 0; 
        this.imagen = new ImageView(""); // Inicializar la imagen
    
    }


    public Protagonista(String nombre, int velocidad, int salud, int fuerza, int defensa) {
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
    }
    
    
    public void desplazarse(){}

    public void introducirEstadistica(){}
}
