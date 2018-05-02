/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.dto.SaveVentaDTO;
import com.rivdu.entidades.Predio;
import com.rivdu.entidades.Venta;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;
import java.util.List;

/**
 *
 * @author javie
 */

public interface VentaServicio extends GenericoServicio<Venta, Long> {
    public long insertar(SaveVentaDTO entidad) throws GeneralException;
    public void guardarPredioServicio(List<Long> servicios, Predio predio);
    public SaveVentaDTO obtener(Long id) throws GeneralException;
    public BusquedaPaginada busquedaPaginada(Venta entidadBuscar, BusquedaPaginada busquedaPaginada, String clientenombre, String clientedoc, String correlativo);
    public Venta actualizar(SaveVentaDTO entidad);
}
