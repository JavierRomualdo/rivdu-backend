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
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.IngenieroServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author dev-out-03
 */

@Service
@Transactional
public class IngenieroServicioImp extends GenericoServicioImpl<Persona, Long> implements IngenieroServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Persona, Long> ingenieroDao;
 
    public IngenieroServicioImp(GenericoDao<Persona, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Persona> listar() throws GeneralException{
        Criterio filtro;
        filtro = Criterio.forClass(Persona.class);
        return ingenieroDao.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Persona entidadBuscar, BusquedaPaginada busquedaPaginada, String dni, String nombre) {
         Criterio filtro;
        filtro = Criterio.forClass(Persona.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (dni!= null) {
            filtro.add(Restrictions.ilike("dni", '%'+dni+'%'));
        }
        if (nombre!=null) {
            filtro.add(Restrictions.ilike("nombre",'%' +nombre+'%'));
        }
        busquedaPaginada.setTotalRegistros(ingenieroDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("nombre"));
        List<Persona> p = ingenieroDao.buscarPorCriteriaSinProyecciones(filtro);//this is same the method IngSimpl
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
