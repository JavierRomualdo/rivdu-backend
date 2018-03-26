/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Personarol;
import com.rivdu.entidades.Programas;
import com.rivdu.entidades.Responsable;
import com.rivdu.entidades.Programaespecificacion;
import com.rivdu.entidades.ProgramaespecificacionPK;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ProgramasServicio;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.criterion.Projections;
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
    private GenericoDao<Responsable, Long> responsableDao;
    @Autowired
    private GenericoDao<Programaespecificacion, Long> programaespecificacionDAO;

    public ProgramasServicioImp(GenericoDao<Programas, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public Programas crear(Programas entidad) throws GeneralException {
        entidad.setEstado(true);
        List<Responsable> responsables = entidad.getResponsableList();
        entidad.setResponsableList(null);
        List<Programaespecificacion> proespecificaciones = entidad.getProgramaespecificacionesList();
        entidad.setProgramaespecificacionesList(null);
        entidad = programasDao.insertar(entidad);
        for (Responsable re : responsables) {
            re.setIdprograma(entidad.getId());
            re.setEstado(true);
            responsableDao.insertar(re);
        }
        for (Programaespecificacion es : proespecificaciones) {
            ProgramaespecificacionPK pk = es.getProgramaespecificacionPK();
            pk.setIdprograma(entidad.getId());
            es.setEstado(true);
            es.setProgramaespecificacionPK(pk);
            programaespecificacionDAO.insertar(es);

        }

        return entidad;
    }

    @Override
    public List<Programas> listar() throws GeneralException {
        Criterio filtro;
        filtro =Criterio.forClass(Programas.class);
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("id"), "id")
                .add(Projections.property("nombre"),"nombre")
                .add(Projections.property("importe"), "importe")
                .add(Projections.property("codigoet"), "codigoet")
                .add(Projections.property("estado"), "estado"));
        return programasDao.proyeccionPorCriteria(filtro, Programas.class);
    }


    @Override
    public Programas actualizar(Programas entidad) throws GeneralException {
        List<Responsable> responsables = entidad.getResponsableList();
        List<Programaespecificacion> proespecificciones=entidad.getProgramaespecificacionesList();
        if(responsables != null){
            responsables.stream().forEach((pm) -> {
                responsableDao.actualizar(pm);
            });
        }
        if(proespecificciones != null){
            proespecificciones.stream().forEach((pe) -> {
                programaespecificacionDAO.actualizar(pe);
            });
        }
        
        return programasDao.actualizar(entidad);
    }
    @Override
    public Programas obtener(Long id) throws GeneralException {
     Programas p=obtener(Programas.class, id);
        for(Responsable re:p.getResponsableList()){
            List<Personarol> pr = re.getIdpersona().getPersonarolList();
            for (int i = 0; i < pr.size(); i++) {
                pr.get(i).setIdpersona(null);
            }
        }
        for(Programaespecificacion pe:p.getProgramaespecificacionesList()){
            pe.setIdprograma(null);
        }
        return p;   
    }

}
