/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicios.impl;

import com.rivdu.dao.IGenericoDao;
import com.rivdu.servicios.IGenericoServicio;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mario
 * @param <Entidad>
 * @param <TipoLlave>
 */
@Transactional
public abstract class GenericoServicioImpl<Entidad, TipoLlave> implements IGenericoServicio<Entidad, TipoLlave> {

    private final IGenericoDao<Entidad, TipoLlave> genericoDao;

    @SuppressWarnings("unchecked")
    protected GenericoServicioImpl(IGenericoDao<Entidad, TipoLlave> genericoHibernate) {
        this.genericoDao = genericoHibernate;
    }

}
