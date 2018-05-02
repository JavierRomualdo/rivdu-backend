/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.dto;

import com.rivdu.entidades.Usuario;
import com.rivdu.entidades.Colindante;
import com.rivdu.entidades.Personaventa;
import com.rivdu.entidades.Predio;
import com.rivdu.entidades.Venta;
import java.util.List;
import lombok.Data;

/**
 *
 * @author PROPIETARIO
 */
@Data
public class SaveVentaDTO {
    
    public SaveVentaDTO(){
        
    }
    
    private Venta venta;
    private Predio predio;
    private Colindante colindante;
    private List<Long> servicios;
    private Usuario usuario;
    private List<Personaventa> propietarioList;
    private List<Personaventa> allegadosList;
    
}
