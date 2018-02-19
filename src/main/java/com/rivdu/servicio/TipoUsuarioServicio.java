/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Tipousuario;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;

/**
 *
 * @author dev-out-03
 */
public interface TipoUsuarioServicio extends GenericoServicio<Tipousuario, Integer>{
    public BusquedaPaginada busquedaPaginada(Tipousuario entidadBuscar, BusquedaPaginada busquedaPaginada, String tipo);
    public Tipousuario insertar(Tipousuario entidad) throws GeneralException;
}
