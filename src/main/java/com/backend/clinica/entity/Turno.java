package com.backend.clinica.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TURNOS")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@ManyToOne
//    @OneToOne (cascade = CascadeType.MERGE)
//    @OneToOne
//    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

@ManyToOne
//    @OneToOne (cascade = CascadeType.MERGE)
    //CascadeType.MERGE
//    @OneToOne (cascade = CascadeType.ALL)
//    @OneToOne
    @JoinColumn(name = "odontologo_id")
    private Odontologo odontologo;

    @Column(length = 30,nullable = false)
    private LocalDateTime fechaHora;


    public Turno(){


    }
    public Turno(Long id, Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fechaHora = fechaHora;
    }

    public Turno(Paciente paciente, Odontologo odontologo, LocalDateTime fechaHora) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Odontologo getOdontologo() {
        return odontologo;
    }

    public void setOdontologo(Odontologo odontologo) {
        this.odontologo = odontologo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
