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
import com.rivdu.entidades.Menu;
import com.rivdu.entidades.Menutipousuario;
import com.rivdu.entidades.MenutipousuarioPK;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.MenuServicio;
import com.rivdu.util.Criterio;
/**
 *
 * @author dev-out-03
 */

@Service
@Transactional
public class MenuServicioImp extends GenericoServicioImpl<Menu, Integer> implements MenuServicio {

    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenericoDao<Menutipousuario, MenutipousuarioPK> menuTipousuarioDao;

    public MenuServicioImp(GenericoDao<Menu, Integer> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public List<Menu> listarPorTipoDeUsuario(Long tipousuario) throws GeneralException {
        List<Menu> menus = new ArrayList<>();
        Criterio filtro;
        filtro = Criterio.forClass(Menutipousuario.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        filtro.createAlias("menu", "menu");
        filtro.add(Restrictions.eq("menu.estado", Boolean.TRUE));
        filtro.add(Restrictions.eq("menutipousuarioPK.idtipousuario", tipousuario));
        List<Menutipousuario> menustipos = menuTipousuarioDao.buscarPorCriteriaSinProyecciones(filtro);
        for (int i = 0; i < menustipos.size(); i++) {
            //menus.add(menustipos.get(i).getMenu());
        }
        return menus;
    }
    
}
