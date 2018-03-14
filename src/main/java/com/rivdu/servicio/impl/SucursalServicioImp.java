/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Sucursal;
import com.rivdu.servicio.SucursalServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author LUIS ORTIZ
 */
@Service
@Transactional
public class SucursalServicioImp extends GenericoServicioImpl<Sucursal, Long> implements SucursalServicio{

    
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Sucursal, Long> sucursalDao;
 
    public SucursalServicioImp(GenericoDao<Sucursal, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    @Override
    public BusquedaPaginada busquedaPaginada(Sucursal entidadBuscar, BusquedaPaginada busquedaPaginada, String ruc, String nombre) {
         Criterio filtro;
        filtro = Criterio.forClass(Sucursal.class);
        if (nombre!= null) {
            filtro.add(Restrictions.ilike("ruc", '%'+nombre+'%'));
        }
        if (ruc!=null) {
            filtro.add(Restrictions.ilike("nombre",'%' +ruc+'%'));
        }
        busquedaPaginada.setTotalRegistros(sucursalDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("nombre"));
        List<Sucursal> s = sucursalDao.buscarPorCriteriaSinProyecciones(filtro);//this is same the method IngSimpl
        busquedaPaginada.setRegistros(s);
        return busquedaPaginada;
    }

    @Override
    public Sucursal insertar(Sucursal entidad) {
        entidad.setEstado(true);
        return sucursalDao.actualizar(entidad);
    }

    @Override
    public Sucursal obtener(Long id) {
        Sucursal p=obtener(Sucursal.class, id);
        return p;
    }

    @Override
    public Sucursal actualizar(Sucursal entidad) {
        return sucursalDao.actualizar(entidad);
    }
}
