package com.michael.biblioteca.controller;

import com.michael.biblioteca.entity.LibroEntity;
import com.michael.biblioteca.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        name = "Libros",
        description = "API para gestionar libros del sistema biblioteca"
)

@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
public class LibroController {

  private final LibroService libroService;

  /* ------------------- GET - LISTAR TODOS LOS LIBROS ---------------------- */
  @Operation(
          summary = "Listar todos los libros",
          description = "Devuelve una lista con todos los libros registrados en el sistema"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de libros obtenida correctamente"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping
  public List<LibroEntity> listarLibros() {
    return libroService.listarLibros();
  }

  /* ------------------- GET - OBTENER UN LIBRO POR SU ID ------------------- */
  @Operation(
          summary = "Obtener libro por ID",
          description = "Devuelve un libro específico según el ID proporcionado"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Libro encontrado"),
    @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping("/{id}")
  public LibroEntity obtenerLibro(
          @Parameter(description = "Identificador único del libro")
          @PathVariable Long id) {
    return libroService.obtenerLibro(id);
  }

  /* ------------------- POST - GUARDA UN LIBRO ----------------------------- */
  @Operation(
          summary = "Registrar un nuevo libro",
          description = "Crea un nuevo libro en el sistema con los datos proporcionados"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Libro creado correctamente"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "409", description = "El libro ya existe"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @PostMapping
  public ResponseEntity<LibroEntity> guardarLibro(
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "Datos del libro a registrar")
          @RequestBody LibroEntity libroEntity) {

    LibroEntity libro = libroService.guardarLibro(libroEntity);
    return ResponseEntity.status(201).body(libro);
  }

  /* ------------------- PUT - MODIFICAR UN LIBRO EXISTENTE ----------------- */
  @Operation(
          summary = "Actualizar un libro",
          description = "Modifica los datos de un libro existente según su ID proporcionado"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
    @ApiResponse(responseCode = "409", description = "El libro ya existe"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @PutMapping("/{id}")
  public LibroEntity modificarLibro(
          @Parameter(description = "ID del libro a actualizar")
          @PathVariable Long id,
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                  description = "Nuevos datos del libro")
          @RequestBody LibroEntity libroEntity) {
    return libroService.modificarLibro(id, libroEntity);
  }

  /* ------------------- DELETE - ELIMINAR UN LIBRO ------------------------- */
  @Operation(
          summary = "Eliminar un libro",
          description = "Elimina un libro del sistema según el ID proporcionado"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
    @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarLibro(
          @Parameter(description = "ID del libro a eliminar")
          @PathVariable Long id) {
    libroService.eliminarLibro(id);
    return ResponseEntity.noContent().build();
  }

  /* ------------------- GET - LISTAR LOS LIBROS DE UN AUTOR ---------------- */
  @Operation(
          summary = "Listar libros por autor",
          description = "Devuelve una lista de libros asociados a un autor específico según su ID"
  )
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista de libros obtenida correctamente"),
    @ApiResponse(responseCode = "404", description = "ID de autor no encontrado"),
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
  })
  @GetMapping("/autor/{id}")
  public List<LibroEntity> listarLibrosPorAutor(
          @Parameter(description = "ID del autor para listar sus libros")
          @PathVariable("id") Long autorId) {
    return libroService.listarLibrosPorAutor(autorId);
  }
}
