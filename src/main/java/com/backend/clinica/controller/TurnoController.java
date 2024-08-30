package com.backend.clinica.controller;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.service.ITurnoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turnos")
public class TurnoController {

    private ITurnoService iTurnoService;

    public TurnoController(ITurnoService iTurnoService) {
        this.iTurnoService = iTurnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<TurnoSalidaDto> guardarTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto) {
        TurnoSalidaDto turnoSalidaDto = iTurnoService.guardarTurno(turnoEntradaDto);
        return new ResponseEntity<>(turnoSalidaDto, HttpStatus.CREATED);
    }


    @GetMapping("/listar")
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos(){
        return new ResponseEntity<>(iTurnoService.listarTurnos(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id){
        return new ResponseEntity<>(iTurnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoEntradaDto turno, @PathVariable Long id){
        return new ResponseEntity<>(iTurnoService.actualizarTurno(turno, id), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarTurno(@RequestParam Long id){
        iTurnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.NO_CONTENT);
    }

}
