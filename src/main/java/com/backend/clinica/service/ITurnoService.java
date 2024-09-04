package com.backend.clinica.service;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import java.util.List;



public interface ITurnoService {

    TurnoSalidaDto guardarTurno(TurnoEntradaDto turno);
    List<TurnoSalidaDto> listarTurnos();
    TurnoSalidaDto buscarTurnoPorId(Long id);
    void eliminarTurno(Long id);
    TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id);
}

