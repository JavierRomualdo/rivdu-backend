/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

/**
 *
 * @author PROPIETARIO
 */

import com.rivdu.entidades.Ubigeo;
import com.rivdu.excepcion.GeneralException;

public interface UbigeoServicio extends GenericoServicio<Ubigeo, Long>{
   // public Ubigeo validar(String id) throws GeneralException;
    public Ubigeo crear(Ubigeo entidad) throws GeneralException;
    
}
