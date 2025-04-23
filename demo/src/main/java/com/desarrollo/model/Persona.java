package com.desarrollo.model;

public class Persona {
    private String nombre;
    private int edad;
    private String colorPelo;

    public Persona(String nombre, int edad, String colorPelo) {
        this.nombre = nombre;
        this.edad = edad;
        this.colorPelo = colorPelo;
    }

    // Getter y Setter para nombre, edad y colorPelo
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getColorPelo() { return colorPelo; }
    public void setColorPelo(String colorPelo) { this.colorPelo = colorPelo; }
}
