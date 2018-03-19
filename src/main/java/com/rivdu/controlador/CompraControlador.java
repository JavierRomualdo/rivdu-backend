/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.dto.SaveCompraDTO;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.CompraServicio;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if(entidad != null){
            try {
                long idCompra = compraservicio.insertar(entidad);
                if(idCompra > 0){
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
    
}
