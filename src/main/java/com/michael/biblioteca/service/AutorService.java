package com.michael.biblioteca.service;

import com.michael.biblioteca.entity.AutorEntity;
import java.util.List;

public interface AutorService {

  List<AutorEntity> listarAutores();

  AutorEntity guardarAutor(AutorEntity autorEntity);

  AutorEntity obtenerAutor(Long id);

  AutorEntity modificarAutor(Long id, AutorEntity autorEntity);

  void eliminarAutor(Long id);

}
