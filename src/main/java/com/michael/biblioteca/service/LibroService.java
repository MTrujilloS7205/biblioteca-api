package com.michael.biblioteca.service;

import com.michael.biblioteca.entity.LibroEntity;
import java.util.List;

public interface LibroService {

  List<LibroEntity> listarLibros();

  LibroEntity guardarLibro(LibroEntity libroEntity);

  LibroEntity obtenerLibro(Long id);

  LibroEntity modificarLibro(Long id, LibroEntity libroEntity);

  void eliminarLibro(Long id);

  List<LibroEntity> listarLibrosPorAutor(Long autorId);
  
}
