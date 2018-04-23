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
import com.rivdu.entidades.Servicios;
import com.rivdu.entidades.Tipoexpediente;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CompraServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
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
        Servicios[] servicios = entidad.getServicios();
        Captador captador = entidad.getCaptador();
        List<Personacompra> personacompra = entidad.getPersonacompra();
        List<Personacompra> personacompra2 = entidad.getPersonacompra2();
        predio = guardarPredio(predio);
        if (predio != null) {
            compra = guardarcompra(compra, predio);
        } else {
            throw new GeneralException("No existe predio", "No existe predio", loggerServicio);
        }
        if (servicios != null && servicios.length > 0) {
            guardarPredioServicio(servicios, predio);
        }
        if (personacompra != null && personacompra.size() > 0) {
            guardarPersonaCompra(personacompra, compra);
        }
        if (personacompra2 != null && personacompra2.size() > 0) {
            guardarPersonaCompra(personacompra2, compra);
        }
        guardarCaptador(captador, compra);
        guardarColindante(colindante, compra, predio);
        return compra.getId();
    }

   /* @Override*/
    /*public Compra actualizar(Compra producto) throws GeneralException {
    /*    Compra compra = entidad.getCompra();
        Predio predio = entidad.getPredio();
        Colindante colindante = entidad.getColindante();
        Servicios[] servicios = entidad.getServicios();
        Captador captador = entidad.getCaptador();
        List<Personacompra> personacompra = entidad.getPersonacompra();
        List<Personacompra> personacompra2 = entidad.getPersonacompra2();*/
    //    return null;
    

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
               // Servicios[] ps = obtenerPrediosServicios(p.getId());
                compradto.setColindante(colindante);
              //  compradto.setServicios(ps);
            }
            List<Personacompra> pclist = listarPersonasCompra(compra.getId());
            List<Personacompra> pclist2 = listarPersonasCompra2(compra.getId());
            compradto.setCompra(compra);
            compradto.setPredio(p);
            compradto.setCaptador(c);
            compradto.setPersonacompra(pclist);
            compradto.setPersonacompra2(pclist2);
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

    private void guardarPredioServicio(Servicios[] servicios, Predio predio) {
        for (Servicios servicio : servicios) {
            Predioservicio ps = new Predioservicio();
            ps.setEstado(true);
            ps.setIdpredio(predio);
            ps.setIdservicio(servicio);
            predioservicioDao.insertar(ps);
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
        if (captador != null) {
            captador.setEstado(true);
            captador.setIdcompra(compra.getId());
            captadorDao.insertar(captador);
        }
    }

    private void guardarColindante(Colindante colindante, Compra compra, Predio predio) {
        if (colindante == null) {
            colindante = new Colindante();
        }
        colindante.setIdpredio(predio);
        colindante.setEstado(true);
        colindanteDao.insertar(colindante);

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
            filtro.add(Restrictions.ilike("p.nombre", '%' + clientenombre + '%'));
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
                .add(Projections.property("p.apellido"), "apellido")
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

    private Servicios[] obtenerPrediosServicios(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Captador obtenerCaptador(Long idcompra) {
        Criterio filtro;
        filtro = Criterio.forClass(Captador.class);
        filtro.add(Restrictions.eq("idcompra", idcompra));
        Captador cap = captadorDao.obtenerPorCriteriaSinProyecciones(filtro);
        return cap;
    }

    private List<Personacompra> listarPersonasCompra(Long id) {
       Criterio filtro;
       filtro =Criterio.forClass(Personacompra.class);
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

    private List<Personacompra> listarPersonasCompra2(Long id) {
        Criterio filtro;
       filtro =Criterio.forClass(Personacompra.class);
       filtro.add(Restrictions.isNotNull("idrelacion.id"));
       filtro.add(Restrictions.eq("idcompra.id", id));
       filtro.add(Restrictions.eq("estado", Boolean.TRUE));
       List<Personacompra> ps= personacompraDao.buscarPorCriteriaSinProyecciones(filtro);
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
        Servicios[] servicios = entidad.getServicios();
        Captador captador = entidad.getCaptador();
        List<Personacompra> personacompra = entidad.getPersonacompra();
        List<Personacompra> personacompra2 = entidad.getPersonacompra2();
        
        
        compra = compraDao.actualizar(compra);
        predioDao.actualizar(predio);
        colindanteDao.actualizar(colindante);
        captadorDao.actualizar(captador);
        personacompra.stream().forEach((personacompra1) -> {
            personacompraDao.actualizar(personacompra1);
        });
        
        personacompra2.stream().forEach((personacompra21) -> {
            personacompraDao.actualizar(personacompra21);
        });
        
        return compra;
    }
}