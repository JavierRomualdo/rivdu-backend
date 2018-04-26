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
import java.util.List;
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
        if (nombre != null && !nombre.equals("")) {
            filtro.add(Restrictions.ilike("nombre", '%' + nombre + '%'));
        }
        if (codigo != null && !codigo.equals("")) {
            filtro.add(Restrictions.ilike("codigo", '%' + codigo + '%'));
        }
        busquedaPaginada.setTotalRegistros(ubigeoDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.desc("id"));
        List<Ubigeo> u = ubigeoDao.buscarPorCriteriaSinProyecciones(filtro);
        obtenerEtiquetas(u);
        busquedaPaginada.setRegistros(u);
        return busquedaPaginada;
    }

    @Override
    public Ubigeo obtener(Long id) throws GeneralException {
        Ubigeo u = obtener(Ubigeo.class, id);
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
        if (ubigeo.getId() != null) {
            filtro.add(Restrictions.ne("id", ubigeo.getId()));
        }
        filtro.add(Restrictions.eq("codigo", ubigeo.getCodigo()));
        Ubigeo u = ubigeoDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u != null) {
            throw new GeneralException("Ya existe Ubigeo  con igual codigo", "Guardar retorno nulo", loggerServicio);
        }
    }

    private void obtenerEtiquetas(List<Ubigeo> u) {
        for (int i = 0; i < u.size(); i++) {
            if (Long.compare(u.get(i).getIdtipoubigeo().getId(), 4) == 0) {
                obtenerDepartamento(u.get(i));
                obtenerProvincia(u.get(i));
                obtenerDistrito(u.get(i));
                u.get(i).setCentro(u.get(i).getNombre());
            }
            if (Long.compare(u.get(i).getIdtipoubigeo().getId(), 3) == 0) {
                obtenerDepartamento(u.get(i));
                obtenerProvincia(u.get(i));
                u.get(i).setDistrito(u.get(i).getNombre());
            }
            if (Long.compare(u.get(i).getIdtipoubigeo().getId(), 2) == 0) {
                obtenerDepartamento(u.get(i));
                u.get(i).setProvincia(u.get(i).getNombre());
            }
            if (Long.compare(u.get(i).getIdtipoubigeo().getId(), 1) == 0) {
                u.get(i).setDepartamento(u.get(i).getNombre());
            }
        }
    }

    private void obtenerDepartamento(Ubigeo u) {
        Criterio filtro = Criterio.forClass(Ubigeo.class);
        filtro.add(Restrictions.eq("idtipoubigeo.id", new Long(1)));
        String inicioUbigeo = u.getCodigo();
        if (inicioUbigeo != null && !inicioUbigeo.isEmpty() && inicioUbigeo.length() >= 2) {
            inicioUbigeo = inicioUbigeo.substring(0, 2);
            filtro.add(Restrictions.like("codigo", inicioUbigeo + "00000000"));
            Ubigeo d = ubigeoDao.obtenerPorCriteriaSinProyecciones(filtro);
            if (d != null) {
                u.setDepartamento(d.getNombre());
            }
        }
    }

    private void obtenerProvincia(Ubigeo u) {
        Criterio filtro = Criterio.forClass(Ubigeo.class);
        filtro.add(Restrictions.eq("idtipoubigeo.id", new Long(2)));
        String inicioUbigeo = u.getCodigo();
        if (inicioUbigeo != null && !inicioUbigeo.isEmpty() && inicioUbigeo.length() >= 4) {
            inicioUbigeo = inicioUbigeo.substring(0, 4);
            filtro.add(Restrictions.like("codigo", inicioUbigeo + "000000"));
            Ubigeo d = ubigeoDao.obtenerPorCriteriaSinProyecciones(filtro);
            if (d != null) {
                u.setProvincia(d.getNombre());
            }
        }
    }

    private void obtenerDistrito(Ubigeo u) {
        Criterio filtro = Criterio.forClass(Ubigeo.class);
        filtro.add(Restrictions.eq("idtipoubigeo.id", new Long(3)));
        String inicioUbigeo = u.getCodigo();
        if (inicioUbigeo != null && !inicioUbigeo.isEmpty() && inicioUbigeo.length() >= 6) {
            inicioUbigeo = inicioUbigeo.substring(0, 6);
            filtro.add(Restrictions.like("codigo", inicioUbigeo + "0000"));
            Ubigeo d = ubigeoDao.obtenerPorCriteriaSinProyecciones(filtro);
            if (d != null) {
                u.setDistrito(d.getNombre());
            }
        }
    }
}
