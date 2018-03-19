/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;
import com.rivdu.excepcion.GeneralException;
import java.util.List;
import com.rivdu.entidades.Estadocliente;

/**
 *
 * @author PROPIETARIO
 */
public interface EstadoClienteServicio extends GenericoServicio<Estadocliente, Long> {
    public List<Estadocliente> listar() throws GeneralException;
    public Estadocliente crear(Estadocliente entidad) throws GeneralException;
    public Estadocliente actualizar(Estadocliente entidad) throws GeneralException;
    public void actualizarEstadoCliente(Long id, boolean estado) throws GeneralException;
    
}
