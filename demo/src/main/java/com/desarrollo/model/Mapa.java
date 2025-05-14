package com.desarrollo.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Representa un mapa de juego cargado desde un archivo de texto, donde cada celda puede ser
 * transitable ('S') o no transitable ('P'). Proporciona métodos para consultar las dimensiones
 * del mapa, el estado de las celdas y la transitabilidad.
 *
 * @author Ángel Andrés Villorina
 * @author Ana Rubio
 * @author María Teresa Calvo
 * @version 1.0
 */
public class Mapa {
    /** Número de filas del mapa. */
    private final int FILAS;
    /** Número de columnas del mapa. */
    private final int COLUMNAS;
    /** Matriz bidimensional que almacena el estado de cada celda del mapa. */
    private final char[][] mapa;

    /**
     * Crea un mapa con dimensiones predefinidas y carga su contenido desde un archivo.
     *
     * @param ruta Ruta del archivo de texto que contiene el mapa.
     * @throws IOException Si ocurre un error al leer el archivo o si el formato es inválido.
     */
    public Mapa(String ruta) throws IOException {
        this.FILAS = 15;  // O el tamaño adecuado que necesites
        this.COLUMNAS = 15;  // O el tamaño adecuado que necesites
        mapa = new char[FILAS][COLUMNAS];
        
        cargarDesdeArchivo(ruta);  // Llamar a cargar el mapa desde el archivo
    }

    /**
     * Crea un mapa con dimensiones predefinidas y carga su contenido desde un archivo por defecto.
     *
     * @throws IOException Si ocurre un error al leer el archivo por defecto.
     */
    public Mapa() throws IOException {
        this("demo/ficheros/tablero.txt");  // Llama al constructor que recibe la ruta
    }

    /**
     * Carga el contenido del mapa desde un archivo de texto. Cada línea del archivo representa
     * una fila del mapa, y cada carácter representa una celda ('S' para transitable, 'P' para no transitable).
     * Imprime el mapa cargado en la consola para depuración.
     *
     * @param ruta Ruta del archivo de texto que contiene el mapa.
     * @throws IOException Si el archivo no se puede leer o si una línea tiene un formato inválido.
     */
    public void cargarDesdeArchivo(String ruta) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int fila = 0;
            while ((linea = br.readLine()) != null && fila < FILAS) {
                String lineaSinEspacios = linea.replaceAll("\\s+", "");
                if (lineaSinEspacios.length() < COLUMNAS) {
                    throw new IOException("La línea " + (fila + 1) + " tiene menos de " + COLUMNAS + " caracteres.");
                }
                for (int col = 0; col < COLUMNAS; col++) {
                    mapa[fila][col] = lineaSinEspacios.charAt(col);
                }
                fila++;
            }
        }
        // Imprimir el mapa cargado
        System.out.println("Mapa cargado:");
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }
    

    /**
     * Obtiene el número de filas del mapa.
     *
     * @return El número de filas.
     */
    public int getNumeroDeFilas() {
        return mapa.length;
    }

    /**
     * Obtiene el número de columnas del mapa.
     *
     * @return El número de columnas.
     */
    public int getNumeroDeColumnas() {
        return mapa[0].length;
    }

    /**
     * Obtiene el carácter que representa una celda específica del mapa.
     *
     * @param fila    Índice de la fila.
     * @param columna Índice de la columna.
     * @return El carácter en la celda especificada, o 'X' si las coordenadas están fuera de los límites.
     */
    public char getCelda(int fila, int columna) {
        if (fila < 0 || fila >= mapa.length || columna < 0 || columna >= mapa[0].length) {
            return 'X';
        }
        return mapa[fila][columna];
    }
    
    /**
     * Verifica si una celda es transitable (representada por 'S').
     *
     * @param x Índice de la fila.
     * @param y Índice de la columna.
     * @return true si la celda es transitable y está dentro de los límites, false en caso contrario.
     */
    public boolean esCeldaTransitable(int x, int y) {
        // Verifica si las coordenadas están dentro de los límites
        if (x >= 0 && x < mapa.length && y >= 0 && y < mapa[0].length) {
            // Verifica si la celda es transitable ('S')
            return mapa[x][y] == 'S';
        }
        return false; // Si fuera fuera de los límites, no es transitable
    }
}

