/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;
import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Relacion;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.RelacionServicio;
import java.util.List;
import org.hibernate.Query;
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
public class RelacionServicioImp extends GenericoServicioImpl<Relacion, Long> implements RelacionServicio {
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private GenericoDao<Relacion, Long> estadocivildao;

    public RelacionServicioImp(GenericoDao<Relacion, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public List<Relacion> listar() throws GeneralException {
      return  estadocivildao.listarTodosVigentes(Relacion.class, "estado", true);
    }
    
     @Override
    public Relacion crear(Relacion entidad) throws GeneralException {   
        entidad.setEstado(true);
        return estadocivildao.insertar(entidad);
    }
    
    @Override
    public Relacion actualizar(Relacion e) throws GeneralException {
        return estadocivildao.actualizar(e);
    }
    
     @Override
    public void actualizarEstadoCliente(Long id) throws GeneralException {
            String hql = "UPDATE Relacion SET estado=FALSE WHERE id=:id";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
    }

}
