/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Compra;
import com.rivdu.excepcion.GeneralException;
import java.util.List;

/**
 *
 * @author javie
 */
public interface CompraServicio extends GenericoServicio<Compra, Long> {
    
    public List<Compra> listar() throws GeneralException;
//    public BusquedaPaginada busquedaPaginada(Persona entidadBuscar, BusquedaPaginada busquedaPaginada, String dni, String nombre);
    public Compra insertar(Compra entidad) throws GeneralException;
    public Compra actualizar(Compra producto) throws GeneralException;
    public Compra obtener(Long id) throws GeneralException;
}
