/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Especificaciones;
import com.rivdu.excepcion.GeneralException;
import java.util.List;

/**
 *
 * @author LUIS ORTIZ
 */
public interface EspecificacionesServicio extends GenericoServicio<Especificaciones, Long>{

    public List<Especificaciones> listar() throws GeneralException;

    public Especificaciones obtener(Long id);

    public Especificaciones actualizar(Especificaciones especificacion);

    public Especificaciones insertar(Especificaciones entidad);
    
}
