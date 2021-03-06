/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.dto.UsuarioEdicionDTO;
import com.rivdu.entidades.Usuario;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;

/**
 *
 * @author dev-out-03
 */
public interface UsuarioServicio extends GenericoServicio<Usuario, Long>{
    public BusquedaPaginada busquedaPaginada(Usuario entidadBuscar, BusquedaPaginada busquedaPaginada, String numdoc, String nomusu);
    public Usuario show(String username) throws GeneralException;
    public Usuario insertar(Usuario entidad) throws GeneralException;
    public Usuario obtener(long id) throws GeneralException;
    public Usuario actualizar(Usuario unidad) throws GeneralException;
    public Usuario validarDni(String dni) throws GeneralException;
    public boolean validarNuevaPassword(String username, String passwordTipeada) throws GeneralException;
    public UsuarioEdicionDTO obtenerParaEdicion(Long id) throws GeneralException;
}
