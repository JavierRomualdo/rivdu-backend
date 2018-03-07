/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Tipoubigeo;
import com.rivdu.excepcion.GeneralException;
import java.util.List;

/**
 *
 * @author PROPIETARIO
 */
public interface UbigeoTipoServicio extends GenericoServicio<Tipoubigeo, Long> {
    public List<Tipoubigeo> listar() throws GeneralException;
}
