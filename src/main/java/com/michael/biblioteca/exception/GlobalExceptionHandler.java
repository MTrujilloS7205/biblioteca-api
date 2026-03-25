package com.michael.biblioteca.exception;

import com.michael.biblioteca.util.LogUtils;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

  /* ======================= ERROR 400 =======================================*/
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> manejar400(IllegalArgumentException ex,
    HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
      400,
      "Bad Request",
      ex.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity
      .status(400)
      .body(error);
  }

  /* ======================= ERROR 404 =======================================*/
  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<ErrorResponse> manejar404(RecursoNoEncontradoException ex,
    HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
      404,
      "Not Found",
      ex.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity
      .status(404)
      .body(error);
  }

  /* ======================= ERROR 409 =======================================*/
  @ExceptionHandler(RecursoDuplicadoException.class)
  public ResponseEntity<ErrorResponse> manejar409(RecursoDuplicadoException ex,
    HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
      409,
      "Conflict",
      ex.getMessage(),
      request.getRequestURI()
    );

    return ResponseEntity
      .status(409)
      .body(error);
  }

  /* ======================= ERROR 500 =======================================*/
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> manejar500(Exception ex,
    HttpServletRequest request) {

    LogUtils.error("Error inesperado: " + ex);

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
      500,
      "Internal Server Error",
      "Error interno del servidor",
      request.getRequestURI()
    );

    return ResponseEntity
      .status(500)
      .body(error);
  }
}
