package com.michael.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Entidad que representa a un Autor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "autor")
@Entity
public class AutorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "aut_id")
  private Long id;

  @Schema(description = "Nombre del autor", example = "Gabriel García Márquez")
  @NotBlank(message = "El nombre del autor es obligatorio")
  @Size(max = 50, message = "El nombre no puede tener mas de 50 caracteres")
  @Column(name = "aut_nombre")
  private String nombre;

  @Schema(description = "Nacionalidad del autor", example = "Colombiano")
  @Column(name = "aut_nacionalidad")
  private String nacionalidad;

  @Schema(description = "Estado del autor", example = "true")
  @Column(name = "aut_estado")
  private Boolean estado = true;

  @JsonIgnore
  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<LibroEntity> libros = new ArrayList<>();

  
  // Metodos Helper
  public void addLibro(LibroEntity libro) {
    libros.add(libro);
    libro.setAutor(this);
  }

  public void removeLibro(LibroEntity libro) {
    libros.remove(libro);
    libro.setAutor(null);
  }

}
