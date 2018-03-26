package com.rivdu.dto;

import com.rivdu.entidades.Persona;
import lombok.*;

@Data
public class CompraDTO {

    private Long id;
    private Persona titular;

    public CompraDTO() {
    }
    
}
