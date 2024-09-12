package com.backend.clinica.repository;

import com.backend.clinica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno,Long> {

//    Turno findByPacienteAndOdontologoAndFechaHora(int pacienteDni, String odontologoMatricula, LocalDateTime fechaHora);
}


