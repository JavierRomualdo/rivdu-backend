/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Menutipousuario;
import com.rivdu.entidades.MenutipousuarioPK;
import com.rivdu.entidades.Rol;
import com.rivdu.entidades.Servicios;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ServiciosServicio;
import com.rivdu.util.Criterio;
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
public class ServiciosServicioImp extends GenericoServicioImpl<Servicios, Long> implements  ServiciosServicio {
    
    @Autowired
    private GenericoDao<Servicios, Long> serviciosDao;
     
    public ServiciosServicioImp(GenericoDao<Servicios, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public List<Servicios>  listar() throws GeneralException {
        return serviciosDao.listarTodosVigentes(Servicios.class, "estado", true);
    }
    
}
