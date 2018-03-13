/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Rol;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.RolServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PROPIETARIO
 */
@Service
@Transactional
public class RolServicioImp extends GenericoServicioImpl<Rol, Long> implements  RolServicio {
     @Autowired
    private GenericoDao<Rol, Long> rolDao;
    public RolServicioImp(GenericoDao<Rol, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public List<Rol>  listar() throws GeneralException {
     return  rolDao.listarTodosVigentes(Rol.class, "estado", true);
    }
}
