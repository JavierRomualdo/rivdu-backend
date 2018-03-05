/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rivdu.servicio.UbigeoTipoServicio;
import com.rivdu.entidades.Tipoubigeo;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.Respuesta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author PROPIETARIO
 */

@RestController
@RequestMapping("tipoubigeo")
public class TipoUbigeoControlador {
    
   private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
   @Autowired
   private UbigeoTipoServicio tipoubigeoservicio;

    @GetMapping ("listar")
    public ResponseEntity show() throws GeneralException{
        Respuesta resp = new Respuesta();
        List<Tipoubigeo> lista;
        try {
          lista = tipoubigeoservicio.listar();
            if (lista!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(lista);
            }else{
                throw new GeneralException("Lista no disponible", "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
    
}
