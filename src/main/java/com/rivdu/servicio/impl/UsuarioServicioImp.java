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
import com.rivdu.dto.UsuarioEdicionDTO;
import com.rivdu.entidades.Persona;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private GenericoDao<Usuarioacceso, Long> usuarioAccesoDao;

    public UsuarioServicioImp(GenericoDao<Usuario, Long> genericoHibernate) {
        super(genericoHibernate);
    }
    
    @Override
    public BusquedaPaginada busquedaPaginada(Usuario entidadBuscar, BusquedaPaginada busquedaPaginada, String numdoc, String nomusu) {
        Criterio filtro;
        filtro = Criterio.forClass(Usuario.class);
        if (numdoc != null && !numdoc.equals("")) {
            filtro.add(Restrictions.ilike("dni", '%' + numdoc + '%'));
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
    public Usuario show(String username) throws GeneralException {
        Criterio filtro;
        filtro = Criterio.forClass(Usuario.class);
        filtro.add(Restrictions.eq("userId", username));
        Usuario u = usuarioDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u == null || !u.getEstado()) {
            throw new GeneralException("Este usuario no esta habilitado", "El usuario fue dado de baja.", loggerServicio);
        }
        if(u.getIdempresa()!=null && u.getIdempresa().getIdgerente()!=null){
                Persona g = new Persona(u.getIdempresa().getIdgerente().getId());
                u.getIdempresa().setIdgerente(g);
            }
        u.setUsuarioaccesoList(null);
        return u;
    }
    
    @Override
    public Usuario insertar(Usuario entidad) throws GeneralException {
        Criterio filtro;
        filtro = Criterio.forClass(Usuario.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (entidad.getId() != null) {
            filtro.add(Restrictions.eq("id", entidad.getId()));
        }
        filtro.add(Restrictions.eq("userId", entidad.getUserId()));
        Usuario u = usuarioDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u != null) {
            throw new GeneralException("Guardar retorno nulo", "Ya existe un usuario con igual nombre.", loggerServicio);
        }
        entidad.setEstado(Boolean.TRUE);
        entidad.setCambiarclave(Boolean.TRUE);
        List<Usuarioacceso> uaList = entidad.getUsuarioaccesoList();
        entidad.setUsuarioaccesoList(null);
        usuarioDao.insertar(entidad);
        for (int i = 0; i < uaList.size(); i++) {
            Usuario usuario = new Usuario(entidad.getId());
            uaList.get(i).setIdusuario(usuario);
            uaList.get(i).setEstado(true);
            usuarioAccesoDao.insertar(uaList.get(i));
        }
        return entidad;
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
            if(u.getIdempresa()!=null && u.getIdempresa().getIdgerente()!=null){
                Persona g = new Persona(u.getIdempresa().getIdgerente().getId());
                u.getIdempresa().setIdgerente(g);
            }
            
        }
        return u;
    }

    @Override
    public UsuarioEdicionDTO obtenerParaEdicion(Long id) throws GeneralException {
        UsuarioEdicionDTO uedto = new UsuarioEdicionDTO();
        Usuario u = obtener(Usuario.class, id);
        if(u!=null){
            List<Usuarioacceso> uaList = u.getUsuarioaccesoList();
            uaList.stream().forEach((pr) -> {
                Usuario us = new Usuario(id);
                pr.setIdusuario(us);
            });
            u.setUsuarioaccesoList(null);
            if(u.getIdempresa()!=null && u.getIdempresa().getIdgerente()!=null){
                Persona g = new Persona(u.getIdempresa().getIdgerente().getId());
                u.getIdempresa().setIdgerente(g);
            }
            uedto.setUsuarioaccesoList(uaList);
        }
        uedto.setUsuario(u);
        return uedto;
    }
    
    @Override
    public Usuario validarDni(String dni) throws GeneralException {
        Criterio filtro;
        filtro = Criterio.forClass(Usuario.class);
        filtro.add(Restrictions.eq("dni", dni));
        Usuario e = usuarioDao.obtenerPorCriteriaSinProyecciones(filtro);
        if (e!=null && !e.getUsuarioaccesoList().isEmpty()) {
            List<Usuarioacceso> uaList = e.getUsuarioaccesoList();
            uaList.stream().forEach((pr) -> {
                Usuario us = new Usuario(e.getId());
                pr.setIdusuario(us);
            });
            e.setUsuarioaccesoList(uaList);
            if(e.getIdempresa()!=null && e.getIdempresa().getIdgerente()!=null){
                Persona g = new Persona(e.getIdempresa().getIdgerente().getId());
                e.getIdempresa().setIdgerente(g);
            }
        }
        return e;
    }
    
    @Override
    public boolean validarNuevaPassword(String username, String passwordTipeada) throws GeneralException {
        Usuario usuario = this.show(username);
        if (usuario != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(passwordTipeada, usuario.getPassword());
        }
        return false;
        
    }
    
}
