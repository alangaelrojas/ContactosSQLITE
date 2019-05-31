package com.alan.moviles.contactossqlite.Pojo;

public class Contacto {
    private String nombres, apellidos, edad, carrera, peso, estatura, descripcion;

    public Contacto(){

    }

    public Contacto(String nombres, String apellidos, String edad, String carrera, String peso, String estatura, String descripcion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.carrera = carrera;
        this.peso = peso;
        this.estatura = estatura;
        this.descripcion = descripcion;
    }
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
