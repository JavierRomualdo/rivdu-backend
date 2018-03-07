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
    
    @Autowired
    private GenericoDao<Ubigeo, Long> ubigeoDao;
   
     public UbigeoServicioImp(GenericoDao<Ubigeo, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public Ubigeo crear(Ubigeo e) throws GeneralException {
        e.setEstado(true);
        return ubigeoDao.insertar(e);
    }
  
}
