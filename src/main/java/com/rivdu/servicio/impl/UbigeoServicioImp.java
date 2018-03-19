/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Ubigeo;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.UbigeoServicio;
import com.rivdu.util.BusquedaPaginada;
import org.hibernate.criterion.Order;
import com.rivdu.util.Criterio;
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
public class UbigeoServicioImp extends GenericoServicioImpl<Ubigeo, Long> implements UbigeoServicio {
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenericoDao<Ubigeo, Long> ubigeoDao;
   
     public UbigeoServicioImp(GenericoDao<Ubigeo, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public Ubigeo crear(Ubigeo entidad) throws GeneralException {
        verificarUbigeoRepetidad(entidad);
        entidad.setEstado(true);
        return ubigeoDao.insertar(entidad);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Ubigeo entidadBuscar, BusquedaPaginada busquedaPaginada, String nombre, String codigo) {
        Criterio filtro;
        filtro = Criterio.forClass(Ubigeo.class);
        if (nombre!= null && !nombre.equals("")) {
            filtro.add(Restrictions.ilike("nombre", '%'+nombre+'%'));
        }
        if (codigo!= null && !codigo.equals("")) {
            filtro.add(Restrictions.ilike("codigo",'%'+codigo+'%'));
        }
        busquedaPaginada.setTotalRegistros(ubigeoDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.desc("id"));
        busquedaPaginada.setRegistros(ubigeoDao.buscarPorCriteriaSinProyecciones(filtro));
        return busquedaPaginada;
    }

    @Override
    public Ubigeo obtener(Long id) throws GeneralException {
        Ubigeo u =obtener(Ubigeo.class, id);
        return u;
    }

    @Override
    public Ubigeo actualizar(Ubigeo ubigeo) throws GeneralException {
        verificarUbigeoRepetidad(ubigeo);
        return ubigeoDao.actualizar(ubigeo);
    }

    private void verificarUbigeoRepetidad(Ubigeo ubigeo) {
        Criterio filtro;
        filtro = Criterio.forClass(Ubigeo.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (ubigeo.getId()!=null) {
            filtro.add(Restrictions.ne("id", ubigeo.getId()));
        }
        filtro.add(Restrictions.eq("codigo", ubigeo.getCodigo()));
        Ubigeo u = ubigeoDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u!=null) {
            throw new GeneralException("Ya existe Ubigeo  con igual codigo", "Guardar retorno nulo", loggerServicio);
        }
    }
}
