package com.backend.clinica.controller;


import com.backend.clinica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinica.dto.salida.OdontologoSalidaDto;
import com.backend.clinica.dto.salida.PacienteSalidaDto;
import com.backend.clinica.exceptions.ResourceNotFoundException;
import com.backend.clinica.service.IOdontologoService;
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
@RequestMapping("odontologos")
public class OdontologoController {

    private IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }
    @Operation(summary = "Registro de un nuevo odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontologo registrado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/guardar")
    public ResponseEntity<OdontologoSalidaDto> guardarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologoEntradaDto) {
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.guardarOdontologo(odontologoEntradaDto);
        return new ResponseEntity<>(odontologoSalidaDto, HttpStatus.CREATED);
    }
    @Operation(summary = "Listado de todos los odontologos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de odontologos obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })

    @GetMapping("/listar")
    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologos() {
        return new ResponseEntity<>(odontologoService.listarTodos(), HttpStatus.OK);
    }
    @Operation(summary = "Búsqueda de un odontologo por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontologo obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontologo no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoSalidaDto> buscarOdontologoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id), HttpStatus.OK);
    }
    @Operation(summary = "Actualización de un odontologo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontologo actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OdontologoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontologo no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<OdontologoSalidaDto> actualizarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologo, @PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(odontologoService.actualizarOdontologo(odontologo, id), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación de un odontologo por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Odontologo eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontologo no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarOdontologo(@RequestParam Long id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontologo eliminado", HttpStatus.NO_CONTENT);
    }
}


//{
//     "nmatricula": "4564",
//    "nombre": "Pepe",
//    "apellido": "Perez"
//    }