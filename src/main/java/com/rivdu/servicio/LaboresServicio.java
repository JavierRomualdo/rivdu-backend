/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Labores;
import com.rivdu.excepcion.GeneralException;
import java.util.List;
/**
 *
 * @author Christhian
 */
public interface LaboresServicio extends GenericoServicio<Labores, Long>{
    public List<Labores> listar() throws GeneralException;
    public Labores crear(Labores entidad) throws GeneralException;
    public Labores actualizar(Labores entidad) throws GeneralException;
    public void actualizarMateriales(Long id) throws GeneralException;
    public Labores obtener(Long id) throws GeneralException; 
}
