package com.backend.clinica.dto.salida;



public class OdontologoSalidaDto {

    private Long id;
    private String nmatricula;
    private String nombre;
    private String apellido;

    public OdontologoSalidaDto(){

    }

    public OdontologoSalidaDto(Long id, String nmatricula, String nombre, String apellido) {
        this.id = id;
        this.nmatricula = nmatricula;
        this.nombre = nombre;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
