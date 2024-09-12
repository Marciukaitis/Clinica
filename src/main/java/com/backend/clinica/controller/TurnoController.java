package com.backend.clinica.controller;

import com.backend.clinica.dto.entrada.TurnoEntradaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.dto.salida.TurnoSalidaDto;
import com.backend.clinica.exceptions.BadRequestException;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.service.ITurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//localhost:8080/swagger-ui.html
@RestController
@RequestMapping("turnos")
@CrossOrigin   // (http://127.0.0.1:5500/ ))
public class TurnoController {

    private ITurnoService iTurnoService;

    public TurnoController(ITurnoService iTurnoService) {
        this.iTurnoService = iTurnoService;
    }

    @Operation(summary = "Registro de un nuevo turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno registrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/guardar")
    public ResponseEntity<TurnoSalidaDto> guardarTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto) throws BadRequestException {
        TurnoSalidaDto turnoSalidaDto = iTurnoService.guardarTurno(turnoEntradaDto);
        return new ResponseEntity<>(turnoSalidaDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Listado de todos los turnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de turnos obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos() {
        return new ResponseEntity<>(iTurnoService.listarTurnos(), HttpStatus.OK);
    }

    @Operation(summary = "Búsqueda de un turno por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(iTurnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }
    @Operation(summary = "Actualización de un turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TurnoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoEntradaDto turno, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(iTurnoService.actualizarTurno(turno, id), HttpStatus.OK);
    }
    @Operation(summary = "Eliminación de un turno por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turno eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarTurno(@RequestParam Long id) throws ResourceNotFoundException {
        iTurnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.NO_CONTENT);
    }

}


//{
//        "fechaHora": "2024-09-30T10:00:00",
//        "paciente": {
//        "nombre": "Juancito",
//        "apellido": "Lopez",
//        "dni": 23124,
//        "fechaIngreso": "2024-09-25",
//        "domicilioEntradaDto": {
//        "calle": "Av. Libertador",
//        "numero": 1234,
//        "localidad": "Buenos Aires",
//        "provincia": "Buenos Aires"
//        }
//        },
//        "odontologo": {
//        "nmatricula": "4564",
//        "nombre": "Pepe",
//        "apellido": "Perez"
//        }
//        }