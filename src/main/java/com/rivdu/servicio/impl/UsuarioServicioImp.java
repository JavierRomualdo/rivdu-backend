/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Personarol;
import com.rivdu.entidades.PersonarolPK;
import com.rivdu.entidades.Usuario;
import com.rivdu.entidades.Usuarioacceso;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.UsuarioServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.criterion.Projections;
/**
 *
 * @author dev-out-03
 */

@Service
@Transactional
public class UsuarioServicioImp extends GenericoServicioImpl<Usuario, Long> implements UsuarioServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Usuario, Long> usuarioDao;
    @Autowired
    private GenericoDao<Personarol, PersonarolPK> personarolDao;

    public UsuarioServicioImp(GenericoDao<Usuario, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public BusquedaPaginada busquedaPaginada(Usuario entidadBuscar, BusquedaPaginada busquedaPaginada, String numdoc, String nomusu) {
        Criterio filtro;
        filtro = Criterio.forClass(Usuario.class);
        //filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (numdoc!= null && !numdoc.equals("")) {
            filtro.add(Restrictions.ilike("dni", '%'+numdoc+'%'));
        }
        busquedaPaginada.setTotalRegistros(usuarioDao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("id"),"id")
                .add(Projections.property("userId"),"userId")
                .add(Projections.property("dni"),"dni")
                .add(Projections.property("nombre"),"nombre")
                .add(Projections.property("estado"),"estado"));
          
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.desc("id"));
        List<Usuario> u = usuarioDao.proyeccionPorCriteria(filtro, Usuario.class);
        busquedaPaginada.setRegistros(u);
        return busquedaPaginada;
    }
   
    @Override
    public Usuario insertar(Usuario entidad) throws GeneralException{
        Criterio filtro;
        filtro = Criterio.forClass(Usuario.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (entidad.getId()!=null) {
            filtro.add(Restrictions.eq("id", entidad.getId()));
        }
        filtro.add(Restrictions.eq("userId", entidad.getUserId()));
        Usuario u = usuarioDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u!=null) {
            throw new GeneralException("Guardar retorno nulo", "Ya existe un usuario con igual nombre.", loggerServicio);
        }
        entidad.setEstado(Boolean.TRUE);
        entidad.setCambiarclave(Boolean.TRUE);
        return usuarioDao.insertar(entidad);
    }
    
    @Override
    public Usuario actualizar(Usuario u) throws GeneralException {
        
        return usuarioDao.actualizar(u);
    }

    @Override
    public Usuario obtener(long id) throws GeneralException {
        Usuario u=obtener(Usuario.class,id);
        if(u!=null){
            for(Usuarioacceso pr:u.getUsuarioaccesoList()){
                pr.getIdusuario().setUsuarioaccesoList(null);
            }
            if(u.getIdempresa()!=null){
                 u.getIdempresa().setIdgerente(null);
            }
            
        }
        return u;
    }

    @Override
    public Usuario validarDni(String dni) throws GeneralException {
        Criterio filtro;
        filtro = Criterio.forClass(Usuario.class);
        filtro.add(Restrictions.eq("dni", dni));
        Usuario e = usuarioDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (e!=null && !e.getUsuarioaccesoList().isEmpty()) {
            for (Usuarioacceso personarolList : e.getUsuarioaccesoList()) {
                personarolList.setIdusuario(null);
           }
        }
        return e;
    }

   
    
}
