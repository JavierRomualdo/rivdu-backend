/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Centrocostos;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CentroCostoServicio;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author LUIS ORTIZ
 */
@Service
@Transactional
public class CentroCostosServicioImp extends GenericoServicioImpl<Centrocostos, Long> implements CentroCostoServicio{

     @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private GenericoDao<Centrocostos, Long> centrocostosdao;
    
    public CentroCostosServicioImp(GenericoDao<Centrocostos, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Centrocostos> listar() {
        Criterio filtro;
        filtro =Criterio.forClass(Centrocostos.class);
        return centrocostosdao.buscarPorCriteriaSinProyecciones(filtro);
    }

     @Override
    public Centrocostos actualizar(Centrocostos entidad) throws GeneralException{
        return centrocostosdao.actualizar(entidad);
    }

    @Override
    public Centrocostos crear(Centrocostos entidad) {
       entidad.setEstado(true);
        return centrocostosdao.insertar(entidad); 
    }

      
}
