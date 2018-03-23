/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Status;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;
import java.util.List;

/**
 *
 * @author Javier Romualdo
 */
public interface StatusServicio extends GenericoServicio<Status, Long>{
    public List<Status> listar() throws GeneralException;
    public BusquedaPaginada busquedaPaginada(Status entidadBuscar, BusquedaPaginada busquedaPaginada,String detalle);
    public Status crear(Status entidad) throws GeneralException;
    public Status actualizar(Status entidad) throws GeneralException;
    public void actualizarStatus(Long id) throws GeneralException;
    public Status obtener(Long id) throws GeneralException;
}
