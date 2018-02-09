/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.dao;

import java.io.Serializable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mario
 * @param <Entidad>
 * @param <TipoLlave>
 */
@Transactional
public interface IGenericoDao<Entidad, TipoLlave> extends CrudRepository<Entidad, Serializable>{
    
}
