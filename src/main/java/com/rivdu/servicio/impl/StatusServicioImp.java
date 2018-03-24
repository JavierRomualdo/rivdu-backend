/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Status;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.StatusServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Javier Romualdo
 */
@Service
@Transactional
public class StatusServicioImp extends GenericoServicioImpl<Status, Long> implements StatusServicio{
     
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private SessionFactory sessionFactory;
   
    @Autowired
    private GenericoDao<Status, Long> statusdao;
    public StatusServicioImp(GenericoDao<Status, Long> genericoHibernate) {
        super(genericoHibernate);
    }
   
    @Override
    public List<Status> listar() throws GeneralException {
        return  statusdao.listarTodosVigentes(Status.class, "estado", true);
    }
    
    @Override
    public BusquedaPaginada busquedaPaginada(Status entidadBuscar, BusquedaPaginada busquedaPaginada, String detalle) {
        Criterio filtro;
        filtro = Criterio.forClass(Status.class);
        if (detalle!=null) {
            filtro.add(Restrictions.ilike("detalle",'%' +detalle+'%'));
        }
        busquedaPaginada.setTotalRegistros(statusdao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("detalle"));
        List<Status> p = statusdao.buscarPorCriteriaSinProyecciones(filtro);//this is same the method IngSimpl
        busquedaPaginada.setRegistros(p);
        return busquedaPaginada;
    }
   
    @Override
    public Status crear(Status entidad) throws GeneralException {
       entidad.setEstado(true);
        return statusdao.insertar(entidad);
    }

    @Override
    public Status actualizar(Status entidad) throws GeneralException {
        
        return statusdao.actualizar(entidad);
    }

    @Override
    public void actualizarStatus(Long id) throws GeneralException {
            String hql = "UPDATE Status SET estado=FALSE WHERE id=:id";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
    }

    @Override
    public Status obtener(Long id) throws GeneralException {
       Status p=obtener(Status.class, id);
       return p;
    }  
}
