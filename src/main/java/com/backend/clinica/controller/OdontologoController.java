package com.backend.clinica.controller;


import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.service.IOdontologoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("odontologos")
public class OdontologoController {

    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<OdontologoSalidaDto> guardarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologoEntradaDto) {
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.guardarOdontologo(odontologoEntradaDto);
        return new ResponseEntity<>(odontologoSalidaDto, HttpStatus.CREATED);
    }


    @GetMapping("/listar")
    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologos(){
        return new ResponseEntity<>(odontologoService.listarTodos(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoSalidaDto> buscarOdontologoPorId(@PathVariable Long id){
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id),HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<OdontologoSalidaDto> actualizarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologo, @PathVariable Long id){
        return new ResponseEntity<>(odontologoService.actualizarOdontologo(odontologo,id),HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarOdontologo(@RequestParam Long id){
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontologo eliminado",HttpStatus.NO_CONTENT);
    }
}
