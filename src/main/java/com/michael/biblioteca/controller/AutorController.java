package com.michael.biblioteca.controller;

import com.michael.biblioteca.entity.AutorEntity;
import com.michael.biblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Autores",
        description = "API para gestionar autores del sistema biblioteca"
)

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

  private final AutorService autorService;

  /* ---------------- GET - LISTAR TODOS LOS AUTORES ------------------------ */
  @Operation(
          summary = "Listar todos los autores",
          description = "Devuelve una lista con todos los autores registrados en la base de datos"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de autores obtenida correctamente"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping
  public List<AutorEntity> listarAutores() {
    return autorService.listarAutores();
  }

  /* ---------------- GET - OBTENER UN AUTOR POR SU ID ---------------------- */
  @Operation(
          summary = "Obtener autor por ID",
          description = "Devuelve un autor específico según el ID proporcionado"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Autor encontrado"),
    @ApiResponse(responseCode = "404", description = "Autor no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping("/{id}")
  public AutorEntity obtenerAutor(
          @Parameter(description = "Identificador único del autor")
          @PathVariable Long id) {
    return autorService.obtenerAutor(id);
  }

  /* ---------------- POST - GUARDAR UN AUTOR ------------------------------- */
  @Operation(
          summary = "Registrar un nuevo autor",
          description = "Crea un nuevo autor en el sistema con los datos proporcionados"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Autor creado correctamente"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "409", description = "El autor ya existe"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @PostMapping
  public ResponseEntity<AutorEntity> guardarAutor(
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "Datos del autor a registrar")
          @RequestBody AutorEntity autorEntity) {

    AutorEntity autor = autorService.guardarAutor(autorEntity);
    return ResponseEntity.status(201).body(autor);
  }

  /* ---------------- PUT - MODIFICAR UN AUTOR EXISTENTE -------------------- */
  @Operation(
          summary = "Actualizar un autor",
          description = "Modifica los datos de un autor existente según su ID proporcionado"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Autor actualizado correctamente"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "404", description = "Autor no encontrado"),
    @ApiResponse(responseCode = "409", description = "El autor ya existe"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @PutMapping("/{id}")
  public AutorEntity modificarAutor(
          @Parameter(description = "ID del autor a actualizar")
          @PathVariable Long id,
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "Nuevos datos del autor")
          @RequestBody AutorEntity autorEntity) {
    return autorService.modificarAutor(id, autorEntity);
  }

  /* ---------------- DELETE - ELIMINAR UN AUTOR EXISTENTE ------------------ */
  @Operation(
          summary = "Eliminar un autor",
          description = "Elimina un autor del sistema según el ID proporcionado"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Autor eliminado correctamente"),
    @ApiResponse(responseCode = "404", description = "Autor no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarAutor(
          @Parameter(description = "ID del autor a eliminar")
          @PathVariable Long id) {
    autorService.eliminarAutor(id);
    return ResponseEntity.noContent().build();
  }
}
