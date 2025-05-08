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
        // Imprimir el mapa cargado
        System.out.println("Mapa cargado:");
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }
    

    // Método para obtener el número de filas
    public int getNumeroDeFilas() {
        return mapa.length;
    }

    // Método para obtener el número de columnas
    public int getNumeroDeColumnas() {
        return mapa[0].length;
    }

    // Método para obtener el valor de la celda en (x, y)
    /*public char getCelda(int x, int y) {
        if (x < 0 || x >= mapa.length || y < 0 || y >= mapa[0].length) {
            return 'X';  // Si las coordenadas están fuera de los límites, consideramos que es un obstáculo
        }
        return mapa[x][y];
    }*/

    public char getCelda(int fila, int columna) {
        if (fila < 0 || fila >= mapa.length || columna < 0 || columna >= mapa[0].length) {
            return 'X';
        }
        return mapa[fila][columna];
    }
    
    

    public boolean esCeldaTransitable(int x, int y) {
        // Verifica si las coordenadas están dentro de los límites
        if (x >= 0 && x < mapa.length && y >= 0 && y < mapa[0].length) {
            // Verifica si la celda es transitable ('S')
            return mapa[x][y] == 'S';
        }
        return false; // Si fuera fuera de los límites, no es transitable
    }
    
    
    
}

