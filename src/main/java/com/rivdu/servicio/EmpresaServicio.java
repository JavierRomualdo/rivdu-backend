/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Empresa;
import com.rivdu.excepcion.GeneralException;

/**
 *
 * @author dev-out-03
 */
public interface EmpresaServicio extends GenericoServicio<Empresa, Long>{
    public Empresa validar(String ruc) throws GeneralException;
    public Empresa actualizar(Empresa entidad) throws GeneralException;
}
