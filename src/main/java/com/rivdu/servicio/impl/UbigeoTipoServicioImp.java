/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;


import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Tipoubigeo;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.UbigeoTipoServicio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author PROPIETARIO
 */

@Service
@Transactional
public class UbigeoTipoServicioImp extends GenericoServicioImpl<Tipoubigeo, Long> implements  UbigeoTipoServicio {
    
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
 
    @Autowired
    private GenericoDao<Tipoubigeo, Long> tipoubigeoDao;
    
    public UbigeoTipoServicioImp(GenericoDao<Tipoubigeo, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Tipoubigeo>  listar() throws GeneralException {
     return  tipoubigeoDao.listarTodosVigentes(Tipoubigeo.class, "estado", true);
    }
}
