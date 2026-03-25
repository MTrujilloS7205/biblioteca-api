package com.michael.biblioteca.util;

public class StringUtils {

  public static String limpiarTexto(String valor) {
    if (valor == null) {
      return null;
    }
    String limpio = valor.trim();
    return limpio.isEmpty() ? null : limpio;
  }
}
