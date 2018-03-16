/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Estructura;
import com.rivdu.servicio.EstructuraServicio;
import com.rivdu.util.Criterio;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author LUIS ORTIZ
 */
@Service
@Transactional
public class EstructuraServicioImp extends GenericoServicioImpl<Estructura, Long> implements EstructuraServicio{
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Estructura, Long> estructuraDao;
    
    public EstructuraServicioImp(GenericoDao<Estructura, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Estructura> listar() {
        Criterio filtro;
        filtro = Criterio.forClass(Estructura.class);
        return estructuraDao.buscarPorCriteriaSinProyecciones(filtro);    
    }
    
}
