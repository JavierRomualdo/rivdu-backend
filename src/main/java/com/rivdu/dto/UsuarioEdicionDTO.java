package com.rivdu.dto;

import com.rivdu.entidades.Usuario;
import com.rivdu.entidades.Usuarioacceso;
import java.util.List;
import lombok.Data;

@Data
public class UsuarioEdicionDTO {

    private Usuario usuario;
    private List<Usuarioacceso> usuarioaccesoList;
    private String password;

    public UsuarioEdicionDTO() {
    }
    
}
