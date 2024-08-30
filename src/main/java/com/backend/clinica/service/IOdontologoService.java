package com.backend.clinica.service;

import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto guardarOdontologo(OdontologoEntradaDto odontologo);
    List<OdontologoSalidaDto> listarTodos();
    OdontologoSalidaDto buscarOdontologoPorId(Long id);
    void eliminarOdontologo(Long id);
    OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id);
}
