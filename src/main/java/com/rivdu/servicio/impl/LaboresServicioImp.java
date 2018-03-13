/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Labores;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.LaboresServicio;
import java.util.List;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Christhian
 */
@Service
public class LaboresServicioImp extends GenericoServicioImpl<Labores, Long> implements LaboresServicio{
    
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private GenericoDao<Labores, Long> laboresdao;
    public LaboresServicioImp(GenericoDao<Labores, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Labores> listar() throws GeneralException {
         return  laboresdao.listarTodosVigentes(Labores.class, "estado", true);
    }

    @Override
    public Labores crear(Labores entidad) throws GeneralException {
        entidad.setEstado(true);
        return laboresdao.insertar(entidad);
    }

    @Override
    public Labores actualizar(Labores entidad) throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarMateriales(Long id) throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
