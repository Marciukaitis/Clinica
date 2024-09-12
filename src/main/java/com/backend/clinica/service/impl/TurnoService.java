package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
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

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final OdontologoService odontologoService;
    private final ModelMapper modelMapper;
    private PacienteService pacienteService;
    private TurnoRepository turnoRepository;


    public TurnoService(OdontologoService odontologoService, PacienteService pacienteService, TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    @Override
    public TurnoSalidaDto guardarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException {


        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turnoEntradaDto));
        TurnoSalidaDto turnoSalidaDto = null;

        int pacienteDni = turnoEntradaDto.getPaciente().getDni();
        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorDni(pacienteDni);
        String odontologoMatricula = turnoEntradaDto.getOdontologo().getNmatricula();
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorMatricula(odontologoMatricula);

        if (pacienteSalidaDto == null && odontologoSalidaDto == null) {
            LOGGER.error("No existen ni el odontologo " + odontologoMatricula + " ni el paciente " + pacienteDni);
            throw new BadRequestException("El paciente y el odontologo no se encuentran en la base de datos");

        } else if (pacienteSalidaDto == null) {
            LOGGER.error("No existe el paciente " + pacienteDni);
            throw new BadRequestException("El paciente no se encuentran en la base de datos");

        } else if (odontologoSalidaDto == null) {
            LOGGER.error("No existe el odontologo " + odontologoMatricula);
            throw new BadRequestException("El odontologo no se encuentran en la base de datos");

        }

        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turnoEntradaDto));
        Turno entidadTurno = modelMapper.map(turnoEntradaDto, Turno.class);

        LOGGER.info("EntidadTurno: {}", JsonPrinter.toString(entidadTurno));

        Paciente paciente = modelMapper.map(pacienteSalidaDto, Paciente.class);
        Odontologo odontologo = modelMapper.map(odontologoSalidaDto, Odontologo.class);
        entidadTurno.setPaciente(paciente);
        entidadTurno.setOdontologo(odontologo);

        Turno turnoRegistrado = turnoRepository.save(entidadTurno);
        LOGGER.info("turnoRegistrado: {}", JsonPrinter.toString(turnoRegistrado));

        turnoSalidaDto = modelMapper.map(turnoRegistrado, TurnoSalidaDto.class);
        LOGGER.info("turnoSalidaDto: {}", JsonPrinter.toString(turnoSalidaDto));

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
        LOGGER.info("Turno buscado : {}", JsonPrinter.toString(turnoBuscado));
        TurnoSalidaDto turnoEncontrado = null;
        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado : {}", JsonPrinter.toString(turnoEncontrado));
        } else LOGGER.error("No se ha encontrado el turno con id{}", id);

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

        if (turnoParaActualizar != null) {
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


    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getFechaHora, Turno::setFechaHora));

        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto))
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto));

        modelMapper.typeMap(OdontologoSalidaDto.class, Odontologo.class);
        modelMapper.typeMap(PacienteSalidaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteSalidaDto::getDomicilioSalidaDto, Paciente::setDomicilio));


    }
}
