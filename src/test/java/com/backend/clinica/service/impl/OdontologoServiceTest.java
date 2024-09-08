package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.repository.OdontologoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class OdontologoServiceTest {

    private final OdontologoRepository odontologoRepositoryMock = mock(OdontologoRepository.class);
    private final ModelMapper modelMapper = new ModelMapper();
    private final OdontologoService odontologoService = new OdontologoService(odontologoRepositoryMock,modelMapper);
    private static OdontologoEntradaDto odontologoEntradaDto;
    private static Odontologo odontologo;

    @BeforeAll
    static void setUp(){

        odontologo = new Odontologo(1L,"5654","Marcelo","Tinelli");

        odontologoEntradaDto = new OdontologoEntradaDto("5654","Marcelo","Tinelli");
    }

    @Test
    void deberiaActualizarApellidoDelOdontologo_YRetornarloActualizado() throws ResourceNotFoundException {
        String nuevoApellido = "Mendoza";
        odontologoEntradaDto.setApellido(nuevoApellido);
        when(odontologoRepositoryMock.findById(1L)).thenReturn(Optional.of(odontologo));

        Odontologo odontologoActualizado = new Odontologo("5654", "Marcelo", nuevoApellido);
        when(odontologoRepositoryMock.save(any(Odontologo.class))).thenReturn(odontologoActualizado);
        OdontologoSalidaDto odontologoGuardado = odontologoService.actualizarOdontologo(odontologoEntradaDto, 1L);
        assertNotNull(odontologoGuardado);
        assertEquals(1L, odontologoGuardado.getId());
        assertEquals(nuevoApellido, odontologoGuardado.getApellido());
        verify(odontologoRepositoryMock, times(1)).findById(1L);
        verify(odontologoRepositoryMock, times(1)).save(any(Odontologo.class));

    }

    @Test
    void deberiaBuscarAlPacienteConNumeroDeMatricula_YRetornarlo() {
        when(odontologoRepositoryMock.findByNmatricula("5654")).thenReturn(odontologo);
        OdontologoSalidaDto odontologoEncontrado = odontologoService.buscarOdontologoPorMatricula("5654");

        assertNotNull(odontologoEncontrado);
        assertEquals("5654",odontologoEncontrado.getNmatricula());

        verify(odontologoRepositoryMock,times(1)).findByNmatricula("5654");

    }

    @Test
    void deberiaEliminarElOdontologoConId1(){
        when(odontologoRepositoryMock.findById(1L)).thenReturn(Optional.of(odontologo));
        doNothing().when(odontologoRepositoryMock).deleteById(1L);

        assertDoesNotThrow(() -> odontologoService.eliminarOdontologo(1L));

        verify(odontologoRepositoryMock, times(1)).deleteById(1L);

    }

}