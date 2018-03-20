/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.entidades.Centrocostos;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CentroCostoServicio;
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
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("centrocosto")
public class CentroCostosControlador {
    
      private final Logger loggerControlador = LoggerFactory.getLogger(getClass());

    @Autowired
    private CentroCostoServicio centrocostoservicio;
    
    @GetMapping("listar")
    public ResponseEntity show() throws GeneralException {
        Respuesta resp = new Respuesta();
        List<Centrocostos> lista;
        try {
            lista = centrocostoservicio.listar();
            if (lista != null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(lista);
            } else {
                throw new GeneralException("Lista no disponible", "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    };
    
        @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity crear(HttpServletRequest request, @RequestBody Centrocostos entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                Centrocostos guardado;
                if (entidad.getId() != null) {
                    guardado = centrocostoservicio.actualizar(entidad);
                } else {
                    guardado = centrocostoservicio.crear(entidad);
                }
                if (guardado != null) {
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
    
        @RequestMapping(value = "eliminarestadocosto/{id}", method = RequestMethod.GET)
    public ResponseEntity eliminarestado(HttpServletRequest request, @PathVariable("id") Long id) throws GeneralException {
        Respuesta resp = new Respuesta();
        Centrocostos estadocliente=centrocostoservicio.obtener(Centrocostos.class,id);
        if (estadocliente.isEstado()) {
            estadocliente.setEstado(Boolean.FALSE);
        }else{
            estadocliente.setEstado(Boolean.TRUE);
        }
        centrocostoservicio.actualizar(estadocliente); 
        resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
        resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
        resp.setExtraInfo(id);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

        @RequestMapping(value = "obtener", method = RequestMethod.POST)
    public ResponseEntity obtener(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            Centrocostos unidad = centrocostoservicio.obtener(Centrocostos.class, id);
            if (unidad != null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(unidad);
            } else {
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }

}
