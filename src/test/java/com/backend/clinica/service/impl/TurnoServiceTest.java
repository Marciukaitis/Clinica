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

        private final TurnoRepository turnoRepositoryMock = mock(TurnoRepository.class);
        private final PacienteService pacienteServiceMock = mock(PacienteService.class);
        private final OdontologoService odontologoServiceMock = mock(OdontologoService.class);
        private ModelMapper modelMapper = new ModelMapper();
        private TurnoEntradaDto turnoEntradaDto;
        private Turno turno;
        private PacienteSalidaDto pacienteSalidaDto;
        private OdontologoSalidaDto odontologoSalidaDto;
        private TurnoService turnoService = new TurnoService(odontologoServiceMock, pacienteServiceMock, turnoRepositoryMock, modelMapper);

        @BeforeEach
        void setUp() {

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
       public void DeberiaGuardarTurnoConPacienteYOdontologoExistentes() throws BadRequestException {

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



        @Test
        public void deberiaListarTodosLosTurnos() {
            List<Turno> turnos = List.of(turno);

            when(turnoRepositoryMock.findAll()).thenReturn(turnos);

            List<TurnoSalidaDto> listadoDeTurnos = turnoService.listarTurnos();

            assertNotNull(listadoDeTurnos);
            assertEquals(1, listadoDeTurnos.size());
        }
    @Test
    void deberiaLanzarBadRequestExceptionCuandoFaltaPacienteYOdontologo() {
        when(pacienteServiceMock.buscarPacientePorDni(anyInt())).thenReturn(null);
        when(odontologoServiceMock.buscarOdontologoPorMatricula("6645")).thenReturn(null);

        assertThrows(BadRequestException.class, () -> turnoService.guardarTurno(turnoEntradaDto));

        verify(pacienteServiceMock, times(1)).buscarPacientePorDni(anyInt());
        verify(odontologoServiceMock, times(1)).buscarOdontologoPorMatricula(anyString());
    }

    @Test
    void deberiaEliminarTurnoConExito()  {
        when(turnoRepositoryMock.findById(anyLong())).thenReturn(Optional.of(turno));
        doNothing().when(turnoRepositoryMock).deleteById(anyLong());

        assertDoesNotThrow(() -> turnoService.eliminarTurno(1L));

        verify(turnoRepositoryMock, times(1)).findById(anyLong());
        verify(turnoRepositoryMock, times(1)).deleteById(anyLong());
    }


    }




