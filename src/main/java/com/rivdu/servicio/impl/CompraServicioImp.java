/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.dto.SaveCompraDTO;
import com.rivdu.entidades.Captador;
import com.rivdu.entidades.Colindante;
import com.rivdu.entidades.Compra;
import com.rivdu.entidades.Personacompra;
import com.rivdu.entidades.Predio;
import com.rivdu.entidades.Predioservicio;
import com.rivdu.entidades.Servicios;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CompraServicio;
import java.util.List;
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
    
 
    public CompraServicioImp(GenericoDao<Compra, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Compra> listar() throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
   public long insertar(SaveCompraDTO entidad) throws GeneralException {
        Compra compra = entidad.getCompra();
        Predio predio = entidad.getPredio();
        Colindante colindante = entidad.getColindante();
        Servicios[] servicios = entidad.getServicios();
        Captador captador = entidad.getCaptador();
        Personacompra [] personacompra = entidad.getPersonacompra();
        Personacompra [] personacompra2 = entidad.getPersonacompra2();
        predio = guardarPredio(predio);
        if(predio!=null){
            compra = guardarcompra(compra, predio);
        } else {
           throw new GeneralException("No existe predio", "No existe predio", loggerServicio);
        }
        if (servicios!=null && servicios.length>0) {
           guardarPredioServicio(servicios, predio); 
        }
        if(personacompra!=null && personacompra.length>0){
           guardarPersonaCompra(personacompra, compra); 
        }
        if(personacompra2!=null && personacompra2.length>0){
            guardarPersonaCompra(personacompra2, compra);
        }
        guardarCaptador(captador, compra);
        guardarColindante(colindante, compra, predio);
        return compra.getId();
    }

    @Override
    public Compra actualizar(Compra producto) throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Compra obtener(Long id) throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Predio guardarPredio(Predio predio) {
        if(predio!=null){
            predio.setEstado(true);
            return predioDao.insertar(predio);
        } else{
            return null;
        }
    }

    private Compra guardarcompra(Compra compra, Predio predio) {
        if(compra==null){
            compra = new Compra();
        }
        compra.setIdpredio(predio);
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

    private void guardarPersonaCompra(Personacompra[] personacompra, Compra compra) {
        for (Personacompra personacompra1 : personacompra) {
            personacompra1.setIdcompra(compra.getId());
            personacompra1.setEstado(true);
            personacompraDao.insertar(personacompra1);
        }
    }

    private void guardarCaptador(Captador captador, Compra compra) {
        if(captador!=null){
            captador.setEstado(true);
            captador.setIdcompra(compra);
            captadorDao.insertar(captador);
        }
    }

    private void guardarColindante(Colindante colindante, Compra compra, Predio predio) {
        if(colindante!=null){
            colindante.setIdpredio(predio);
            colindante.setEstado(true);
            colindanteDao.insertar(colindante);
        }
    }
}
