/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Ubigeo;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.UbigeoServicio;
import com.rivdu.util.Criterio;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PROPIETARIO
 */
@Service
@Transactional
public class UbigeoServicioImp extends GenericoServicioImpl<Ubigeo, Long> implements UbigeoServicio {
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenericoDao<Ubigeo, Long> ubigeoDao;
   
     public UbigeoServicioImp(GenericoDao<Ubigeo, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public Ubigeo crear(Ubigeo entidad) throws GeneralException {
        Criterio filtro;
        filtro = Criterio.forClass(Ubigeo.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (entidad.getId()!=null) {
            filtro.add(Restrictions.eq("id", entidad.getId()));
        }
        filtro.add(Restrictions.eq("codigo", entidad.getCodigo()));
        Ubigeo u = ubigeoDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u!=null) {
            throw new GeneralException("Ya existe un ubigeo  con igual codigo.", "Guardar retorno nulo", loggerServicio);
        }
        entidad.setEstado(true);
        return ubigeoDao.insertar(entidad);
    }
    
}
