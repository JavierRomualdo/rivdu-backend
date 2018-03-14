/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.entidades.Materiales;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.MaterialesServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Christhian
 */
@RestController
@RequestMapping("materiales")
public class MaterialesControlador {
    private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
   
   @Autowired
   private MaterialesServicio materialesServicio;
   
   //@GetMapping ("listar")
    @RequestMapping(value = "pagina/{pagina}/cantidadPorPagina/{cantidadPorPagina}", method = RequestMethod.POST)
    public ResponseEntity<BusquedaPaginada> busquedaPaginada(HttpServletRequest request, @PathVariable("pagina") Long pagina, 
                                                             @PathVariable("cantidadPorPagina") Long cantidadPorPagina, 
                                                             @RequestBody Map<String, Object> parametros) throws GeneralException{
    //public ResponseEntity show() throws GeneralException{
//        Respuesta resp = new Respuesta();
//        List<Materiales> lista;
//        try {
//          lista = materialesServicio.listar();
//            if (lista!=null) {
//                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
//                resp.setOperacionMensaje("");
//                resp.setExtraInfo(lista);
//            }else{
//                throw new GeneralException("Lista no disponible", "No hay datos", loggerControlador);
//            }
//            return new ResponseEntity<>(resp, HttpStatus.OK);
//        } catch (Exception e) {
//            loggerControlador.error(e.getMessage());
//            throw e;
//        }
//    }
try {
            
            String detalle;
            BusquedaPaginada busquedaPaginada = new BusquedaPaginada();
            busquedaPaginada.setBuscar(parametros);
            Materiales entidadBuscar = new Materiales();
            detalle = busquedaPaginada.obtenerFiltroComoString("detalle");
            busquedaPaginada.setPaginaActual(pagina);
            busquedaPaginada.setCantidadPorPagina(cantidadPorPagina);
            busquedaPaginada = materialesServicio.busquedaPaginada(entidadBuscar, busquedaPaginada, detalle);
            return new ResponseEntity<>(busquedaPaginada, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity crear(HttpServletRequest request, @RequestBody Materiales entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if(entidad != null){
            try {
                Materiales guardado = materialesServicio.crear(entidad);
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

    @RequestMapping(value = "eliminarEstadoMaterial/{id}", method = RequestMethod.DELETE)
    public ResponseEntity eliminarestado(HttpServletRequest request, @PathVariable("id") Long id) throws GeneralException {
        
        Respuesta resp = new Respuesta();
            materialesServicio.actualizarMateriales(id);
            resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
            resp.setExtraInfo(id);
            return new ResponseEntity<>(resp, HttpStatus.OK);
    }    
    
    
    
}
