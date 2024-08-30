package com.backend.clinica.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "ODONTOLOGOS")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nmatricula;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = false)
    private String apellido;

    public Odontologo (){

    }

    public Odontologo(Long id, String nmatricula, String nombre, String apellido) {
        this.id = id;
        this.nmatricula = nmatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Odontologo(String nmatricula, String nombre, String apellido) {
        this.nmatricula = nmatricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }


    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmatricula() {
        return nmatricula;
    }

    public void setNmatricula(String nmatricula) {
        this.nmatricula = nmatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
