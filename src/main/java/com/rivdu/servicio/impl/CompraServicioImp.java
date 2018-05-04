/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.dto.CompraDTO;
import com.rivdu.dto.ExpedienteChildrenDTO;
import com.rivdu.dto.ExpedientesDTO;
import com.rivdu.dto.SaveCompraDTO;
import com.rivdu.entidades.Captador;
import com.rivdu.entidades.Colindante;
import com.rivdu.entidades.Compra;
import com.rivdu.entidades.Compraexpediente;
import com.rivdu.entidades.CompraexpedientePK;
import com.rivdu.entidades.Personacompra;
import com.rivdu.entidades.Predio;
import com.rivdu.entidades.Predioservicio;
import com.rivdu.entidades.Tipoexpediente;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CompraServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.ArrayList;
import java.util.Date;
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
public class CompraServicioImp extends GenericoServicioImpl<Compra, Long> implements CompraServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());

    @Autowired
    private GenericoDao<Compra, Long> compraDao;
    @Autowired
    private GenericoDao<Predio, Long> predioDao;
    @Autowired
    private GenericoDao<Colindante, Long> colindanteDao;
    @Autowired
    private GenericoDao<Predioservicio, Long> predioservicioDao;
    @Autowired
    private GenericoDao<Captador, Long> captadorDao;
    @Autowired
    private GenericoDao<Personacompra, Long> personacompraDao;
    @Autowired
    private GenericoDao<Tipoexpediente, Long> tipoExpedientesDao;
    @Autowired
    private GenericoDao<Compraexpediente, CompraexpedientePK> compraExpedienteDao;

    public CompraServicioImp(GenericoDao<Compra, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public long insertar(SaveCompraDTO entidad) throws GeneralException {
        Compra compra = entidad.getCompra();
        Predio predio = entidad.getPredio();
        Colindante colindante = entidad.getColindante();
        List<Long> servicios = entidad.getServicios();
        Captador captador = entidad.getCaptador();
        List<Personacompra> propietarioList = entidad.getPropietarioList();
        List<Personacompra> allegadosList = entidad.getAllegadosList();
        predio = guardarPredio(predio);
        if (predio != null) {
            compra = guardarcompra(compra, predio);
        } else {
            throw new GeneralException("No existe predio", "No existe predio", loggerServicio);
        }
        if (servicios != null && servicios.size() > 0) {
            guardarPredioServicio(servicios, predio);
        }
        if (propietarioList != null && propietarioList.size() > 0) {
            guardarPersonaCompra(propietarioList, compra);
        } else {
            throw new GeneralException("No existen representantes", "No existen representantes", loggerServicio);
        }
        if (allegadosList != null && allegadosList.size() > 0) {
            guardarPersonaCompra(allegadosList, compra);
        } else {
            throw new GeneralException("No existen representantes", "No existen representantes", loggerServicio);
        }
        guardarCaptador(captador, compra);
        guardarColindante(colindante, predio);
        return compra.getId();
    }

    @Override
    public SaveCompraDTO obtener(Long id) throws GeneralException {
        SaveCompraDTO compradto = new SaveCompraDTO();
        Compra compra = obtenerCompra(id);
        if (compra != null) {
            compra.setCompraexpedienteList(null);
            Captador c = obtenerCaptador(compra.getId());
            Predio p = compra.getIdpredio();
            if (p != null) {
                Colindante colindante = obtenerColindante(p.getId());
                List<Long> ps = obtenerPrediosServicios(p.getId());
                compradto.setColindante(colindante);
                compradto.setServicios(ps);
            }
            List<Personacompra> propietarioList = listarPropietario(compra.getId());
            List<Personacompra> allegadosList = listarAllegados(compra.getId());
            compradto.setCompra(compra);
            compradto.setPredio(p);
            compradto.setCaptador(c);
            compradto.setPropietarioList(propietarioList);
            compradto.setAllegadosList(allegadosList);
        }
        return compradto;
    }

    private Predio guardarPredio(Predio predio) {
        if (predio != null) {
            predio.setEstado(true);
            return predioDao.insertar(predio);
        } else {
            return null;
        }
    }

    private Compra guardarcompra(Compra compra, Predio predio) {
        if (compra == null) {
            compra = new Compra();
        }
        compra.setIdpredio(predio);
        compra.setFecha(new Date());
        compra.setEstado(true);
        return compraDao.insertar(compra);
    }

    @Override
    public void guardarPredioServicio(List<Long> servicios, Predio predio) {
        for (Long servicio : servicios) {
            Predioservicio ps = new Predioservicio(predio.getId(), servicio);
            ps.setEstado(true);
            predioservicioDao.actualizar(ps);
        }
    }

    private void guardarPersonaCompra(List<Personacompra> personacompra, Compra compra) {
        for (Personacompra personacompra1 : personacompra) {
            personacompra1.setIdcompra(compra);
            personacompra1.setEstado(true);
            personacompraDao.insertar(personacompra1);
        }
    }

    private void guardarCaptador(Captador captador, Compra compra) {
        if (captador != null && captador.getNombre() != null) {
            captador.setEstado(true);
            captador.setIdcompra(compra.getId());
            captadorDao.actualizar(captador);
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
    public BusquedaPaginada busquedaPaginada(Compra entidadBuscar, BusquedaPaginada busquedaPaginada, String clientenombre, String clientedoc, String correlativo) {
        Criterio filtro;
        filtro = Criterio.forClass(Personacompra.class);
        filtro.createAlias("idpersona", "p");
        filtro.createAlias("idcompra", "c", JoinType.RIGHT_OUTER_JOIN);
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
        busquedaPaginada.setTotalRegistros(compraDao.cantidadPorCriteria(filtro, "c.id"));
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
        busquedaPaginada.setRegistros(compraDao.proyeccionPorCriteria(filtro, CompraDTO.class));
        return busquedaPaginada;
    }

    private Compra obtenerCompra(Long id) {
        Compra comp = obtener(Compra.class, id);
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

    private Captador obtenerCaptador(Long idcompra) {
        Criterio filtro;
        filtro = Criterio.forClass(Captador.class);
        filtro.add(Restrictions.eq("idcompra", idcompra));
        Captador cap = captadorDao.obtenerPorCriteriaSinProyecciones(filtro);
        return cap;
    }

    private List<Personacompra> listarPropietario(Long id) {
        Criterio filtro;
        filtro = Criterio.forClass(Personacompra.class);
        filtro.add(Restrictions.isNull("idrelacion.id"));
        filtro.add(Restrictions.eq("idcompra.id", id));
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        List<Personacompra> ps = personacompraDao.buscarPorCriteriaSinProyecciones(filtro);
        ps.stream().forEach((p) -> {
            p.getIdpersona().setPersonarolList(null);
        });
        return ps;
    }

    @Override
    public List<ExpedientesDTO> listarExpedientes(Long id) {
        List<ExpedientesDTO> lista = listarCarpetas();
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setIdcompra(id);
            obtenerDocumentos(lista.get(i), id);
        }
        return lista;
    }

    private List<ExpedientesDTO> listarCarpetas() {
        Criterio filtro;
        filtro = Criterio.forClass(Tipoexpediente.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("nombre"), "label"));
        return tipoExpedientesDao.proyeccionPorCriteria(filtro, ExpedientesDTO.class);
    }

    private void obtenerDocumentos(ExpedientesDTO get, Long id) {
        Criterio filtro;
        filtro = Criterio.forClass(Compraexpediente.class);
        filtro.createAlias("compra", "c");
        filtro.createAlias("expediente", "e");
        filtro.createAlias("e.idtipoexpediente", "te");
        filtro.add(Restrictions.eq("estado", true));
        filtro.add(Restrictions.eq("c.id", id));
        filtro.add(Restrictions.eq("te.id", get.getId()));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("e.id"), "id")
                .add(Projections.property("e.nombre"), "nombre")
                .add(Projections.property("e.contenttype"), "contenttype")
                .add(Projections.property("e.tipofile"), "tipo"));
        get.setChildren(compraExpedienteDao.proyeccionPorCriteria(filtro, ExpedienteChildrenDTO.class));
    }

    private List<Personacompra> listarAllegados(Long id) {
        Criterio filtro;
        filtro = Criterio.forClass(Personacompra.class);
        filtro.add(Restrictions.isNotNull("idrelacion.id"));
        filtro.add(Restrictions.eq("idcompra.id", id));
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        List<Personacompra> ps = personacompraDao.buscarPorCriteriaSinProyecciones(filtro);
        ps.stream().forEach((p) -> {
            p.getIdpersona().setPersonarolList(null);
        });
        return ps;
    }

    @Override
    public Compra actualizar(SaveCompraDTO entidad) {
        Compra compra = entidad.getCompra();
        Predio predio = entidad.getPredio();
        Colindante colindante = entidad.getColindante();
        Captador captador = entidad.getCaptador();
        List<Personacompra> propietarioList = entidad.getPropietarioList();
        List<Personacompra> allegadosList = entidad.getAllegadosList();
        compra = compraDao.actualizar(compra);
        predioDao.actualizar(predio);
        actualizarServicios(predio);
        this.guardarColindante(colindante, predio);
        this.guardarCaptador(captador, compra);
        if (propietarioList != null && propietarioList.size() > 0) {
            actualizarPropietarios(propietarioList, compra.getId());
        } else {
            throw new GeneralException("No existen representantes", "No existen representantes", loggerServicio);
        }
        if (allegadosList != null && allegadosList.size() > 0) {
            allegadosList.stream().forEach((personacompra21) -> {
                personacompraDao.actualizar(personacompra21);
            });
        } else {
            throw new GeneralException("No existen representantes", "No existen representantes", loggerServicio);
        }
        return compra;
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

    private void actualizarPropietarios(List<Personacompra> propietarioList, Long id) {
        List<Personacompra> propietarioOld = this.listarPropietario(id);
        for (int i = 0; i < propietarioOld.size(); i++) {
            personacompraDao.eliminar(propietarioOld.get(i));
        }
        propietarioList.stream().forEach((personacompra1) -> {
            personacompraDao.insertar(personacompra1);
        });
    }

}
