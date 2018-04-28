/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.dto.RolMenuDTO;
import com.rivdu.entidades.Menutipousuario;
import com.rivdu.entidades.MenutipousuarioPK;
import com.rivdu.entidades.Rol;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.RolServicio;
import com.rivdu.util.Criterio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PROPIETARIO
 */
@Service
@Transactional
public class RolServicioImp extends GenericoServicioImpl<Rol, Long> implements  RolServicio {
    
    @Autowired
    private GenericoDao<Rol, Long> rolDao;
    @Autowired
    private GenericoDao<Menutipousuario, MenutipousuarioPK> menutipousuarioDao;
     
    public RolServicioImp(GenericoDao<Rol, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public List<Rol>  listar() throws GeneralException {
        Criterio filtro;
        filtro =Criterio.forClass(Rol.class);
        return rolDao.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
    public Rol crear(Rol entidad) throws GeneralException { //To change body of generated methods, choose Tools | Templates.
        entidad.setEstado(true);
        return rolDao.insertar(entidad);
    }

    @Override
    public Rol actualizar(Rol entidad) throws GeneralException { //To change body of generated methods, choose Tools | Templates.
        return rolDao.actualizar(entidad);
    }

    @Override
    public Boolean apilarmenu(RolMenuDTO entidad) throws GeneralException {
        List<Long> ids = entidad.getIds();
        for (int i = 0; i < ids.size(); i++) {
            Menutipousuario mtu = new Menutipousuario(ids.get(i), entidad.getIdrol());
            mtu.setEstado(Boolean.TRUE);
            menutipousuarioDao.actualizar(mtu);
        }
        return true;
    }

    @Override
    public Boolean desapilarmenu(RolMenuDTO entidad) throws GeneralException {
        List<Long> ids = entidad.getIds();
        for (int i = 0; i < ids.size(); i++) {
            Menutipousuario mtu = new Menutipousuario(ids.get(i), entidad.getIdrol());
            mtu.setEstado(Boolean.FALSE);
            menutipousuarioDao.actualizar(mtu);
        }
        return true;
    }
    
}
