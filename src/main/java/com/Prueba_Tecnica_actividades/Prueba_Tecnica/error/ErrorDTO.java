package com.Prueba_Tecnica_actividades.Prueba_Tecnica.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String code;
    private String message;
}
