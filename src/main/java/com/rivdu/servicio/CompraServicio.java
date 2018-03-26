/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.dto.CompraDTO;
import com.rivdu.dto.SaveCompraDTO;
import com.rivdu.entidades.Compra;
import com.rivdu.excepcion.GeneralException;
import java.util.List;

/**
 *
 * @author javie
 */
public interface CompraServicio extends GenericoServicio<Compra, Long> {
    
    public List<CompraDTO> listar() throws GeneralException;
    public long insertar(SaveCompraDTO entidad) throws GeneralException;
    public Compra actualizar(Compra producto) throws GeneralException;
    public Compra obtener(Long id) throws GeneralException;
}
