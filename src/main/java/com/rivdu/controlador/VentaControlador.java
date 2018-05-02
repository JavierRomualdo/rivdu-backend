/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.dao.GenericoDao;
import com.rivdu.dto.ExpedientesDTO;
import com.rivdu.dto.SaveVentaDTO;
import com.rivdu.entidades.Venta;
import com.rivdu.entidades.Personaventa;
import com.rivdu.entidades.Venta;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.VentaServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta;
import com.rivdu.util.RivduUtil;
import java.util.List;
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
 * @author javie
 */
@RestController
@RequestMapping("/venta")
public class VentaControlador {

    private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
    @Autowired
    private VentaServicio ventaservicio;
    
    @Autowired
    private GenericoDao<Venta,Long> ventaDao;
    @Autowired
    private GenericoDao<Personaventa,Long> personaventaDao;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity crear(HttpServletRequest request, @RequestBody SaveVentaDTO entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                long idVenta = ventaservicio.insertar(entidad);
                if (idVenta > 0) {
                    resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                    resp.setExtraInfo(idVenta);
                } else {
                    throw new GeneralException(Mensaje.ERROR_CRUD_GUARDAR, "Guardar retorno nulo", loggerControlador);
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "guardar", method = RequestMethod.POST)
    public ResponseEntity guardar(HttpServletRequest request, @RequestBody SaveVentaDTO entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                Long id = ventaservicio.insertar(entidad);
                if (id > 0) {
                    resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                    resp.setExtraInfo(id);
                }
            } catch (Exception e) {
                throw e;
            }
        } else {
            resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.ERROR.getValor());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    };
    
    @RequestMapping(value = "actualizar", method = RequestMethod.PUT)
    public ResponseEntity actualizar(HttpServletRequest request, @RequestBody SaveVentaDTO entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                Venta guardado = ventaservicio.actualizar(entidad);
                if (guardado != null) {
                    ventaservicio.guardarPredioServicio(entidad.getServicios(), entidad.getPredio());
                    resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                    resp.setExtraInfo(guardado);
                } else {
                    throw new GeneralException(Mensaje.ERROR_CRUD_GUARDAR, "Guardar retorno nulo", loggerControlador);
                }
            } catch (Exception e) {
                throw e;
            }
        } else {
            resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.ERROR.getValor());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public ResponseEntity eliminar(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            Venta venta = ventaDao.obtener(Venta.class, id);
            if(venta.isEstado()){
            venta.setEstado(Boolean.FALSE);
            }
            else{
            venta.setEstado(Boolean.TRUE);
            }
            venta = ventaDao.actualizar(venta);
            if (venta.getId() != null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(venta.getId());
            } else {
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }//fin del metodo eliminar

    @RequestMapping(value = "quitarallegado/{id}", method = RequestMethod.GET)
    public ResponseEntity quitarallegado(HttpServletRequest request, @PathVariable("id") Long id) throws GeneralException {
        Respuesta resp = new Respuesta();
        try {
            Personaventa pc = personaventaDao.obtener(Personaventa.class, id);
            personaventaDao.eliminar(pc);
            if (pc.getId() != null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(pc.getId());
            } else {
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }//fin del metodo eliminar

    
    @RequestMapping(value = "pagina/{pagina}/cantidadPorPagina/{cantidadPorPagina}", method = RequestMethod.POST)
    public ResponseEntity<BusquedaPaginada> busquedaPaginada(HttpServletRequest request, @PathVariable("pagina") Long pagina, 
                                                             @PathVariable("cantidadPorPagina") Long cantidadPorPagina, 
                                                             @RequestBody Map<String, Object> parametros) throws GeneralException{
        try {
            String clientenombre,clientedoc,correlativo;
            BusquedaPaginada busquedaPaginada = new BusquedaPaginada();
            busquedaPaginada.setBuscar(parametros);
            Venta entidadBuscar = new Venta();
            clientenombre = busquedaPaginada.obtenerFiltroComoString("nombre");
            clientedoc = busquedaPaginada.obtenerFiltroComoString("dni");
            correlativo = busquedaPaginada.obtenerFiltroComoString("correlativo");
            busquedaPaginada.setPaginaActual(pagina);
            busquedaPaginada.setCantidadPorPagina(cantidadPorPagina);
            busquedaPaginada = ventaservicio.busquedaPaginada(entidadBuscar, busquedaPaginada, clientenombre,clientedoc,correlativo);
            return new ResponseEntity<>(busquedaPaginada, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "obtener", method = RequestMethod.POST)
    public ResponseEntity Obtener(HttpServletRequest request,@RequestBody Map<String, Object> parametros) throws GeneralException {
      Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            SaveVentaDTO venta = ventaservicio.obtener(id);
            if (venta!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(venta);
            }else{
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
    
}
