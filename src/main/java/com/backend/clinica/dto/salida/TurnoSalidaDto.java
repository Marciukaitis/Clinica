package com.backend.clinica.dto.salida;
import java.time.LocalDate;



public class TurnoSalidaDto {

    private Long id;
    private PacienteSalidaDto paciente;
    private OdontologoSalidaDto odontologo;
    private LocalDate fechaHora;

    public TurnoSalidaDto(){

    }

    public TurnoSalidaDto(Long id, PacienteSalidaDto paciente, OdontologoSalidaDto odontologo, LocalDate fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PacienteSalidaDto getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteSalidaDto paciente) {
        this.paciente = paciente;
    }

    public OdontologoSalidaDto getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(OdontologoSalidaDto odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDate getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDate fechaHora) {
        this.fechaHora = fechaHora;
    }
}
