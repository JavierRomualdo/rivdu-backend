/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Relacion;
import com.rivdu.excepcion.GeneralException;
import java.util.List;

/**
 *
 * @author PROPIETARIO
 */
public interface RelacionServicio extends GenericoServicio<Relacion, Long> {
    public List<Relacion> listar() throws GeneralException;
    public Relacion crear(Relacion entidad) throws GeneralException;
    public Relacion actualizar(Relacion entidad) throws GeneralException;
    public void actualizarEstadoCliente(Long id) throws GeneralException;
}
