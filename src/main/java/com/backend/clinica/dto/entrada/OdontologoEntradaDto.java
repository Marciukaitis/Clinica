package com.backend.clinica.dto.entrada;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class OdontologoEntradaDto {

    @Positive(message = "El numero de matricula del odontologo no puede ser nulo o menor a cero")
    private String nmatricula;

    @NotBlank(message = "Debe especificarse el nombre del odontologo")
    @Size(max = 50, message = "El nombre debe tener hasta 50 caracteres")
    private String nombre;

    @Size(max = 50, message = "El apellido debe tener hasta 50 caracteres")
    @NotBlank(message = "Debe especificarse el apellido del odontologo")
    private String apellido;

    public OdontologoEntradaDto() {
    }

    public OdontologoEntradaDto(String nmatricula, String nombre, String apellido) {
        this.nmatricula = nmatricula;
        this.nombre = nombre;
        this.apellido = apellido;
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
