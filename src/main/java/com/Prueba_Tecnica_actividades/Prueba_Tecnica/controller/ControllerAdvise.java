package com.Prueba_Tecnica_actividades.Prueba_Tecnica.controller;

import com.Prueba_Tecnica_actividades.Prueba_Tecnica.error.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ControllerAdvise {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDTO> validacionIdException(Exception ex){
        ErrorDTO error= ErrorDTO.builder().code("501").message("No se ha introducido el parametro Id").build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ErrorDTO>  validacionIdExceptionProducto(IOException ex){
        ErrorDTO error= ErrorDTO.builder().code("501").message("Se debe introducir un Id Valido del producto").build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO>  validacionIdExceptionProducto(RuntimeException ex){
        ErrorDTO error= ErrorDTO.builder().code("501").message("El id introducido no es válido").build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
