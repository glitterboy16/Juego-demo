package com.desarrollo.model;

import java.util.List;

/**
 * Representa un enemigo en el juego, con atributos como posición, estadísticas de combate
 * y comportamiento para moverse automáticamente hacia el protagonista.
 *
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0
 */
public class Enemigo {

    /** Radio de percepción del enemigo para detectar al protagonista. */
    private int percepcion;
    /** Tipo de enemigo, usado para determinar su imagen. */
    private int tipo;
    /** Velocidad de movimiento del enemigo. */
    private int velocidad;
    /** Posición X del enemigo en el tablero. */
    private int posicionX;
    /** Posición Y del enemigo en el tablero. */
    private int posicionY;
    /** Ruta de la imagen que representa al enemigo en la interfaz gráfica. */
    private String imagenRutaEnemigo;
    /** Nombre del enemigo. */
    private String nombre;
    /** Salud actual del enemigo. */
    private int salud;
    /** Fuerza del enemigo para calcular el daño en combate. */
    private int fuerza;
    /** Defensa del enemigo para reducir el daño recibido. */
    private int defensa;
    /** Velocidad estadística del enemigo (usada en estadísticas, no en movimiento). */
    private int velocidadStat;

    /**
     * Crea un enemigo con estadísticas completas.
     *
     * @param percepcion   Radio de percepción del enemigo.
     * @param velocidad    Velocidad de movimiento del enemigo.
     * @param nombre       Nombre del enemigo.
     * @param salud        Salud inicial del enemigo.
     * @param fuerza       Fuerza del enemigo.
     * @param defensa      Defensa del enemigo.
     * @param velocidadStat Velocidad estadística del enemigo.
     * @param tipo         Tipo de enemigo (determina su imagen).
     */
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

    /**
     * Crea un enemigo con estadísticas predeterminadas, usado para configuraciones básicas.
     *
     * @param percepcion Radio de percepción del enemigo.
     * @param velocidad Velocidad de movimiento del enemigo.
     * @param tipo      Tipo de enemigo (determina su imagen).
     */
    public Enemigo(int percepcion, int velocidad, int tipo) {
        this(percepcion, velocidad, "Enemigo", 100, 5, 5, 5, tipo);
    }

    /**
     * Establece la posición del enemigo en el tablero.
     *
     * @param x Coordenada X en el tablero.
     * @param y Coordenada Y en el tablero.
     */
    public void setPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
    }

    /**
     * Obtiene la coordenada X del enemigo en el tablero.
     *
     * @return La coordenada X actual.
     */
    public int getPosicionX() {
        return posicionX;
    }

    /**
     * Obtiene la coordenada Y del enemigo en el tablero.
     *
     * @return La coordenada Y actual.
     */
    public int getPosicionY() {
        return posicionY;
    }

    /**
     * Obtiene el nombre del enemigo.
     *
     * @return El nombre del enemigo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la salud actual del enemigo.
     *
     * @return La salud actual.
     */
    public int getSalud() {
        return salud;
    }

    /**
     * Establece la salud del enemigo.
     *
     * @param salud La nueva salud del enemigo.
     */
    public void setSalud(int salud) {
        this.salud = salud;
    }

    /**
     * Obtiene la fuerza del enemigo.
     *
     * @return La fuerza actual.
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * Obtiene la defensa del enemigo.
     *
     * @return La defensa actual.
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Obtiene la velocidad estadística del enemigo.
     *
     * @return La velocidad estadística.
     */
    public int getVelocidad() {
        return velocidadStat;
    }
    
    /**
     * Obtiene la ruta de la imagen que representa al enemigo.
     *
     * @return La ruta de la imagen, o null si no está definida.
     */
    public String getImagenRutaEnemigo() {
        return imagenRutaEnemigo;
    }

    /**
     * Cambia la imagen del enemigo según su dirección y tipo.
     *
     * @param direccion Dirección del movimiento ("arriba", "abajo", "izquierda", "derecha").
     * @param tipo      Tipo de enemigo para seleccionar la imagen correspondiente.
     */
    private void cambiarImagen(String direccion, int tipo) {
        this.imagenRutaEnemigo = "/com/desarrollo/imagenes/Enemigo" + tipo + "_" + direccion + ".png";
    }

    /**
     * Mueve al enemigo automáticamente hacia el protagonista, atacando si está en una
     * posición adyacente. El movimiento se realiza en la dirección de mayor distancia
     * (horizontal o vertical) hacia el protagonista, respetando las reglas del mapa y
     * evitando colisiones con otros enemigos.
     *
     * @param prota    El protagonista hacia el cual se mueve el enemigo.
     * @param mapa     El mapa del juego que define las celdas transitables.
     * @param enemigos Lista de otros enemigos para evitar colisiones.
     */
    public void moverAutomaticamente(Protagonista prota, Mapa mapa, List<Enemigo> enemigos) {
        // Calcular la diferencia de posición con el protagonista
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

        // Actualizar posición y cambiar imagen
        posicionX = nextX;
        posicionY = nextY;
        cambiarImagen(direccion, tipo);
    }
}