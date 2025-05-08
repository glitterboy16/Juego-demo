package com.desarrollo.model;

import java.util.ArrayList;
import java.util.List;

import com.desarrollo.interfaces.Observer;

public class Protagonista {
    private String nombre;
    private int salud;
    private int fuerza;
    private int defensa;
    private int velocidad;
    private int posicionX;
    private int posicionY;

    private Mapa mapa;
    private List<Observer> observers = new ArrayList<>();
    private String imagenRutaPronta; 

   
    
    public Protagonista() {
        // Constructor por defecto
    }

    public Protagonista(String nombre, int salud, int fuerza, int defensa, int velocidad, int posicionX, int posicionY, Mapa mapa) {
        this.nombre = nombre;
        this.salud = salud;
        this.fuerza = fuerza;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.mapa = mapa;

        // Verificación de la celda en la posición inicial
    char celdaInicio = mapa.getCelda(posicionX, posicionY);
    System.out.println("Posición inicial del protagonista: (" + posicionX + ", " + posicionY + ")");
    System.out.println("Celda en la posición inicial: " + celdaInicio);  // Debe ser 'S' si es transitable
    }

    public String getImagenRutaPronta() {
        return imagenRutaPronta;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyObservers();
    }

    public void setSalud(int salud) {
        this.salud = salud;
        notifyObservers();
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
        notifyObservers();
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
        notifyObservers();
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
        notifyObservers();
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;  // Ya no es necesario verificar límites aquí
        notifyObservers();  // Notificar a los observadores cuando la posición cambia
    }
    
    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;  // Ya no es necesario verificar límites aquí
        notifyObservers();  // Notificar a los observadores cuando la posición cambia
    }
    

    // Método para mover hacia arriba
    /*public void moverArriba() {
        int nuevaPosX = this.posicionX;
        int nuevaPosY = this.posicionY - 1; // Movimiento hacia arriba
        System.out.println("Intentando mover a: (" + nuevaPosX + ", " + nuevaPosY + ")");
        char celdaDestino = mapa.getCelda(nuevaPosX, nuevaPosY);
        System.out.println("Celda destino: " + celdaDestino);
    
        if (nuevaPosY >= 0 && mapa.esCeldaTransitable(nuevaPosX, nuevaPosY)) {
            this.posicionY = nuevaPosY; // Si es transitable, actualiza la posición
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido a: (" + nuevaPosX + ", " + nuevaPosY + ")");
        } else {
            System.out.println("Movimiento no permitido.");
        }
    }

    public void moverAbajo() {
        int nuevaPosX = this.posicionX;
        int nuevaPosY = this.posicionY + 1; // Movimiento hacia abajo
    
        // Verifica si la nueva posición está dentro del mapa y si la celda es transitable
        if (nuevaPosY < mapa.getNumeroDeFilas() && mapa.esCeldaTransitable(nuevaPosX, nuevaPosY)) {
            this.posicionY = nuevaPosY; // Actualiza la posición si es transitable
            notifyObservers();  // Notificar a los observadores (si es necesario)
        } else {
            System.out.println("Movimiento no permitido hacia abajo.");
        }
    }

    public void moverIzquierda() {
        int nuevaPosX = this.posicionX - 1; // Movimiento hacia la izquierda
        int nuevaPosY = this.posicionY;
    
        // Verifica si la nueva posición está dentro del mapa y si la celda es transitable
        if (nuevaPosX >= 0 && mapa.esCeldaTransitable(nuevaPosX, nuevaPosY)) {
            this.posicionX = nuevaPosX; // Actualiza la posición si es transitable
            notifyObservers();  // Notificar a los observadores (si es necesario)
        } else {
            System.out.println("Movimiento no permitido hacia la izquierda.");
        }
    }

    public void moverDerecha() {
        int nuevaPosX = this.posicionX + 1; // Movimiento hacia la derecha
        int nuevaPosY = this.posicionY;
    
        // Verifica si la nueva posición está dentro del mapa y si la celda es transitable
        if (nuevaPosX < mapa.getNumeroDeColumnas() && mapa.esCeldaTransitable(nuevaPosX, nuevaPosY)) {
            this.posicionX = nuevaPosX; // Actualiza la posición si es transitable
            notifyObservers();  // Notificar a los observadores (si es necesario)
        } else {
            System.out.println("Movimiento no permitido hacia la derecha.");
        }
    }*/
    /*nuevo para cargar las imagenes del prota */

    
    
    /*nuevo */
    public void moverArriba() {
        int nuevaY = this.posicionY - 1; // Movimiento hacia arriba
        if (nuevaY >= 0 && mapa.esCeldaTransitable(nuevaY, posicionX)) {
            this.posicionY = nuevaY;           
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido hacia arriba a: (" + nuevaY + ", " + posicionX + ")");
        } else {
            System.out.println("Movimiento no permitido hacia arriba.");
        }
    }
    
    public void moverAbajo() {
        int nuevaY = this.posicionY + 1; // Movimiento hacia abajo
        if (nuevaY < mapa.getNumeroDeFilas() && mapa.esCeldaTransitable(nuevaY, posicionX)) {
            this.posicionY = nuevaY;
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido hacia abajo a: (" + nuevaY + ", " + posicionX + ")");
        } else {
            System.out.println("Movimiento no permitido hacia abajo.");
        }
    }
    
    public void moverIzquierda() {
        int nuevaX = this.posicionX - 1; // Movimiento hacia la izquierda
        if (nuevaX >= 0 && mapa.esCeldaTransitable(posicionY, nuevaX)) {
            this.posicionX = nuevaX;            
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido hacia la izquierda a: (" + posicionY + ", " + nuevaX + ")");
        } else {
            System.out.println("Movimiento no permitido hacia la izquierda.");
        }
    }
    
    public void moverDerecha() {
        int nuevaX = this.posicionX + 1; // Movimiento hacia la derecha
        if (nuevaX < mapa.getNumeroDeColumnas() && mapa.esCeldaTransitable(posicionY, nuevaX)) {
            this.posicionX = nuevaX;           
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido hacia la derecha a: (" + posicionY + ", " + nuevaX + ")");
        } else {
            System.out.println("Movimiento no permitido hacia la derecha.");
        }
    }
    // Observer pattern
    public void suscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsuscribe(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.onChange();
        }
    }
}