/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Empresa;
import com.rivdu.entidades.Usuario;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.EmpresaServicio;
import com.rivdu.util.Criterio;
/**
 *
 * @author dev-out-03
 */

@Service
@Transactional
public class EmpresaServicioImp extends GenericoServicioImpl<Empresa, Long> implements EmpresaServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Empresa, Long> empresaDao;

    public EmpresaServicioImp(GenericoDao<Empresa, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public Empresa validar(String ruc) throws GeneralException{
        Criterio filtro;
        filtro = Criterio.forClass(Empresa.class);
        filtro.add(Restrictions.eq("ruc", ruc));
        Empresa e = empresaDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (e!=null && !e.isEstado()) {
            throw new GeneralException("Esta empresa no esta habilitada", "La empresa fue dada de baja.", loggerServicio);
        }
        if(e!=null && e.getIdgerente()!=null) {
            e.getIdgerente().setPersonarolList(null);
        }
        return e;
    }
    
    @Override
    public Empresa actualizar(Empresa e) throws GeneralException {
        return empresaDao.actualizar(e);
    }
    
}
