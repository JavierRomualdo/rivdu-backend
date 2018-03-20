/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Centrocostos;
import java.util.List;

/**
 *
 * @author LUIS ORTIZ
 */
public interface CentroCostoServicio extends GenericoServicio<Centrocostos, Long>{

    public List<Centrocostos> listar();

    public Centrocostos actualizar(Centrocostos entidad);

    public Centrocostos crear(Centrocostos entidad);


    
}
