package com.michael.biblioteca.service.impl;

import com.michael.biblioteca.entity.AutorEntity;
import com.michael.biblioteca.exception.RecursoDuplicadoException;
import com.michael.biblioteca.exception.RecursoNoEncontradoException;
import com.michael.biblioteca.repository.AutorRepository;
import com.michael.biblioteca.service.AutorService;
import com.michael.biblioteca.util.LogUtils;
import static com.michael.biblioteca.util.StringUtils.limpiarTexto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AutorServiceImpl implements AutorService {

  private final AutorRepository autorRepository;

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public List<AutorEntity> listarAutores() {
    LogUtils.info("Listando todos los autores");
    List<AutorEntity> autores = autorRepository.findAll();
    LogUtils.info("Se encontraron " + autores.size() + " autores");
    return autores;
  }


  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public AutorEntity guardarAutor(AutorEntity autorEntity) {

    String nombre = limpiarTexto(autorEntity.getNombre());
    if (nombre == null) {
      LogUtils.warn("Nombre de autor inválido");
      throw new IllegalArgumentException("El nombre del autor es obligatorio");
    }
    LogUtils.info("Intentando guardar autor con nombre '" + nombre + "'");

    if (existeAutorPorNombre(nombre)) {
      LogUtils.warn("No se pudo guardar el autor, nombre duplicado: '" + nombre + "'");
      throw new RecursoDuplicadoException("El nombre del autor ya existe");
    }
    autorEntity.setNombre(nombre);

    String nacionalidad = limpiarTexto(autorEntity.getNacionalidad());
    autorEntity.setNacionalidad(nacionalidad);
    
    AutorEntity guardado = autorRepository.save(autorEntity);
    LogUtils.info("Autor guardado exitosamente con ID " + guardado.getId());
    return guardado;
  }


  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public AutorEntity obtenerAutor(Long id) {
    LogUtils.info("Obteniendo autor con ID " + id);
    return autorRepository.findById(id)
      .orElseThrow(() -> {
        LogUtils.error("Autor con ID " + id + ", no encontrado");
        return new RecursoNoEncontradoException("Autor no encontrado");
      });
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public AutorEntity modificarAutor(Long id, AutorEntity autorEntity) {
    LogUtils.info("Modificando autor con ID " + id);
    AutorEntity autor = obtenerAutor(id);

    String nombre = limpiarTexto(autorEntity.getNombre());
    if (nombre == null) {
      LogUtils.warn("Nombre de autor inválido");
      throw new IllegalArgumentException("El nombre del autor es obligatorio");
    }
    LogUtils.info("Intentando modificar autor ID " + id + " con nombre '" + nombre + "'");
    
    String nacionalidad = limpiarTexto(autorEntity.getNacionalidad());
    
    if (!autor.getNombre().equalsIgnoreCase(nombre)
      && existeAutorPorNombre(nombre)) {
      LogUtils.warn("No se pudo modificar, nombre duplicado: '" + nombre + "'");
      throw new RecursoDuplicadoException("El nombre del autor ya existe");
    }

    autor.setNombre(nombre);
    autor.setNacionalidad(nacionalidad);
    autor.setEstado(autorEntity.getEstado());

    AutorEntity modificado = autorRepository.save(autor);
    LogUtils.info("Autor modificado exitosamente: ID " + modificado.getId());

    return modificado;
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public void eliminarAutor(Long id) {
    LogUtils.info("Elimiando autor con ID " + id);
    AutorEntity autor = obtenerAutor(id);
    autorRepository.delete(autor);
    LogUtils.info("Autor eliminado exitosamente: ID " + id);
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  private boolean existeAutorPorNombre(String nombre) {
    boolean existe = autorRepository.existsByNombreIgnoreCase(nombre);
    LogUtils.info("Validando la existencia de autor '" + nombre + "': " + existe);
    return existe;
  }
}
