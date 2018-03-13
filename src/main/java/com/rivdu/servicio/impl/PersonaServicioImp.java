/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Persona;
import com.rivdu.entidades.Personarol;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import com.rivdu.servicio.PersonaServicio;
/**
 *
 * @author dev-out-03
 */

@Service
@Transactional
public class PersonaServicioImp extends GenericoServicioImpl<Persona, Long> implements PersonaServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Persona, Long> ingenieroDao;
 
    public PersonaServicioImp(GenericoDao<Persona, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Persona> listar() throws GeneralException{
        Criterio filtro;
        filtro = Criterio.forClass(Persona.class);
        return ingenieroDao.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Persona entidadBuscar, BusquedaPaginada busquedaPaginada, String dni, String nombre,Long idrol) {
        Criterio filtro;
        filtro = Criterio.forClass(Personarol.class);
        filtro.createAlias("persona", "p", JoinType.RIGHT_OUTER_JOIN);
        if (dni!= null && !"".equals(dni)) {
            filtro.add(Restrictions.ilike("p.dni", '%'+dni+'%'));
        }
        if (nombre!=null && !"".equals(nombre)) {
            filtro.add(Restrictions.ilike("p.nombre",'%' +nombre+'%'));
        }
        if(idrol>0){
           filtro.add(Restrictions.eq("personarolPK.idrol", idrol));
        }
        busquedaPaginada.setTotalRegistros(ingenieroDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        
        filtro.setProjection(Projections.projectionList()
                .add(Projections.distinct(Projections.property("p.id")))
                .add(Projections.property("p.id"), "id")
                .add(Projections.property("p.estado"), "estado")
                .add(Projections.property("p.apellido"), "apellido")
                .add(Projections.property("p.direccion"), "direccion")
                .add(Projections.property("p.nombre"), "nombre")
                .add(Projections.property("p.dni"), "dni"));
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("p.nombre"));
        
       List<Persona> p = ingenieroDao.proyeccionPorCriteria(filtro, Persona.class);//this is same the method IngSimpl
       busquedaPaginada.setRegistros(p);
       return busquedaPaginada;
    }

    @Override
    public Persona insertar(Persona entidad) throws GeneralException {
        verificarPersonaRepetidad(entidad);
        entidad.setEstado(true);
        return ingenieroDao.insertar(entidad);
    }

    @Override
    public Persona actualizar(Persona entidad) throws GeneralException {
        verificarPersonaRepetidad(entidad);
        return ingenieroDao.actualizar(entidad);
    }

    @Override
    public Persona obtener(Long id) throws GeneralException {
        Persona p=obtener(Persona.class, id);
        return p;
    }

    private void verificarPersonaRepetidad(Persona entidad) {
        Criterio filtro;
        filtro = Criterio.forClass(Persona.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (entidad.getId()!=null) {
            filtro.add(Restrictions.ne("id", entidad.getId()));
        }
        filtro.add(Restrictions.eq("dni", entidad.getDni()));
        Persona u = ingenieroDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u!=null) {
            throw new GeneralException("Ya existe Ingeniero  con igual D.N.I.", "Guardar retorno nulo", loggerServicio);
        }
    }
}
