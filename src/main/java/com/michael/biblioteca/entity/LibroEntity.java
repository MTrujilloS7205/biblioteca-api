package com.michael.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Entidad que representa a un Libro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "libro")
@Entity
public class LibroEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lib_id")
  private Long id;

  @Schema(description = "Nombre del libro", example = "La ciudad y los perror")
  @NotBlank(message = "El título es obligatorio")
  @Size(max = 150, message = "El título no puede superar 150 caracteres")
  @Column(name = "lib_titulo")
  private String titulo;

  @Schema(description = "ISNB del libro", example = "965255547000101")
  @NotBlank(message = "El ISBN es obligatorio")
  @Size(max = 30)
  @Column(name = "lib_isbn")
  private String isbn;

  @PastOrPresent(message = "La fecha no puede ser futura")
  @Column(name = "lib_fecha_publicacion")
  private LocalDate fechaPublicacion;

  @Column(name = "lib_estado")
  private Boolean estado = true;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "lib_aut_id", nullable = true, referencedColumnName = "aut_id")
  private AutorEntity autor;

}
