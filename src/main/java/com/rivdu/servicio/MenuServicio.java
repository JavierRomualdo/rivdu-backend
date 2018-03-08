/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import java.util.List;
import com.rivdu.entidades.Menu;
import com.rivdu.excepcion.GeneralException;

/**
 *
 * @author dev-out-03
 */
public interface MenuServicio extends GenericoServicio<Menu, Long>{
    public List<Menu> listarMenus(String login) throws GeneralException;
}
