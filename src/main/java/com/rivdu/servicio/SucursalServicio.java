/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Sucursal;
import com.rivdu.util.BusquedaPaginada;

/**
 *
 * @author LUIS ORTIZ
 */
public interface SucursalServicio extends GenericoServicio<Sucursal, Long> {

    public BusquedaPaginada busquedaPaginada(Sucursal entidadBuscar, BusquedaPaginada busquedaPaginada, String ruc, String nombre);
    public Sucursal insertar(Sucursal entidad);
    public Sucursal obtener(Long id);
    public Sucursal actualizar(Sucursal sucursal);

}
