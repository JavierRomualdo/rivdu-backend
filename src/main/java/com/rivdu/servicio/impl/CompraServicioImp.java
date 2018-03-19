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
    private GenericoDao<Servicios, Long> servicioDao;
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
        Predioservicio[] predioservicio = new Predioservicio[servicios.length];
        Captador captador = entidad.getCaptador();
        Personacompra [] personacompra = entidad.getPersonacompra();
        
        predio.setEstado(true);
        colindante.setEstado(true);
        
        captador.setEstado(true);
        for (int i = 0; i < personacompra.length; i++) {
            personacompra[i].setEstado(true);
            personacompraDao.insertar(personacompra[i]);
        }
        
        compraDao.insertar(compra);
        predioDao.insertar(predio);
        colindanteDao.insertar(colindante);
        
        for (int i = 0; i < servicios.length; i++) {
            servicios[i].setEstado(true);
            servicioDao.insertar(servicios[i]);
            predioservicio[i] = new Predioservicio();
            predioservicio[i].setIdpredio(predio);
            predioservicio[i].setIdservicio(servicios[i]);
        }
        captadorDao.insertar(captador);
        
        
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
}
