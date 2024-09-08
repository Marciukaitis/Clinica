package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.DomicilioSalidaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.entity.Domicilio;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.entity.Turno;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.repository.TurnoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@SpringBootTest
class TurnoServiceTest {

    private TurnoRepository turnoRepositoryMock;
    private PacienteService pacienteServiceMock;
    private OdontologoService odontologoServiceMock;
    private ModelMapper modelMapper = new ModelMapper();
    private TurnoEntradaDto turnoEntradaDto;
    private Turno turno;
    private PacienteSalidaDto pacienteSalidaDto;
    private OdontologoSalidaDto odontologoSalidaDto;
    private TurnoService turnoService = new TurnoService(odontologoServiceMock, pacienteServiceMock, turnoRepositoryMock, modelMapper);

 @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);

        turno = new Turno(1L,
                new Paciente(1L, "Zulma", "Lobato", 556677, LocalDate.of(2024, 9, 18),
                        new Domicilio(1L, "Avispas", 123, "unaLocalidad", "laProvincia")),
                new Odontologo("1343", "Pedro", "Alfonso"),
                LocalDateTime.of(2024, 10, 24, 10, 0));

        turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.of(2024, 10, 24, 10, 0),
                new OdontologoEntradaDto("1343", "Pedro", "Alfonso"),
                new PacienteEntradaDto("Zulma", "Lobato", 556677, LocalDate.of(2024, 9, 18),
                        new DomicilioEntradaDto("Avispas", 123, "unaLocalidad", "laProvincia")));

        pacienteSalidaDto = new PacienteSalidaDto(1L, "Zulma", "Lobato", 556677, LocalDate.of(2024, 9, 18),
                new DomicilioSalidaDto(1L, "Avispas", 123, "unaLocalidad", "laProvincia"));

        odontologoSalidaDto = new OdontologoSalidaDto(1L, "1343", "Pedro", "Alfonso");
    }

    @Test
    void deberiaGuardarTurnoConExito() throws BadRequestException {

        when(pacienteServiceMock.buscarPacientePorDni(556677)).thenReturn(pacienteSalidaDto);
        when(odontologoServiceMock.buscarOdontologoPorMatricula("1343")).thenReturn(odontologoSalidaDto);
        when(turnoRepositoryMock.save(any(Turno.class))).thenReturn(turno);

        TurnoSalidaDto resultado = turnoService.guardarTurno(turnoEntradaDto);

        assertNotNull(resultado);
        assertEquals(turno.getId(), resultado.getId());
        verify(turnoRepositoryMock, times(1)).save(any(Turno.class));
    }

    @Test
    void deberiaBuscarTurnoPorId_YRetornarlo() {
        when(turnoRepositoryMock.findById(1L)).thenReturn(Optional.of(turno));

        TurnoSalidaDto resultado = turnoService.buscarTurnoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(turnoRepositoryMock, times(1)).findById(1L);
    }


//    @Test
//    public void DeberiaGuardarTurnoPacienteYOdontologoExistentes() throws BadRequestException {
//        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.now(), new OdontologoEntradaDto(), new PacienteEntradaDto());
//
//        Turno turno = new Turno(1L, new Paciente(), new Odontologo(), LocalDateTime.now());
//
//        when(pacienteServiceMock.buscarPacientePorDni(556677)).thenReturn(pacienteSalidaDto);
//        when(odontologoServiceMock.buscarOdontologoPorMatricula("1343")).thenReturn(odontologoSalidaDto);
//        when(turnoRepositoryMock.save(any(Turno.class))).thenReturn(turno);
//
//        TurnoSalidaDto resultado = turnoService.guardarTurno(turnoEntradaDto);
//
//        assertNotNull(resultado);
//        assertEquals(1L, resultado.getId());
//    }


    @Test
    public void DeberiaListarTodosLosTurnos() {
        List<Turno> turnos = List.of(turno);

        when(turnoRepositoryMock.findAll()).thenReturn(turnos);

        List<TurnoSalidaDto> listadoDeTurnos = turnoService.listarTurnos();

        assertNotNull(listadoDeTurnos);
        assertEquals(1, listadoDeTurnos.size());
    }

}


