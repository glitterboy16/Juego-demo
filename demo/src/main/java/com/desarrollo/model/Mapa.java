package com.desarrollo.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Mapa {
    private final int filas;
    private final int columnas;
    private final char[][] contenido;

    public Mapa(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.contenido = new char[filas][columnas];
    }

    public void cargarDesdeArchivo(String ruta) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int fila = 0;
            while ((linea = br.readLine()) != null && fila < filas) {
                String lineaSinEspacios = linea.replaceAll("\\s+", "");
                for (int col = 0; col < columnas; col++) {
                    contenido[fila][col] = lineaSinEspacios.charAt(col);
                }
                fila++;
            }
        }
    }

    public char getCelda(int fila, int columna) {
        return contenido[fila][columna];
    }

    public int getFilas() { return filas; }
    public int getColumnas() { return columnas; }
}

