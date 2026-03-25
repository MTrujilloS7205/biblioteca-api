package com.michael.biblioteca.repository;

import com.michael.biblioteca.entity.LibroEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, Long> {

  public List<LibroEntity> findByAutorId(Long autorId);
  
  public boolean existsByIsbn(String isbn);
  
  public boolean existsByTituloIgnoreCase(String titulo);
}
