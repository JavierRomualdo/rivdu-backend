/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Usuario;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;

/**
 *
 * @author dev-out-03
 */
public interface UsuarioServicio extends GenericoServicio<Usuario, Integer>{
    public BusquedaPaginada busquedaPaginada(Usuario entidadBuscar, BusquedaPaginada busquedaPaginada, String numdoc, String nomusu);
    public Usuario insertar(Usuario entidad) throws GeneralException;

    public Usuario actualizar(Usuario unidad) throws GeneralException;
}
