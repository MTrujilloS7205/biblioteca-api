package com.michael.biblioteca.repository;

import com.michael.biblioteca.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

  boolean existsByNombreIgnoreCase(String nombre);

}
