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
import com.rivdu.util.BusquedaPaginada;

public interface UbigeoServicio extends GenericoServicio<Ubigeo, Long>{
    public Ubigeo crear(Ubigeo entidad) throws GeneralException;
    public BusquedaPaginada busquedaPaginada(Ubigeo entidadBuscar, BusquedaPaginada busquedaPaginada, String nombre,String codigo);
    public Ubigeo obtener(Long id) throws GeneralException;
    public Ubigeo actualizar(Ubigeo ubigeo) throws GeneralException;
}
