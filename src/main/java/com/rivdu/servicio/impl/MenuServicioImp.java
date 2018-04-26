/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rivdu.dao.GenericoDao;
import com.rivdu.dto.MenuUsuarioDTO;
import com.rivdu.entidades.Menu;
import com.rivdu.entidades.Menutipousuario;
import com.rivdu.entidades.MenutipousuarioPK;
import com.rivdu.entidades.Usuarioacceso;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.MenuServicio;
import com.rivdu.util.Criterio;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 *
 * @author dev-out-03
 */

@Service
@Transactional
public class MenuServicioImp extends GenericoServicioImpl<Menu, Long> implements MenuServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenericoDao<Usuarioacceso, Long> usuarioAccesoDao;
    @Autowired
    private GenericoDao<Menutipousuario, MenutipousuarioPK> menuTipousuarioDao;

    public MenuServicioImp(GenericoDao<Menu, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Menu> listarMenus(String login) throws GeneralException {
        List<Long> ltu = obtenerRolesPorUserName(login);
        if (ltu.isEmpty()) {
            throw new GeneralException("El usuario no tiene tipos de usuario", "No existe tipos.", loggerServicio);
        }
        List<Menu> lm = obtenerMenusPadresPorTipoDeRol(ltu);
        obtenerSubmenus(lm, ltu);
        return lm;
    }

    private List<Long> obtenerRolesPorUserName(String login) {
        List<Long> ids = new ArrayList<>();
        Criterio filtro;
        filtro = Criterio.forClass(Usuarioacceso.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        filtro.createAlias("idrol", "rol");
        filtro.createAlias("idusuario", "usuario");
        filtro.add(Restrictions.eq("usuario.userId", login));
        List<Usuarioacceso> ua = usuarioAccesoDao.buscarPorCriteriaSinProyecciones(filtro);
        for (int i = 0; i < ua.size(); i++) {
            ids.add(ua.get(i).getIdrol().getId());
        }
        return ids;
    }

    private List<Menu> obtenerMenusPadresPorTipoDeRol(List<Long> ids) {
        Criterio filtro;
        filtro = Criterio.forClass(Menutipousuario.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        filtro.createAlias("idmenu", "menu");
        filtro.add(Restrictions.isNull("menu.idmenupadre"));
        filtro.add(Restrictions.in("menutipousuarioPK.idrol", ids));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.distinct(Projections.property("menutipousuarioPK.idmenu")))
                .add(Projections.property("menu.id"), "id")
                .add(Projections.property("menu.nombre"), "nombre")
                .add(Projections.property("menu.icono"), "icono")
                .add(Projections.property("menu.estado"), "estado")
                .add(Projections.property("menu.url"), "url")
                .add(Projections.property("menu.orden"), "orden")
                .add(Projections.property("menu.idmenupadre"), "idmenupadre")
        );
        filtro.addOrder(Order.asc("menu.orden"));
        List<Menu> lmtu = menuTipousuarioDao.proyeccionPorCriteria(filtro, Menu.class);
        return lmtu;
    }

    private void obtenerSubmenus(List<Menu> lm, List<Long> ids) {
        for (int i = 0; i < lm.size(); i++) {
            Criterio filtro;
            filtro = Criterio.forClass(Menutipousuario.class);
            filtro.add(Restrictions.eq("estado", Boolean.TRUE));
            filtro.createAlias("idmenu", "menu");
            filtro.add(Restrictions.eq("menu.idmenupadre", lm.get(i).getId()));
            filtro.add(Restrictions.in("menutipousuarioPK.idrol", ids));
            filtro.setProjection(Projections.projectionList()
                    .add(Projections.distinct(Projections.property("menutipousuarioPK.idmenu")))
                    .add(Projections.property("menu.id"), "id")
                    .add(Projections.property("menu.nombre"), "nombre")
                    .add(Projections.property("menu.icono"), "icono")
                    .add(Projections.property("menu.estado"), "estado")
                    .add(Projections.property("menu.url"), "url")
                    .add(Projections.property("menu.orden"), "orden")
                    .add(Projections.property("menu.idmenupadre"), "idmenupadre")
            );
            filtro.addOrder(Order.asc("menu.orden"));
            List<Menu> lmhijos = menuTipousuarioDao.proyeccionPorCriteria(filtro, Menu.class);
            lm.get(i).setMenuList(lmhijos);
            obtenerSubmenus(lmhijos, ids);
        }
    }

    //lista de menu select
    @Override
    public List<MenuUsuarioDTO> listarMenuSelect(Long idRol) throws GeneralException {
        List<MenuUsuarioDTO> mudtoList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        ids.add(idRol);      
        //listar los menus padre
        List<Menu> listMenu = obtenerMenusPadresPorTipoDeRol(ids);
        obtenerSubmenus(listMenu, ids);
        for (int i = 0; i <listMenu.size(); i++) {
            MenuUsuarioDTO mudtopadre=new MenuUsuarioDTO();
            List<MenuUsuarioDTO> mudtohijoList=new ArrayList<>();
            mudtopadre.setId(listMenu.get(i).getId());
            mudtopadre.setLabel(listMenu.get(i).getNombre());
            //recorrer la lista  hijos
            for (Menu menuList : listMenu.get(i).getMenuList()) {
                MenuUsuarioDTO mudtohijo=new MenuUsuarioDTO();
                mudtohijo.setId(menuList.getId());
                mudtohijo.setLabel(menuList.getNombre());  
                mudtohijoList.add(mudtohijo);
                
            } 
            mudtopadre.setChildren(mudtohijoList);
            mudtoList.add(i, mudtopadre);
            
        }
      return mudtoList;
    }
         
}
