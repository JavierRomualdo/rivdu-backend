/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.entidades.Ubigeo;
import com.rivdu.entidades.Tipoubigeo;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.UbigeoServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta;
import com.rivdu.util.RivduUtil;
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
 * @author PROPIETARIO
 */
@RestController
@RequestMapping("/ubigeo")
public class UbigeoControlador {
     Tipoubigeo ubi;
     private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
    @Autowired
    private UbigeoServicio ubigeoServicio;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity crear(HttpServletRequest request, @RequestBody Ubigeo entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if(entidad != null){
            try {
                Ubigeo guardado;
                if(entidad.getId()!=null){
                    guardado = ubigeoServicio.actualizar(entidad);
                } else {
                    guardado = ubigeoServicio.crear(entidad);
                }
                if (guardado != null ) {
                    resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                    resp.setExtraInfo(guardado);
                }else{
                    throw new GeneralException(Mensaje.ERROR_CRUD_GUARDAR, "Guardar retorno nulo", loggerControlador);
                }
            } catch (Exception e) {
                throw e;
            }
        }else{
            resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.ERROR.getValor());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    //el otro método listar ubigeo
        @RequestMapping(value = "pagina/{pagina}/cantidadPorPagina/{cantidadPorPagina}", method = RequestMethod.POST)
    public ResponseEntity<BusquedaPaginada> busquedaPaginada(HttpServletRequest request, @PathVariable("pagina") Long pagina, 
                                                             @PathVariable("cantidadPorPagina") Long cantidadPorPagina, 
                                                             @RequestBody Map<String, Object> parametros) throws GeneralException{
        try {
            String nombre,codigo;
            BusquedaPaginada busquedaPaginada = new BusquedaPaginada();
            busquedaPaginada.setBuscar(parametros);
            Ubigeo entidadBuscar = new Ubigeo();
            nombre = busquedaPaginada.obtenerFiltroComoString("nombre");
            codigo = busquedaPaginada.obtenerFiltroComoString("codigo");
            busquedaPaginada.setPaginaActual(pagina);
            busquedaPaginada.setCantidadPorPagina(cantidadPorPagina);
            busquedaPaginada = ubigeoServicio.busquedaPaginada(entidadBuscar, busquedaPaginada, nombre,codigo);
            return new ResponseEntity<>(busquedaPaginada, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
    
    @RequestMapping(value="eliminar", method = RequestMethod.POST)
    public ResponseEntity eliminar(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException{
        Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            Ubigeo ubigeo = ubigeoServicio.obtener(id);
            if(ubigeo.isEstado()){
            ubigeo.setEstado(Boolean.FALSE);
            }
            else{
            ubigeo.setEstado(Boolean.TRUE);
            }
            ubigeo = ubigeoServicio.actualizar(ubigeo);
            if (ubigeo.getId()!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(ubigeo);
            }else{
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }//fin del metodo eliminar
    
        @RequestMapping(value="obtener", method = RequestMethod.POST)//metódo para editar
    public ResponseEntity obtener(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException{
        Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            Ubigeo ubigeo = ubigeoServicio.obtener(id);
            if (ubigeo!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(ubigeo);
            }else{
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }//Fin del Metodo obtener:traerparaedicion
}
