/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Persona;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;
import java.util.List;

/**
 *
 * @author PROPIETARIO
 */
public interface CaptadorServicio extends GenericoServicio<Persona, Long>{
    public List<Persona> listar() throws GeneralException;
    public BusquedaPaginada busquedaPaginada(Persona entidadBuscar, BusquedaPaginada busquedaPaginada, String dni, String nombre);
    public Persona insertar(Persona entidad) throws GeneralException;
    public Persona actualizar(Persona producto) throws GeneralException;
    public Persona obtener(Long id) throws GeneralException;
    
}
