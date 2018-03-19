/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Rol;
import com.rivdu.excepcion.GeneralException;
import java.util.List;

/**
 *
 * @author PROPIETARIO
 */
public interface RolServicio extends GenericoServicio<Rol, Long> {
     public List<Rol> listar() throws GeneralException;
    
}
