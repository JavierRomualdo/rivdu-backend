/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rivdu.servicio;

/**
 *
 * @author dev-out-04
 * @param <Entidad>
 * @param <TipoLlave>
 */
public interface GenericoServicio <Entidad, TipoLlave>{
    public Entidad obtener(Class<Entidad> aClass, TipoLlave id);  
}
