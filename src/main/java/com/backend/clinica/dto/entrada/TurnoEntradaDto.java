package com.backend.clinica.dto.entrada;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TurnoEntradaDto {


    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de turno requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaHora;

    @NotNull(message = "El odontologo no puede ser nulo")
    @Valid
    private OdontologoEntradaDto odontologo;

    @NotNull(message = "El paciente no puede ser nulo")
    @Valid
    private PacienteEntradaDto paciente;

    public TurnoEntradaDto() {

    }


    public TurnoEntradaDto(LocalDateTime fechaHora, OdontologoEntradaDto odontologo, PacienteEntradaDto paciente) {
        this.fechaHora = fechaHora;
        this.odontologo = odontologo;
        this.paciente = paciente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public OdontologoEntradaDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoEntradaDto odontologo) {
        this.odontologo = odontologo;
    }

    public PacienteEntradaDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEntradaDto paciente) {
        this.paciente = paciente;
    }
}
