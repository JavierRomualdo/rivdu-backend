/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Cuentabanco;
import com.rivdu.excepcion.GeneralException;
import java.util.List;

/**
 *
 * @author LUIS ORTIZ
 */
public interface CuentaBancoServicio extends GenericoServicio<Cuentabanco, Long>{

    public List<Cuentabanco> listar()throws GeneralException;

    public Cuentabanco actualizar(Cuentabanco entidad);

    public Cuentabanco crear(Cuentabanco entidad);
    
}
