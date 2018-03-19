/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rivdu.entidades.Estadocliente;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.EstadoClienteServicio;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author PROPIETARIO
 */
@Service
@Transactional
public class EstadoClienteServicioImp extends GenericoServicioImpl<Estadocliente, Long> implements EstadoClienteServicio{
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private GenericoDao<Estadocliente, Long> estadocivildao;
    
    
    public EstadoClienteServicioImp(GenericoDao<Estadocliente, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public List<Estadocliente> listar() throws GeneralException {
        Criterio filtro;
        filtro =Criterio.forClass(Estadocliente.class);
        return estadocivildao.buscarPorCriteriaSinProyecciones(filtro);
    }
    
     @Override
    public Estadocliente crear(Estadocliente entidad) throws GeneralException {   
        entidad.setEstado(true);
        return estadocivildao.insertar(entidad);
    }
    
    @Override
    public Estadocliente actualizar(Estadocliente e) throws GeneralException {
        return estadocivildao.actualizar(e);
    }
    
     @Override
    public void actualizarEstadoCliente(Long id, boolean estado) throws GeneralException {
            String hql = "UPDATE Estadocliente SET estado=:estado WHERE id=:id";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameter("id", id);
            query.setParameter("estado", estado);
            query.executeUpdate();
    }

    
}
