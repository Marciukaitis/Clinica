package com.backend.clinica.dto.entrada;

import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TurnoEntradaDto {


    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de turno requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaHora;

    @NotNull(message = "El odontologo no puede ser nulo")
    @Valid
    private OdontologoEntradaDto odontologoEntradaDto;

    @NotNull(message = "El paciente no puede ser nulo")
    @Valid
    private PacienteEntradaDto pacienteEntradaDto;

    public TurnoEntradaDto() {

    }

    public TurnoEntradaDto(LocalDate fechaHora, OdontologoEntradaDto odontologoEntradaDto, PacienteEntradaDto pacienteEntradaDto) {
        this.fechaHora = fechaHora;
        this.odontologoEntradaDto = odontologoEntradaDto;
        this.pacienteEntradaDto = pacienteEntradaDto;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }

    public OdontologoEntradaDto getOdontologoEntradaDto() {
        return odontologoEntradaDto;
    }

    public void setOdontologoEntradaDto(OdontologoEntradaDto odontologoEntradaDto) {
        this.odontologoEntradaDto = odontologoEntradaDto;
    }

    public PacienteEntradaDto getPacienteEntradaDto() {
        return pacienteEntradaDto;
    }

    public void setPacienteEntradaDto(PacienteEntradaDto pacienteEntradaDto) {
        this.pacienteEntradaDto = pacienteEntradaDto;
    }
}
