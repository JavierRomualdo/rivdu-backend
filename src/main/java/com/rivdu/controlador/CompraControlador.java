/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.dto.CompraDTO;
import com.rivdu.dto.SaveCompraDTO;
import com.rivdu.entidades.Compra;
import com.rivdu.entidades.Persona;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CompraServicio;
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
@RequestMapping("/compra")
public class CompraControlador {

    private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
    @Autowired
    private CompraServicio compraservicio;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity crear(HttpServletRequest request, @RequestBody SaveCompraDTO entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                long idCompra = compraservicio.insertar(entidad);
                if (idCompra > 0) {
                    resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                    resp.setExtraInfo(idCompra);
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
    public ResponseEntity guardar(HttpServletRequest request, @RequestBody SaveCompraDTO entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                Long id = compraservicio.insertar(entidad);
                if (id > 0) {
                    resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                    resp.setExtraInfo(id);
                }
            } catch (Exception e) {
                throw new GeneralException(Mensaje.ERROR_CRUD_GUARDAR, "Guardar retorno nulo", loggerControlador);
            }
        } else {
            resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.ERROR.getValor());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public ResponseEntity listar(HttpServletRequest request) throws GeneralException {
        Respuesta rsp = new Respuesta();
        List<CompraDTO> lista;
        try {
            lista = compraservicio.listar();
            if (lista != null) {
                rsp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                rsp.setOperacionMensaje(" ");
                rsp.setExtraInfo(lista);
            } else {
                throw new GeneralException("Lista no disponible", "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(rsp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    };

    @RequestMapping(value = "pagina/{pagina}/cantidadPorPagina/{cantidadPorPagina}", method = RequestMethod.POST)
    public ResponseEntity<BusquedaPaginada> busquedaPaginada(HttpServletRequest request, @PathVariable("pagina") Long pagina, 
                                                             @PathVariable("cantidadPorPagina") Long cantidadPorPagina, 
                                                             @RequestBody Map<String, Object> parametros) throws GeneralException{
        try {
            String propietario;
            String correlativo;
            BusquedaPaginada busquedaPaginada = new BusquedaPaginada();
            busquedaPaginada.setBuscar(parametros);
            Persona entidadBuscar = new Persona();
            propietario = busquedaPaginada.obtenerFiltroComoString("propietario");
            correlativo = busquedaPaginada.obtenerFiltroComoString("correlativo");
            busquedaPaginada.setPaginaActual(pagina);
            busquedaPaginada.setCantidadPorPagina(cantidadPorPagina);
            busquedaPaginada = compraservicio.busquedaPaginada(entidadBuscar, busquedaPaginada, propietario, correlativo);
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
            Compra compra = compraservicio.obtener(id);
            if (compra!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(compra);
            }else{
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    };
}
