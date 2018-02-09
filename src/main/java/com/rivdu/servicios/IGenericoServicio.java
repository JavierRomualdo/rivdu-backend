/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicios;

import java.util.List;

/**
 *
 * @author mario
 * @param <Entidad>
 * @param <TipoLlave>
 */
public interface IGenericoServicio<Entidad, TipoLlave> {
    List<Entidad> findAll();
}
