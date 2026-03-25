package com.michael.biblioteca.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {

  private static final DateTimeFormatter timestampFormatter
          = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static final DateTimeFormatter fechaArchivoFormatter
          = DateTimeFormatter.ofPattern("yyyyMMdd");

  public enum TipoLog {
    INFO, WARN, ERROR
  }

  // Método principal para escribir log con tipo y fecha en el archivo
  public static void escribirLog(TipoLog tipo, String mensaje) {
    if (mensaje == null) {
      mensaje = "";
    }

    // timestamp para cada línea
    String timestamp = "[" + LocalDateTime.now().format(timestampFormatter) + "] - ";

    // Nombre del archivo depende del día actual
    String nombreArchivo = "biblioteca-" + LocalDate.now().format(fechaArchivoFormatter) + ".log";

    String linea = timestamp + tipo.name() + " - " + mensaje;

    try (FileWriter fw = new FileWriter(nombreArchivo, true); PrintWriter pw = new PrintWriter(fw)) {
      pw.println(linea);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Métodos de comodidad
  public static void info(String mensaje) {
    escribirLog(TipoLog.INFO, mensaje);
  }

  public static void warn(String mensaje) {
    escribirLog(TipoLog.WARN, mensaje);
  }

  public static void error(String mensaje) {
    escribirLog(TipoLog.ERROR, mensaje);
  }
}
