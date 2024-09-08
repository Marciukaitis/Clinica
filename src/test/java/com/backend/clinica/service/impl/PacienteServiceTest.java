package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.entity.Domicilio;
import com.backend.clinica.entity.Paciente;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteServiceTest {

    private final PacienteRepository pacienteRepositoryMock = mock(PacienteRepository.class);
    private final ModelMapper modelMapper = new ModelMapper();
    private final PacienteService pacienteService = new PacienteService(pacienteRepositoryMock, modelMapper);
    private static PacienteEntradaDto pacienteEntradaDto;
    private static Paciente paciente;
    @BeforeAll
    static void setUp() {

        paciente = new Paciente(1L, "Zulma", "Lobato", 556677, LocalDate.of(2024, 9, 18),
                new Domicilio(1L, "Avispas", 123, "unaLocalidad", "laProvincia"));

        pacienteEntradaDto = new PacienteEntradaDto("Zulma", "Lobato", 556677, LocalDate.of(2024, 9, 18), new DomicilioEntradaDto("Avispas", 123, "unaLocalidad", "laProvincia"));
    }

    @Test
    void deberiaActualizarFechaDeIngresoDelPaciente_YRetornarPacienteActualizado() throws ResourceNotFoundException {
        LocalDate nuevaFechaIngreso = LocalDate.of(2024, 10, 1);
        pacienteEntradaDto.setFechaIngreso(nuevaFechaIngreso);
        when(pacienteRepositoryMock.findById(1L)).thenReturn(Optional.of(paciente));

        Paciente pacienteActualizado = new Paciente(1L, "Zulma", "Lobato", 556677, nuevaFechaIngreso,
                new Domicilio(1L, "Avispas", 123, "unaLocalidad", "laProvincia"));

        when(pacienteRepositoryMock.save(any(Paciente.class))).thenReturn(pacienteActualizado);

        PacienteSalidaDto resultado = pacienteService.actualizarPaciente(pacienteEntradaDto, 1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(nuevaFechaIngreso, resultado.getFechaIngreso());
        verify(pacienteRepositoryMock, times(1)).findById(1L);
        verify(pacienteRepositoryMock, times(1)).save(any(Paciente.class));
    }


    @Test
    void deberiaBuscarAlPacienteConId1_YRetornarlo() {
        when(pacienteRepositoryMock.findById(1L)).thenReturn(Optional.of(paciente));

        PacienteSalidaDto resultado = pacienteService.buscarPacientePorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(pacienteRepositoryMock, times(1)).findById(1L);

    }

    @Test
    void deberiaEliminarPacienteExistente() {
        when(pacienteRepositoryMock.findById(1L)).thenReturn(Optional.of(paciente));
        doNothing().when(pacienteRepositoryMock).deleteById(1L);

        assertDoesNotThrow(() -> pacienteService.eliminarPaciente(1L));

        verify(pacienteRepositoryMock, times(1)).deleteById(1L);
    }


    @Test
    void deberiaDevolverUnaListaConTodosLosPacientes() {
        List<Paciente> pacientes = new ArrayList<>(List.of(paciente));
        when(pacienteRepositoryMock.findAll()).thenReturn(pacientes);

        List<PacienteSalidaDto> listadoDePacientes = pacienteService.listarPacientes();
        assertFalse(listadoDePacientes.isEmpty());

    }


    }

