/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;
import com.rivdu.entidades.Materiales;
import com.rivdu.excepcion.GeneralException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Christhian
 */

public interface MaterialesServicio extends GenericoServicio<Materiales, Long>{
    public List<Materiales> listar() throws GeneralException;
    public Materiales crear(Materiales entidad) throws GeneralException;
    public Materiales actualizar(Materiales entidad) throws GeneralException;
    public void actualizarMateriales(Long id) throws GeneralException;
}
