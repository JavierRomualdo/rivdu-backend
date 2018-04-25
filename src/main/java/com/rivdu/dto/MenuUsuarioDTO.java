/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author PCQUISPE
 */
@Data
public class MenuUsuarioDTO {
    private long id;
    private String label;
    private boolean estado;
    private List <MenuUsuarioDTO> children;
}
