/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Especificaciones;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.EspecificacionesServicio;
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
public class EspecificacionesServicioImp extends GenericoServicioImpl<Especificaciones, Long> implements EspecificacionesServicio{

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Especificaciones, Long> especificacionesDao;
    
    public EspecificacionesServicioImp(GenericoDao<Especificaciones, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Especificaciones> listar() throws GeneralException {
        Criterio filtro;
        filtro = Criterio.forClass(Especificaciones.class);
        return especificacionesDao.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
    public Especificaciones obtener(Long id) {
           Especificaciones esp=obtener(Especificaciones.class, id);
           return esp;
    }

    @Override
    public Especificaciones actualizar(Especificaciones especificacion) {
        return especificacionesDao.actualizar(especificacion);
    }

    @Override
    public Especificaciones insertar(Especificaciones entidad) {
        entidad.setEstado(true);
        return especificacionesDao.actualizar(entidad);
    }
}
    

