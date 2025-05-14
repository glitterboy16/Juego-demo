package com.desarrollo.model;

import java.util.ArrayList;
import java.util.List;
import com.desarrollo.interfaces.Observer;

/**
 * Clase que representa al protagonista del juego.
 * Gestiona las características del protagonista, como su nombre, salud, fuerza, defensa,
 * velocidad y posición en el mapa. Implementa el patrón Observer para notificar cambios
 * a los observadores y permite el movimiento del protagonista en el mapa verificando
 * celdas transitables.
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0 
 */
public class Protagonista {
    /*Datos del protagonista */
    private String nombre;
    private int saludMax;
    private int fuerza;
    private int defensa;
    private int velocidad;
    private int posicionX;
    private int posicionY;
    /** Mapa en el que se encuentra el protagonista. */
    private Mapa mapa;
    /*Lista de observador que recibe las notificaciones de los cambios en el protagonista. */
    private List<Observer> observers = new ArrayList<>();
    private String imagenRutaPronta; 

    /**
     * Constructor por defecto de la clase Protagonista.
     */
    public Protagonista() {
        // Constructor por defecto
    }

    /**
     * Constructor que inicializa el protagonista con sus propiedades y su posición en el mapa.
     * Verifica la celda inicial en el mapa para confirmar que es transitable.
     *
     * @param nombre nombre del protagonista
     * @param salud salud máxima del protagonista
     * @param fuerza fuerza del protagonista
     * @param defensa defensa del protagonista
     * @param velocidad velocidad del protagonista
     * @param posicionX posición inicial en el eje X
     * @param posicionY posición inicial en el eje Y
     * @param mapa mapa en el que se encuentra el protagonista
     */
    public Protagonista(String nombre, int salud, int fuerza, int defensa, int velocidad, int posicionX, int posicionY, Mapa mapa) {
        this.nombre = nombre;
        this.saludMax = salud;
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

    public int getSaludMax() {
        return saludMax;
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

    public void setSaludMax(int salud) {
        this.saludMax = salud;
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
    
    /**
     * Cambia la imagen del protagonista según la dirección del movimiento.
     * La ruta de la imagen se construye usando un formato predefinido.
     *
     * @param direccion la dirección del movimiento ("arriba", "abajo", "izquierda", "derecha")
     */
    public void cambiarImagen(String direccion) {
        // Ruta relativa al archivo de la imagen, ajústala si estás usando otro sistema de rutas
        this.imagenRutaPronta = "/com/desarrollo/imagenes/personaje_" + direccion + ".png";
    }

    /**
     * Mueve al protagonista hacia arriba en el mapa, si la celda destino es transitable.
     * Actualiza la posición, la imagen y notifica a los observadores.
     */
    public void moverArriba() {
        int nuevaY = this.posicionY - 1; // Movimiento hacia arriba
        if (nuevaY >= 0 && mapa.esCeldaTransitable(nuevaY, posicionX)) {
            this.posicionY = nuevaY; 
            cambiarImagen("arriba");          
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido hacia arriba a: (" + nuevaY + ", " + posicionX + ")");
        } else {
            System.out.println("Movimiento no permitido hacia arriba.");
        }
    }

    /**
     * Mueve al protagonista hacia abajo en el mapa, si la celda destino es transitable.
     * Actualiza la posición, la imagen y notifica a los observadores.
     */
    public void moverAbajo() {
        int nuevaY = this.posicionY + 1; // Movimiento hacia abajo
        if (nuevaY < mapa.getNumeroDeFilas() && mapa.esCeldaTransitable(nuevaY, posicionX)) {
            this.posicionY = nuevaY;
            cambiarImagen("abajo");
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido hacia abajo a: (" + nuevaY + ", " + posicionX + ")");
        } else {
            System.out.println("Movimiento no permitido hacia abajo.");
        }
    }

    /**
     * Mueve al protagonista hacia la izquierda en el mapa, si la celda destino es transitable.
     * Actualiza la posición, la imagen y notifica a los observadores.
     */
    public void moverIzquierda() {
        int nuevaX = this.posicionX - 1; // Movimiento hacia la izquierda
        if (nuevaX >= 0 && mapa.esCeldaTransitable(posicionY, nuevaX)) {
            this.posicionX = nuevaX; 
            cambiarImagen("izquierda");           
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido hacia la izquierda a: (" + posicionY + ", " + nuevaX + ")");
        } else {
            System.out.println("Movimiento no permitido hacia la izquierda.");
        }
    }

    /**
     * Mueve al protagonista hacia la derecha en el mapa, si la celda destino es transitable.
     * Actualiza la posición, la imagen y notifica a los observadores.
     */
    public void moverDerecha() {
        int nuevaX = this.posicionX + 1; // Movimiento hacia la derecha
        if (nuevaX < mapa.getNumeroDeColumnas() && mapa.esCeldaTransitable(posicionY, nuevaX)) {
            this.posicionX = nuevaX;  
            cambiarImagen("derecha");         
            notifyObservers();  // Notificar a los observadores
            System.out.println("Movido hacia la derecha a: (" + posicionY + ", " + nuevaX + ")");
        } else {
            System.out.println("Movimiento no permitido hacia la derecha.");
        }
    }

    // Observer pattern
    /**
     * Suscribe un observador para recibir notificaciones de cambios en el protagonista.
     *
     * @param observer el observador a suscribir
     */
    public void suscribe(Observer observer) {
        observers.add(observer);
    }
    /**
     * Desuscribe un observador para que deje de recibir notificaciones.
     *
     * @param observer el observador a desuscribir
     */
    public void unsuscribe(Observer observer) {
        observers.remove(observer);
    }
    /**
     * Notifica a todos los observadores suscritos cuando ocurre un cambio en el protagonista.
     */
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.onChange();
        }
    }
}