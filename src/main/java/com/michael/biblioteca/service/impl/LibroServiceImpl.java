package com.michael.biblioteca.service.impl;

import com.michael.biblioteca.entity.AutorEntity;
import com.michael.biblioteca.entity.LibroEntity;
import com.michael.biblioteca.exception.RecursoDuplicadoException;
import com.michael.biblioteca.exception.RecursoNoEncontradoException;
import com.michael.biblioteca.repository.AutorRepository;
import com.michael.biblioteca.repository.LibroRepository;
import com.michael.biblioteca.service.LibroService;
import com.michael.biblioteca.util.LogUtils;
import static com.michael.biblioteca.util.StringUtils.limpiarTexto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class LibroServiceImpl implements LibroService {

  private final AutorRepository autorRepository;
  private final LibroRepository libroRepository;

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public List<LibroEntity> listarLibros() {
    LogUtils.info("Listando todos los libros");
    List<LibroEntity> libros = libroRepository.findAll();
    LogUtils.info("Se encontraron " + libros.size() + " libros");
    return libros;
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public LibroEntity guardarLibro(LibroEntity libroEntity) {

    if (libroEntity.getTitulo() == null || libroEntity.getTitulo().trim().isEmpty()) {
      LogUtils.warn("Título del libro inválido");
      throw new IllegalArgumentException("El título del libro es obligatorio");
    }
    String titulo = libroEntity.getTitulo().trim();

    if (libroEntity.getIsbn() == null || libroEntity.getIsbn().trim().isEmpty()) {
      LogUtils.warn("ISBN del libro inválido");
      throw new IllegalArgumentException("El ISBN del libro es obligatorio");
    }
    String isbn = libroEntity.getIsbn().trim();
    LogUtils.info("Intentando guardar libro: '" + titulo + "' - ISBN: " + isbn);

    if (existeTitulo(titulo)) {
      LogUtils.warn("No se pudo guardar el libro, título duplicado: '" + titulo + "'");
      throw new RecursoDuplicadoException("El título del libro ya existe");
    }
    if (existeIsbn(isbn)) {
      LogUtils.warn("No se pudo guardar el libro, ISBN duplicado: '" + isbn + "'");
      throw new RecursoDuplicadoException("El código ISBN del libro ya existe");
    }

    libroEntity.setTitulo(titulo);
    libroEntity.setIsbn(isbn);

    if (libroEntity.getAutor() != null && libroEntity.getAutor().getId() == null) {
      LogUtils.warn("Autor sin ID");
      throw new IllegalArgumentException("El ID del autor es obligatorio");
    }
    if (libroEntity.getAutor() != null && libroEntity.getAutor().getId() != null) {

      AutorEntity autor = autorRepository.findById(libroEntity.getAutor().getId())
        .orElseThrow(() -> {
          LogUtils.error("Autor no encontrado con ID " + libroEntity.getAutor().getId());
          return new RecursoNoEncontradoException("El autor no existe");
        });

      libroEntity.setAutor(autor);
    }

    LibroEntity guardado = libroRepository.save(libroEntity);
    LogUtils.info("Libro guardado exitosamente con ID " + guardado.getId());
    return guardado;
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public LibroEntity obtenerLibro(Long id) {
    LogUtils.info("Obteniendo libro con ID " + id);
    return libroRepository.findById(id)
      .orElseThrow(() -> {
        LogUtils.error("Libro no encontrado con ID " + id);
        return new RecursoNoEncontradoException("Libro no encontrado");
      });
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public LibroEntity modificarLibro(Long id, LibroEntity libroEntity) {
    LogUtils.info("Modificando libro con ID " + id);
    LibroEntity libro = obtenerLibro(id);

    String titulo = limpiarTexto(libroEntity.getTitulo());
    if (titulo == null) {
      LogUtils.warn("Título del libro inválido");
      throw new IllegalArgumentException("El título del libro es obligatorio");
    }

    String isbn = limpiarTexto(libroEntity.getIsbn());
    if (isbn == null) {
      LogUtils.warn("ISBN del libro inválido");
      throw new IllegalArgumentException("El ISBN del libro es obligatorio");
    }

    if (!libro.getTitulo().equalsIgnoreCase(titulo)
      && existeTitulo(titulo)) {
      LogUtils.warn("No se pudo modificar el libro, título duplicado: " + titulo);
      throw new RecursoDuplicadoException("El título del libro ya existe");
    }

    if (!libro.getIsbn().equals(isbn)
      && existeIsbn(isbn)) {
      LogUtils.warn("No se pudo modificar el libro, ISBN duplicado: " + isbn);
      throw new RecursoDuplicadoException("El ISBN del libro ya existe");
    }

    libro.setTitulo(titulo);
    libro.setIsbn(isbn);
    libro.setFechaPublicacion(libroEntity.getFechaPublicacion());
    libro.setEstado(libroEntity.getEstado());

    if (libroEntity.getAutor() != null && libroEntity.getAutor().getId() == null) {
      LogUtils.warn("Autor sin ID");
      throw new IllegalArgumentException("El ID del autor es obligatorio");
    }
    if (libroEntity.getAutor() != null && libroEntity.getAutor().getId() != null) {

      AutorEntity autor = autorRepository.findById(libroEntity.getAutor().getId())
        .orElseThrow(() -> {
          LogUtils.error("Autor no encontrado con ID " + libroEntity.getAutor().getId());
          return new RecursoNoEncontradoException("El autor no existe");
        });

      libro.setAutor(autor);
    } else {
      libro.setAutor(null);
    }

    LibroEntity modificado = libroRepository.save(libro);
    LogUtils.info("Libro modificado exitosamente con ID " + modificado.getId());

    return modificado;
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public void eliminarLibro(Long id) {
    LogUtils.info("Eliminando libro con ID " + id);
    LibroEntity libro = obtenerLibro(id);
    libroRepository.delete(libro);
    LogUtils.info("Libro con id " + id + ", eliminado exitosamente");
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  @Override
  public List<LibroEntity> listarLibrosPorAutor(Long autorId) {
    LogUtils.info("Listando libros del autor con ID " + autorId);
    AutorEntity autor = autorRepository.findById(autorId)
      .orElseThrow(() -> {
        LogUtils.error("Autor con id " + autorId + ", no encontrado");
        return new RecursoNoEncontradoException("El autor no existe");
      });

    List<LibroEntity> libros = libroRepository.findByAutorId(autor.getId());
    LogUtils.info("Se encontraron " + libros.size() + " libros para el autor '" + autor.getNombre() + "'");
    return libros;
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  private boolean existeIsbn(String isbn) {
    boolean existe = libroRepository.existsByIsbn(isbn);
    LogUtils.info("Validando la existencia de ISBN '" + isbn + "': " + existe);
    return existe;
  }

  /* ////////////////////////////////////////////////////////////////////////// */
  private boolean existeTitulo(String titulo) {
    boolean existe = libroRepository.existsByTituloIgnoreCase(titulo);
    LogUtils.info("Validando la existencia del título '" + titulo + "': " + existe);
    return existe;
  }
}
