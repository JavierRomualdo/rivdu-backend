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
         entidad.setEstado(true);
        return cuentabancodao.insertar(entidad);
    }
    
    
}
