/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Materiales;
import com.rivdu.entidades.Proyecto;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ProyectoServicio;
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
public class ProyectoServicioImp extends GenericoServicioImpl<Proyecto, Long> implements ProyectoServicio{
     
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private SessionFactory sessionFactory;
   
    @Autowired
    private GenericoDao<Proyecto, Long> proyectodao;
    public ProyectoServicioImp(GenericoDao<Proyecto, Long> genericoHibernate) {
        super(genericoHibernate);
    }
   
    @Override
    public List<Proyecto> listar() throws GeneralException {
        return  proyectodao.listarTodosVigentes(Proyecto.class, "estado", true);
    }
    
    @Override
    public BusquedaPaginada busquedaPaginada(Proyecto entidadBuscar, BusquedaPaginada busquedaPaginada, String detalle) {
        Criterio filtro;
        filtro = Criterio.forClass(Proyecto.class);
        if (detalle!=null) {
            filtro.add(Restrictions.ilike("detalle",'%' +detalle+'%'));
        }
        busquedaPaginada.setTotalRegistros(proyectodao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("detalle"));
        List<Proyecto> p = proyectodao.buscarPorCriteriaSinProyecciones(filtro);//this is same the method IngSimpl
        busquedaPaginada.setRegistros(p);
        return busquedaPaginada;
    }
   
    @Override
    public Proyecto crear(Proyecto entidad) throws GeneralException {
       entidad.setEstado(true);
        return proyectodao.insertar(entidad);
    }

    @Override
    public Proyecto actualizar(Proyecto entidad) throws GeneralException {
        
        return proyectodao.actualizar(entidad);
    }

    @Override
    public void actualizarProyecto(Long id) throws GeneralException {
            String hql = "UPDATE Proyecto SET estado=FALSE WHERE id=:id";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
    }

    @Override
    public Proyecto obtener(Long id) throws GeneralException {
       Proyecto p=obtener(Proyecto.class, id);
       return p;
    }  
}
