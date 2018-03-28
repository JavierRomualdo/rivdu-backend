/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.entidades.Responsable;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rivdu.servicio.ResponsableServicio;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author PROPIETARIO
 */
@RestController
@RequestMapping("responsable")


public class ResponsableControlador {
    
     @Autowired
    private ResponsableServicio responsableservicio;
    
     @RequestMapping(value = "eliminarresponsable/{id}", method = RequestMethod.DELETE)
    public ResponseEntity eliminarresponsable(HttpServletRequest request, @PathVariable("id") Long id) throws GeneralException {
         Respuesta resp = new Respuesta();
         Responsable responsable=responsableservicio.obtener(Responsable.class, id);
        if (responsable.isEstado()) {
            responsable.setEstado(Boolean.FALSE);
        }else{
            responsable.setEstado(Boolean.TRUE);
        }
        responsableservicio.actualizar(responsable); 
        resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
        resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
        resp.setExtraInfo(id);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
}
