package com.desarrollo.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Protagonista extends Personaje {

    private ImageView imagen; // Representación del personaje con una imagen

    public Protagonista() {
        super(30, 30, 30, 10, 0, 0); // Llama al constructor de la clase padre
        this.imagen = new ImageView(new Image(getClass().getResource("/com/desarrollo/imagenes/personaje_abajo.png").toExternalForm()));
    }

    public Protagonista(String nombre, int salud, int fuerza, int defensa, int velocidad, String rutaImagen) {
        super(salud, fuerza, defensa, velocidad, 0, 0); // Llama al constructor de la clase padre
        this.imagen = new ImageView(new Image(getClass().getResource("/com/desarrollo/imagenes/personaje_abajo.png").toExternalForm()));
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void mover(int nuevaX, int nuevaY) {
        setPosicionX(nuevaX); // Actualiza la posición en la clase padre
        setPosicionY(nuevaY);
        imagen.setTranslateX(nuevaX * 32); // Ajusta la posición gráfica
        imagen.setTranslateY(nuevaY * 32);
        notifyObservers(); // Notifica a los observadores del cambio
    }
}