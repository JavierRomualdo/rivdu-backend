/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Estructura;
import com.rivdu.excepcion.GeneralException;
import java.util.List;

/**
 *
 * @author LUIS ORTIZ
 */
public interface EstructuraServicio extends GenericoServicio<Estructura, Long>{

    public List<Estructura> listar() throws GeneralException;
    
}
