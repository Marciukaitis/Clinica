package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.repository.OdontologoRepository;
import com.backend.clinica.repository.PacienteRepository;
import com.backend.clinica.repository.TurnoRepository;
import com.backend.clinica.service.ITurnoService;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private OdontologoRepository odontologoRepository;
    private PacienteRepository pacienteRepository;
    private TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;

    public TurnoService(OdontologoRepository odontologoRepository, PacienteRepository pacienteRepository, TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    @Override
    public TurnoSalidaDto guardarTurno(TurnoEntradaDto turno) {
        // verificar que paciente y odontologo exista


        LOGGER.info("TurnoEntradaDto:{}", JsonPrinter.toString(turno));
        Turno entidadTurno = modelMapper.map(turno,Turno.class);
        LOGGER.info("EntidadTurno: {}",JsonPrinter.toString(entidadTurno));
        Turno turnoGuardado = turnoRepository.save(entidadTurno);
        LOGGER.info("TurnoGuardado: {}", JsonPrinter.toString(turnoGuardado));
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoGuardado, TurnoSalidaDto.class);
        LOGGER.info("TurnoSalidaDto: {}", JsonPrinter.toString(turnoSalidaDto));

        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnoSalidaDtos = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnoSalidaDtos));

        return turnoSalidaDtos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        LOGGER.info("Turno buscado : {}",JsonPrinter.toString(turnoBuscado));
        TurnoSalidaDto turnoEncontrado = null;
        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado : {}", JsonPrinter.toString(turnoEncontrado));
        }else LOGGER.error("No se ha encontrado el turno con id{}", id);

        return turnoEncontrado;
    }

    @Override
    public void eliminarTurno(Long id) {
    if (buscarTurnoPorId(id) != null) {
        turnoRepository.deleteById(id);
        LOGGER.warn("Se ha eliminado el turno con id {}", id);
    } else {
        // excepciones
    }
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) {
        Turno turnoParaActualizar = turnoRepository.findById(id).orElse(null);
        Turno turnoRecibido = modelMapper.map(turnoEntradaDto, Turno.class);
        TurnoSalidaDto turnoSalidaDto = null;

        if(turnoParaActualizar != null){
            turnoRecibido.setId(turnoParaActualizar.getId());
            turnoParaActualizar = turnoRecibido;
            turnoRepository.save(turnoParaActualizar);
            turnoSalidaDto = modelMapper.map(turnoParaActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado {}", JsonPrinter.toString(turnoSalidaDto));
        } else LOGGER.error(" No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
        //excepcion

        return turnoSalidaDto;
    }


    private void configureMapping(){
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getOdontologoEntradaDto,Turno::setOdontologo))
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getPacienteEntradaDto, Turno::setPaciente));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getOdontologo,TurnoSalidaDto::setOdontologo))
                .addMappings(mapper -> mapper.map(Turno::getPaciente,TurnoSalidaDto::setPaciente));

    }
}
