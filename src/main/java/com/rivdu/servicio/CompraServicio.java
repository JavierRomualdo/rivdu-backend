/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.dto.ExpedientesDTO;
import com.rivdu.dto.SaveCompraDTO;
import com.rivdu.entidades.Compra;
import com.rivdu.entidades.Predio;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;
import java.util.List;

/**
 *
 * @author javie
 */

public interface CompraServicio extends GenericoServicio<Compra, Long> {
    public long insertar(SaveCompraDTO entidad) throws GeneralException;
    public void guardarPredioServicio(List<Long> servicios, Predio predio);
    public SaveCompraDTO obtener(Long id) throws GeneralException;
    public BusquedaPaginada busquedaPaginada(Compra entidadBuscar, BusquedaPaginada busquedaPaginada, String clientenombre, String clientedoc, String correlativo);
    public List<ExpedientesDTO> listarExpedientes(Long id);
    public Compra actualizar(SaveCompraDTO entidad);
}
