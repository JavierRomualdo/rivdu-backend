/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;
import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Responsable;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ResponsableServicio;
import org.hibernate.SessionFactory;
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
public class ResponsableServicioImp extends GenericoServicioImpl<Responsable, Long> implements ResponsableServicio {
      private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
      
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private GenericoDao<Responsable, Long> responsabledao;

    public ResponsableServicioImp(GenericoDao<Responsable, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public Responsable actualizar(Responsable entidad) throws GeneralException {
        //To change body of generated methods, choose Tools | Templates.
        return responsabledao.actualizar(entidad);
    }
}
