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
import com.rivdu.util.Criterio;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
    
    @Autowired
    private GenericoDao<Ubigeo, Long> ubigeoDao;
   
     public UbigeoServicioImp(GenericoDao<Ubigeo, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public Ubigeo crear(Ubigeo e) throws GeneralException {
        e.setEstado(true);
        return ubigeoDao.insertar(e);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Ubigeo entidadBuscar, BusquedaPaginada busquedaPaginada, String nombre, String codigo) {
        
        Criterio filtro;
        filtro = Criterio.forClass(Ubigeo.class);
        if (nombre!= null && !nombre.equals("")) {
            filtro.add(Restrictions.ilike("nombre", nombre));
        }
        if (codigo!= null && !codigo.equals("")) {
            filtro.add(Restrictions.ilike("codigo",codigo));
        }
        busquedaPaginada.setTotalRegistros(ubigeoDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.desc("id"));
        busquedaPaginada.setRegistros(ubigeoDao.buscarPorCriteriaSinProyecciones(filtro));
        return busquedaPaginada;
    }
  
}
