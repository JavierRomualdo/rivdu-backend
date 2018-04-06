/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.dto;

import com.rivdu.entidades.Usuario;
import com.rivdu.entidades.Colindante;
import com.rivdu.entidades.Compra;
import com.rivdu.entidades.Personaventa;
import com.rivdu.entidades.Predio;
import com.rivdu.entidades.Servicios;
import lombok.Data;

/**
 *
 * @author PROPIETARIO
 */
@Data
public class SaveVentaDTO {
    
    public SaveVentaDTO(){
        
    }
    
    private Compra compra;
    private Predio predio;
    private Colindante colindante;
    private Servicios[] servicios;
    private Usuario usuario;
    private Personaventa [] personaventa;
    private Personaventa [] personaventa2;
    
}
