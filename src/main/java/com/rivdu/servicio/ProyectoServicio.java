/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Proyecto;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;
import java.util.List;

/**
 *
 * @author Javier Romualdo
 */
public interface ProyectoServicio extends GenericoServicio<Proyecto, Long> {
    public List<Proyecto> listar() throws GeneralException;
    public BusquedaPaginada busquedaPaginada(Proyecto entidadBuscar, BusquedaPaginada busquedaPaginada,String detalle);
    public Proyecto crear(Proyecto entidad) throws GeneralException;
    public Proyecto actualizar(Proyecto entidad) throws GeneralException;
    public void actualizarProyecto(Long id) throws GeneralException;
    public Proyecto obtener(Long id) throws GeneralException;
}
