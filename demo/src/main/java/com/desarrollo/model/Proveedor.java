package com.desarrollo.model;

/**
 * Clase que implementa el patrón Singleton para gestionar una única instancia
 * de un proveedor que almacena y proporciona acceso al objeto {@link Protagonista}.
 * Esta clase asegura que solo exista una instancia global del proveedor en la aplicación.
 *
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0 
 */
public class Proveedor {
    /*Instancia que es única del proveedor, y sigue el patron Singleton */
    private static Proveedor instance;
    private Protagonista protagonista;
    /**
     * Constructor privado para evitar la creación directa de instancias.
     * Implementa el patrón Singleton asegurando que solo se cree una instancia
     * a través del método {@link #getInstance()}.
     */
    private Proveedor() {
        // Constructor privado para singleton
    }

    /**
     * Obtiene la instancia única del proveedor.
     * Si no existe una instancia, crea una nueva. Este método implementa
     * el patrón Singleton para garantizar una única instancia global.
     *
     * @return la instancia única del proveedor
     */
    public static Proveedor getInstance() {
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    /**
     * Obtiene el objeto Protagonista asociado al proveedor.
     *
     * @return el objeto Protagonista, o null si no existe.
     */
    public Protagonista getProtagonista() {
        return this.protagonista;
    }

    /**
     * Establece el objeto Protagonista que será gestionado por el proveedor.
     *
     * @param protagonista el objeto Protagonista que crea.
     */
    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
    }
    
}
