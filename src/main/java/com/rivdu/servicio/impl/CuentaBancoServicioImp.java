/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Cuentabanco;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CuentaBancoServicio;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.SessionFactory;
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
public class CuentaBancoServicioImp extends GenericoServicioImpl<Cuentabanco, Long> implements CuentaBancoServicio{

     private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private GenericoDao<Cuentabanco, Long> cuentabancodao;
    
    public CuentaBancoServicioImp(GenericoDao<Cuentabanco, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Cuentabanco> listar() throws GeneralException {
        Criterio filtro;
        filtro =Criterio.forClass(Cuentabanco.class);
        return cuentabancodao.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
    public Cuentabanco actualizar(Cuentabanco entidad) throws GeneralException{
        return cuentabancodao.actualizar(entidad);
    }

    @Override
    public Cuentabanco crear(Cuentabanco entidad) {
        VerificarCuentaRepetidad(entidad);
         entidad.setEstado(true);
        return cuentabancodao.insertar(entidad);
    }

    private void VerificarCuentaRepetidad(Cuentabanco entidad) {
       Criterio filtro;
        filtro = Criterio.forClass(Cuentabanco.class);
        if (entidad.getId()!=null) {
            filtro.add(Restrictions.ne("id", entidad.getId()));
        }
        filtro.add(Restrictions.eq("numerocuenta", entidad.getNumerocuenta()));
        Cuentabanco u = cuentabancodao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u!=null) {
            throw new GeneralException("Ya existe la cuenta", "Guardar retorno nulo", loggerServicio);
        } 
    }
    
    
}
