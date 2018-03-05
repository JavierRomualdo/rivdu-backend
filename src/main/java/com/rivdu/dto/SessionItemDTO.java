package com.rivdu.dto;

import java.util.List;
import lombok.*;
import com.rivdu.entidades.Menu;

@Data
public class SessionItemDTO {
    private String nombrecompleto;
    private String token;
    private String usuarioId;
    private String nombre;
    private List<Menu> menus;
    private Long tipoUsuario;
    
}
