package com.backend.clinica.service.impl;

import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.entity.Odontologo;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.repository.OdontologoRepository;
import com.backend.clinica.service.IOdontologoService;
import com.backend.clinica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OdontologoService implements IOdontologoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    @Override
    public OdontologoSalidaDto guardarOdontologo (OdontologoEntradaDto odontologo) {
        LOGGER.info("OdontologoEntradaDto:{}", JsonPrinter.toString(odontologo));
        Odontologo entidadOdontologo = modelMapper.map(odontologo,Odontologo.class);
        LOGGER.info("EntidadOdontologo: {}",JsonPrinter.toString(entidadOdontologo));
        Odontologo odontologoGuardado = odontologoRepository.save(entidadOdontologo);
        LOGGER.info("OdontologoGuardado: {}", JsonPrinter.toString(odontologoGuardado));
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoGuardado, OdontologoSalidaDto.class);
        LOGGER.info("OdontologoSalidaDto: {}", JsonPrinter.toString(odontologoSalidaDto));

        return odontologoSalidaDto;
    }

    @Override
    public List<OdontologoSalidaDto> listarTodos() {
        List<OdontologoSalidaDto> odontologoSalidaDtos = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo,OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los odontologos: {}",JsonPrinter.toString(odontologoSalidaDtos));
        return odontologoSalidaDtos;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        LOGGER.info("Odontologo buscado : {}", JsonPrinter.toString(odontologoBuscado));
        OdontologoSalidaDto odontologoEncontrado = null;
        if(odontologoBuscado != null) {
            odontologoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            LOGGER.info("Odontologo encontrado: {}", JsonPrinter.toString(odontologoEncontrado));
        }else LOGGER.error("No se ha encontrado el odontologo con id {}", id);

        return odontologoEncontrado;
        }

    @Override
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        if(buscarOdontologoPorId(id) != null){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id {}", id);
        } else {
            throw new ResourceNotFoundException("No existe el paciente con id " + id);
        }
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) throws ResourceNotFoundException {
        Odontologo odontologoParaActualizar = odontologoRepository.findById(id).orElse(null);
        Odontologo odontologoRecibido = modelMapper.map(odontologoEntradaDto,Odontologo.class);
        OdontologoSalidaDto odontologoSalidaDto = null;

        if(odontologoParaActualizar != null) {
            odontologoRecibido.setId(odontologoParaActualizar.getId());
            odontologoParaActualizar = odontologoRecibido;

            odontologoRepository.save(odontologoParaActualizar);
            odontologoSalidaDto = modelMapper.map(odontologoParaActualizar, OdontologoSalidaDto.class);
            LOGGER.warn("Odontologo actualizado : {}", JsonPrinter.toString(odontologoSalidaDto));
        }  else {
        LOGGER.error("No fue posible actualizar el odontologo porque no se encuentra en nuestra base de datos");
        throw new ResourceNotFoundException("No fue posible actualizar el odontologo porque no se encuentra en nuestra base de datos");
    }
        return odontologoSalidaDto;
    }


    public Odontologo buscarOdontologoPorMatricula(String nmatricula) {
        Odontologo odontologoBuscado = odontologoRepository.findByNmatricula(nmatricula);
        LOGGER.info("Odontologo buscado : {}", JsonPrinter.toString(odontologoBuscado));

        if(odontologoBuscado == null) {
            LOGGER.error("No se ha encontrado el odontologo con numero de matricula {}", nmatricula);
//            throw new RuntimeException();
        }

        return odontologoBuscado;
    }


    private void configureMapping(){
        modelMapper.typeMap(OdontologoEntradaDto.class, Odontologo.class);
        modelMapper.typeMap(Odontologo.class, OdontologoSalidaDto.class);
    }

    }
