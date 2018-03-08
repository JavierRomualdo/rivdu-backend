/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Materiales;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.MaterialesServicio;
import java.util.List;
import javax.transaction.Transactional;
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
@Transactional
public class MaterialesServicioImp extends GenericoServicioImpl<Materiales, Long> implements MaterialesServicio{
     
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Autowired
    private GenericoDao<Materiales, Long> materialesdao;
    public MaterialesServicioImp(GenericoDao<Materiales, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    
    @Override
    public List<Materiales> listar() throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Materiales crear(Materiales entidad) throws GeneralException {
       entidad.setEstado(true);
        return materialesdao.insertar(entidad);
    }

    @Override
    public Materiales actualizar(Materiales entidad) throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarMateriales(Long id) throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
