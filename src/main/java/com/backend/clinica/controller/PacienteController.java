package com.backend.clinica.controller;

import com.backend.clinica.dto.entrada.PacienteEntradaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.service.IPacienteService;
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


@CrossOrigin
@RestController
@RequestMapping("pacientes")
public class PacienteController {

    private IPacienteService pacienteService;


    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(summary = "Registro de un nuevo paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente registrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<PacienteSalidaDto> registrarPaciente(@RequestBody @Valid PacienteEntradaDto pacienteEntradaDto) {
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);
        return new ResponseEntity<>(pacienteSalidaDto, HttpStatus.CREATED);
    }
    @Operation(summary = "Listado de todos los pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de pacientes obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteSalidaDto>> listarPacientes() {
        return new ResponseEntity<>(pacienteService.listarPacientes(), HttpStatus.OK);
    }
    @Operation(summary = "Búsqueda de un paciente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteSalidaDto> buscarPacientePorId(@PathVariable Long id) {
        return new ResponseEntity<>(pacienteService.buscarPacientePorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Actualización de un paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PacienteSalidaDto> actualizarPaciente(@RequestBody @Valid PacienteEntradaDto paciente, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(pacienteService.actualizarPaciente(paciente, id), HttpStatus.OK);
    }
    @Operation(summary = "Eliminación de un paciente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarPaciente(@RequestParam Long id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return new ResponseEntity<>("Paciente eliminado correctamente", HttpStatus.NO_CONTENT);
    }

}

//
//{
//    "nombre": "Juancito",
//        "apellido": "Lopez",
//        "dni": 23124,
//        "fechaIngreso": "2024-09-25",
//        "domicilioEntradaDto":
//        {
//        "calle": "Av. Libertador",
//        "numero": 1234,
//        "localidad": "Buenos Aires",
//        "provincia": "Buenos Aires"
//        }}
