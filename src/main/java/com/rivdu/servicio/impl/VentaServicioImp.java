/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.dto.VentaDTO;
import com.rivdu.dto.ExpedienteChildrenDTO;
import com.rivdu.dto.ExpedientesDTO;
import com.rivdu.dto.SaveVentaDTO;
import com.rivdu.entidades.Captador;
import com.rivdu.entidades.Colindante;
import com.rivdu.entidades.Personaventa;
import com.rivdu.entidades.Predio;
import com.rivdu.entidades.Predioservicio;
import com.rivdu.entidades.Tipoexpediente;
import com.rivdu.entidades.Venta;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.VentaServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author javie
 */
@Service
public class VentaServicioImp extends GenericoServicioImpl<Venta, Long> implements VentaServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());

    @Autowired
    private GenericoDao<Venta, Long> ventaDao;
    @Autowired
    private GenericoDao<Predio, Long> predioDao;
    @Autowired
    private GenericoDao<Colindante, Long> colindanteDao;
    @Autowired
    private GenericoDao<Predioservicio, Long> predioservicioDao;
    @Autowired
    private GenericoDao<Captador, Long> captadorDao;
    @Autowired
    private GenericoDao<Personaventa, Long> personaventaDao;
    @Autowired
    private GenericoDao<Tipoexpediente, Long> tipoExpedientesDao;

    public VentaServicioImp(GenericoDao<Venta, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public long insertar(SaveVentaDTO entidad) throws GeneralException {
        Venta venta = entidad.getVenta();
        Predio predio = entidad.getPredio();
        Colindante colindante = entidad.getColindante();
        List<Long> servicios = entidad.getServicios();
        List<Personaventa> propietarioList = entidad.getPropietarioList();
        List<Personaventa> allegadosList = entidad.getAllegadosList();
        predio = guardarPredio(predio);
        if (predio != null) {
            venta = guardarventa(venta, predio);
        } else {
            throw new GeneralException("No existe predio", "No existe predio", loggerServicio);
        }
        if (servicios != null && servicios.size() > 0) {
            guardarPredioServicio(servicios, predio);
        }
        if (propietarioList != null && propietarioList.size() > 0) {
            guardarPersonaVenta(propietarioList, venta);
        } else {
            throw new GeneralException("No existen representantes", "No existen representantes", loggerServicio);
        }
        if (allegadosList != null && allegadosList.size() > 0) {
            guardarPersonaVenta(allegadosList, venta);
        } else {
            throw new GeneralException("No existen representantes", "No existen representantes", loggerServicio);
        }
        guardarColindante(colindante, predio);
        return venta.getId();
    }

    @Override
    public SaveVentaDTO obtener(Long id) throws GeneralException {
        SaveVentaDTO ventadto = new SaveVentaDTO();
        Venta venta = null;
        if (venta != null) {
//            venta.setVentaexpedienteList(null);
            Captador c = obtenerCaptador(venta.getId());
            Predio p = venta.getIdpredio();
            if (p != null) {
                Colindante colindante = obtenerColindante(p.getId());
                List<Long> ps = obtenerPrediosServicios(p.getId());
                ventadto.setColindante(colindante);
                ventadto.setServicios(ps);
            }
            List<Personaventa> propietarioList = listarPropietario(venta.getId());
            List<Personaventa> allegadosList = listarAllegados(venta.getId());
            ventadto.setVenta(venta);
            ventadto.setPredio(p);
            ventadto.setPropietarioList(propietarioList);
            ventadto.setAllegadosList(allegadosList);
        }
        return ventadto;
    }

    private Predio guardarPredio(Predio predio) {
        if (predio != null) {
            predio.setEstado(true);
            return predioDao.insertar(predio);
        } else {
            return null;
        }
    }

    private Venta guardarventa(Venta venta, Predio predio) {
        if (venta == null) {
            venta = new Venta();
        }
        venta.setIdpredio(predio);
//        venta.s(new Date());
        venta.setEstado(true);
        return ventaDao.insertar(venta);
    }

    @Override
    public void guardarPredioServicio(List<Long> servicios, Predio predio) {
        for (Long servicio : servicios) {
            Predioservicio ps = new Predioservicio(predio.getId(), servicio);
            ps.setEstado(true);
            predioservicioDao.actualizar(ps);
        }
    }

    private void guardarPersonaVenta(List<Personaventa> personaventa, Venta venta) {
        for (Personaventa personaventa1 : personaventa) {
            personaventa1.setIdventa(venta.getId());
            personaventa1.setEstado(true);
            personaventaDao.insertar(personaventa1);
        }
    }

    private void guardarColindante(Colindante colindante, Predio predio) {
        if (colindante == null) {
            colindante = new Colindante();
        }
        colindante.setIdpredio(predio);
        colindante.setEstado(true);
        colindanteDao.actualizar(colindante);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Venta entidadBuscar, BusquedaPaginada busquedaPaginada, String clientenombre, String clientedoc, String correlativo) {
        Criterio filtro;
        filtro = Criterio.forClass(Personaventa.class);
        filtro.createAlias("idpersona", "p");
        filtro.createAlias("idventa", "c", JoinType.RIGHT_OUTER_JOIN);
        filtro.add(Restrictions.eq("c.estado", true));
        filtro.add(Restrictions.eq("estado", true));
        filtro.add(Restrictions.isNull("idrelacion"));
        if (clientenombre != null && !clientenombre.equals("")) {
            filtro.add(Restrictions.or(
                Restrictions.ilike("p.nombre", '%' + clientenombre + '%'),
                Restrictions.ilike("p.appaterno", '%' + clientenombre + '%'),
                Restrictions.ilike("p.apmaterno", '%' + clientenombre + '%')));
        }
        if (clientedoc != null && !clientedoc.equals("")) {
            filtro.add(Restrictions.ilike("p.dni", '%' + clientedoc + '%'));
        }
        if (correlativo != null && !correlativo.equals("")) {
            filtro.add(Restrictions.ilike("c.correlativo1", '%' + correlativo + '%'));
        }
        busquedaPaginada.setTotalRegistros(ventaDao.cantidadPorCriteria(filtro, "c.id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.setProjection(Projections.projectionList()
                .add(Projections.distinct(Projections.property("c.id")))
                .add(Projections.property("c.id"), "id")
                .add(Projections.property("p.nombre"), "nombre")
                .add(Projections.property("p.appaterno"), "appaterno")
                .add(Projections.property("p.apmaterno"), "apmaterno")
                .add(Projections.property("p.dni"), "dni")
                .add(Projections.property("p.nombre"), "persona")
                .add(Projections.property("c.fecha"), "fecharegistro"));
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.asc("c.id"));
        busquedaPaginada.setRegistros(ventaDao.proyeccionPorCriteria(filtro, VentaDTO.class));
        return busquedaPaginada;
    }

    private Venta obtenerventa(Long id) {
        Venta comp = obtener(Venta.class, id);
        return comp;
    }

    private Colindante obtenerColindante(Long idpredio) {
        Criterio filtro;
        filtro = Criterio.forClass(Colindante.class);
        filtro.add(Restrictions.eq("idpredio.id", idpredio));
        Colindante col = colindanteDao.obtenerPorCriteriaSinProyecciones(filtro);
        return col;
    }

    private List<Long> obtenerPrediosServicios(Long id) {
        List<Long> s = new ArrayList<>();
        Criterio filtro;
        filtro = Criterio.forClass(Predioservicio.class);
        filtro.add(Restrictions.eq("predio.id", id));
        filtro.add(Restrictions.eq("estado", true));
        List<Predioservicio> ps = predioservicioDao.buscarPorCriteriaConProyecciones(filtro);
        for (int i = 0; i < ps.size(); i++) {
            s.add(ps.get(i).getServicios().getId());
        }
        return s;
    }

    private Captador obtenerCaptador(Long idventa) {
        Criterio filtro;
        filtro = Criterio.forClass(Captador.class);
        filtro.add(Restrictions.eq("idventa", idventa));
        Captador cap = captadorDao.obtenerPorCriteriaSinProyecciones(filtro);
        return cap;
    }

    private List<Personaventa> listarPropietario(Long id) {
        Criterio filtro;
        filtro = Criterio.forClass(Personaventa.class);
        filtro.add(Restrictions.isNull("idrelacion.id"));
        filtro.add(Restrictions.eq("idventa.id", id));
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        List<Personaventa> ps = personaventaDao.buscarPorCriteriaSinProyecciones(filtro);
        ps.stream().forEach((p) -> {
            p.getIdpersona().setPersonarolList(null);
        });
        return ps;
    }

    private List<Personaventa> listarAllegados(Long id) {
        Criterio filtro;
        filtro = Criterio.forClass(Personaventa.class);
        filtro.add(Restrictions.isNotNull("idrelacion.id"));
        filtro.add(Restrictions.eq("idventa.id", id));
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        List<Personaventa> ps = personaventaDao.buscarPorCriteriaSinProyecciones(filtro);
        ps.stream().forEach((p) -> {
            p.getIdpersona().setPersonarolList(null);
        });
        return ps;
    }

    @Override
    public Venta actualizar(SaveVentaDTO entidad) {
        Venta venta = entidad.getVenta();
        Predio predio = entidad.getPredio();
        Colindante colindante = entidad.getColindante();
        List<Personaventa> propietarioList = entidad.getPropietarioList();
        List<Personaventa> allegadosList = entidad.getAllegadosList();
        venta = ventaDao.actualizar(venta);
        predioDao.actualizar(predio);
        actualizarServicios(predio);
        this.guardarColindante(colindante, predio);
        actualizarPropietarios(propietarioList, venta.getId());
        allegadosList.stream().forEach((personaventa21) -> {
            personaventaDao.actualizar(personaventa21);
        });
        return venta;
    }

    private void actualizarServicios(Predio p) {
        List<Predioservicio> serviciosOld = this.obtenerPrediosServiciosAntiguos(p.getId());
        for (int i = 0; i < serviciosOld.size(); i++) {
            predioservicioDao.eliminar(serviciosOld.get(i));
        }
    }

    private List<Predioservicio> obtenerPrediosServiciosAntiguos(Long id) {
        Criterio filtro;
        filtro = Criterio.forClass(Predioservicio.class);
        filtro.add(Restrictions.eq("predio.id", id));
        List<Predioservicio> ps = predioservicioDao.buscarPorCriteriaConProyecciones(filtro);
        return ps;
    }

    private void actualizarPropietarios(List<Personaventa> propietarioList, Long id) {
        List<Personaventa> propietarioOld = this.listarPropietario(id);
        for (int i = 0; i < propietarioOld.size(); i++) {
            personaventaDao.eliminar(propietarioOld.get(i));
        }
        propietarioList.stream().forEach((personaventa1) -> {
            personaventaDao.actualizar(personaventa1);
        });
    }

}
