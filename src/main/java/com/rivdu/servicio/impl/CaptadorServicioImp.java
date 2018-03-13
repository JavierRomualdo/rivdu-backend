/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Persona;
import com.rivdu.entidades.Personarol;
import com.rivdu.enums.RolEnum;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CaptadorServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
public class CaptadorServicioImp extends GenericoServicioImpl<Persona, Long> implements CaptadorServicio {
    
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Persona, Long> captadorDao;
 
    public CaptadorServicioImp(GenericoDao<Persona, Long> genericoHibernate) {
        super(genericoHibernate);
    }

   @Override
    public List<Persona> listar() throws GeneralException {
      return  captadorDao.listarTodosVigentes(Persona.class, "estado", true);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Persona entidadBuscar, BusquedaPaginada busquedaPaginada, String dni, String nombre) {

    
        Criterio filtro;
        filtro = Criterio.forClass(Personarol.class);
        filtro.createAlias("persona", "p");
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        filtro.add(Restrictions.eq("personarolPK.idrol", RolEnum.PARAMETRO_ID_CAPTADOR.getValue()));
        if (dni!= null) {
            filtro.add(Restrictions.ilike("p.dni", '%'+dni+'%'));
        }
        if (nombre!=null) {
            filtro.add(Restrictions.ilike("p.nombre",'%' +nombre+'%'));
        }
        busquedaPaginada.setTotalRegistros(captadorDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("p.id"), "id")
                .add(Projections.property("p.apellido"), "apellido")
                .add(Projections.property("p.direccion"), "direccion")
                .add(Projections.property("p.nombre"), "nombre")
                .add(Projections.property("p.dni"), "dni"));
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("p.nombre"));
        List<Persona> p = captadorDao.proyeccionPorCriteria(filtro, Persona.class);//this is same the method IngSimpl
        busquedaPaginada.setRegistros(p);
        return busquedaPaginada;
    }

    @Override
    public Persona insertar(Persona entidad) throws GeneralException {
                entidad.setEstado(Boolean.TRUE);
                entidad = captadorDao.insertar(entidad);
               return entidad;
    }

    @Override
    public Persona actualizar(Persona entidad) throws GeneralException {
        return captadorDao.actualizar(entidad);
    }

    @Override
    public Persona obtener(Long id) throws GeneralException {
        Persona p=obtener(Persona.class, id);
        return p;
    }

}
