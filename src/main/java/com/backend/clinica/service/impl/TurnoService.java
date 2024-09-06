package com.backend.clinica.service.impl;
import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.repository.TurnoRepository;
import com.backend.clinica.service.ITurnoService;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final OdontologoService odontologoService;
    private PacienteService pacienteService;
    private TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;


    public TurnoService(OdontologoService odontologoService, PacienteService pacienteService, TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    @Override
    public TurnoSalidaDto guardarTurno(TurnoEntradaDto turno) throws BadRequestException {

        int pacienteDni = turno.getPacienteEntradaDto().getDni();
        Paciente paciente = pacienteService.buscarPacientePorDni(pacienteDni);

        if(paciente == null) {
            throw new BadRequestException("Paciente no encontrado");
        }

        String odontologoMatricula = turno.getOdontologoEntradaDto().getNmatricula();
        Odontologo odontologo =  odontologoService.buscarOdontologoPorMatricula(odontologoMatricula);

        if( odontologo == null) {
            throw new BadRequestException("Odontologo no encontrado");
        }

        if(paciente == null & odontologo == null) {
            throw new BadRequestException("Paciente y Odontologo no fueron encontrados");
        }


        LocalDate fechaHora = turno.getFechaHora();
        Turno turnoRegistrado = new Turno(paciente,odontologo,fechaHora);
        turnoRepository.save(turnoRegistrado);

        return modelMapper.map(turnoRegistrado,TurnoSalidaDto.class);



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
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
    if (buscarTurnoPorId(id) != null) {
        turnoRepository.deleteById(id);
        LOGGER.warn("Se ha eliminado el turno con id {}", id);
    } else {
        throw new ResourceNotFoundException("No existe el paciente con id " + id);
    }
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoEntradaDto turnoEntradaDto, Long id) throws ResourceNotFoundException {
        Turno turnoParaActualizar = turnoRepository.findById(id).orElse(null);
        Turno turnoRecibido = modelMapper.map(turnoEntradaDto, Turno.class);
        TurnoSalidaDto turnoSalidaDto = null;

        if(turnoParaActualizar != null){
            turnoRecibido.setId(turnoParaActualizar.getId());
            turnoParaActualizar = turnoRecibido;
            turnoRepository.save(turnoParaActualizar);
            turnoSalidaDto = modelMapper.map(turnoParaActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado {}", JsonPrinter.toString(turnoSalidaDto));
        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
            throw new ResourceNotFoundException("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
        }

        return turnoSalidaDto;
    }


    private void configureMapping(){
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getOdontologoEntradaDto,Turno::setOdontologo))
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getPacienteEntradaDto, Turno::setPaciente));
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getOdontologo,TurnoSalidaDto::setOdontologoSalidaDto))
                .addMappings(mapper -> mapper.map(Turno::getPaciente,TurnoSalidaDto::setPacienteSalidaDto));

    }
}
