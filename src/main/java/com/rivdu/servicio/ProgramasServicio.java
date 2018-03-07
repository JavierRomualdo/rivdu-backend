/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Programas;
import com.rivdu.excepcion.GeneralException;

/**
 *
 * @author Christhian
 */
public interface ProgramasServicio extends GenericoServicio<Programas, Long>{
    
    public Programas crear(Programas entidad) throws GeneralException;
}
