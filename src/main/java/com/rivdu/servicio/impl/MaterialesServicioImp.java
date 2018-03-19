/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Estadocliente;
import com.rivdu.entidades.Materiales;
import com.rivdu.entidades.Persona;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.MaterialesServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.List;
import javafx.scene.paint.Material;
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
        return  materialesdao.listarTodosVigentes(Materiales.class, "estado", true);
    }
    
    @Override
    public BusquedaPaginada busquedaPaginada(Materiales entidadBuscar, BusquedaPaginada busquedaPaginada, String detalle) {
        Criterio filtro;
        filtro = Criterio.forClass(Materiales.class);
        if (detalle!=null) {
            filtro.add(Restrictions.ilike("detalle",'%' +detalle+'%'));
        }
        busquedaPaginada.setTotalRegistros(materialesdao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("detalle"));
        List<Materiales> p = materialesdao.buscarPorCriteriaSinProyecciones(filtro);//this is same the method IngSimpl
        busquedaPaginada.setRegistros(p);
        return busquedaPaginada;
    }
   
    @Override
    public Materiales crear(Materiales entidad) throws GeneralException {
       entidad.setEstado(true);
        return materialesdao.insertar(entidad);
    }

    @Override
    public Materiales actualizar(Materiales entidad) throws GeneralException {
        
        return materialesdao.actualizar(entidad);
    }

    @Override
    public void actualizarMateriales(Long id) throws GeneralException {
            String hql = "UPDATE Materiales SET estado=FALSE WHERE id=:id";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
    }

    @Override
    public Materiales obtener(Long id) throws GeneralException {
       Materiales p=obtener(Materiales.class, id);
       return p;
    }  
}
