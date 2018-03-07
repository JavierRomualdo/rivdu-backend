/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Programas;
import com.rivdu.entidades.Ubigeo;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ProgramasServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Christhian
 */
@Service
public class ProgramasServicioImp extends GenericoServicioImpl<Programas, Long> implements ProgramasServicio {
    
    @Autowired
    private GenericoDao<Programas, Long> programasDao;
   
     public ProgramasServicioImp  (GenericoDao<Programas, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public Programas crear(Programas e) throws GeneralException {
        e.setEstado(true);
        return programasDao.insertar(e);
    }
    
}
