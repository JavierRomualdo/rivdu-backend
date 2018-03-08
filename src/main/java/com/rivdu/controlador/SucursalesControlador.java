/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Sucursal;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.SucursalServicio;
import com.rivdu.util.BusquedaPaginada;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LUIS ORTIZ
 */
@RestController
@RequestMapping("/sucursal")
public class SucursalesControlador {
        private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
    @Autowired
    private SucursalServicio sucursalServicio;
    @Autowired
    private GenericoDao<Sucursal, Long> sucursaldao;
    
        @RequestMapping(value = "pagina/{pagina}/cantidadPorPagina/{cantidadPorPagina}", method = RequestMethod.POST)
    public ResponseEntity<BusquedaPaginada> busquedaPaginada(HttpServletRequest request, @PathVariable("pagina") Long pagina, 
                                                             @PathVariable("cantidadPorPagina") Long cantidadPorPagina, 
                                                             @RequestBody Map<String, Object> parametros) throws GeneralException{
        try {
            String ruc;
            String nombre;
            BusquedaPaginada busquedaPaginada = new BusquedaPaginada();
            busquedaPaginada.setBuscar(parametros);
            Sucursal entidadBuscar = new Sucursal();
            nombre = busquedaPaginada.obtenerFiltroComoString("nombre");
            ruc = busquedaPaginada.obtenerFiltroComoString("ruc");
            busquedaPaginada.setPaginaActual(pagina);
            busquedaPaginada.setCantidadPorPagina(cantidadPorPagina);
            busquedaPaginada = sucursalServicio.busquedaPaginada(entidadBuscar, busquedaPaginada, ruc, nombre);
            return new ResponseEntity<>(busquedaPaginada, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
}
