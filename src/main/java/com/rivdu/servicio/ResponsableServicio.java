/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Responsable;
import com.rivdu.excepcion.GeneralException;

/**
 *
 * @author PROPIETARIO
 */
public interface ResponsableServicio extends GenericoServicio<Responsable, Long>  {
        public Responsable actualizar(Responsable  entidad) throws GeneralException;
}
