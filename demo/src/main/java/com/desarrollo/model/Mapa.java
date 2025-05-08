package com.desarrollo.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Mapa {
    private final int FILAS;
    private final int COLUMNAS;
    private final char[][] mapa;

    public Mapa(String ruta) throws IOException {
        this.FILAS = 15;  // O el tamaño adecuado que necesites
        this.COLUMNAS = 15;  // O el tamaño adecuado que necesites
        mapa = new char[FILAS][COLUMNAS];
        
        cargarDesdeArchivo(ruta);  // Llamar a cargar el mapa desde el archivo
    }

    // Constructor sin parámetros, usa la ruta por defecto
    public Mapa() throws IOException {
        this("demo/ficheros/tablero.txt");  // Llama al constructor que recibe la ruta
    }
    
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
    }

    public char getTipoCelda(int fila, int columna) {
        return mapa[fila][columna];
    }

    public int getFilas() {
        return FILAS;
    }

    public int getColumnas() {
        return COLUMNAS;
    }

    public boolean esCeldaTransitable(int fila, int columna) {
        return mapa[fila][columna] == 'S'; // Ejemplo: solo 'S' es transitable
    }
}

