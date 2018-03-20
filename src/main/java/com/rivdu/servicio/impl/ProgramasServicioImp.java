/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Programas;
import com.rivdu.entidades.Responsable;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ProgramasServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Christhian
 */
@Service
@Transactional
public class ProgramasServicioImp extends GenericoServicioImpl<Programas, Long> implements ProgramasServicio {
    
    @Autowired
    private GenericoDao<Programas, Long> programasDao;
    
     @Autowired
    private GenericoDao<Responsable,Long> responsableDao;
   
     public ProgramasServicioImp  (GenericoDao<Programas, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public Programas crear(Programas entidad) throws GeneralException {
        entidad.setEstado(true);
        List<Responsable> responsables = entidad.getResponsableList();
        entidad.setResponsableList(null);
        entidad = programasDao.insertar(entidad);
        if(responsables != null){
            for (Responsable re : responsables) {
               re.setIdprograma(entidad.getId());
               re.setEstado(true);
               responsableDao.insertar(re);
            }
        }
        return entidad;    
    }

    @Override
    public Programas actualizar(Programas producto) throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Programas obtener(Long id) throws GeneralException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
