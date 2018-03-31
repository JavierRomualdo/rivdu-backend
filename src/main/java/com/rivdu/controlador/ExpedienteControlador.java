/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import com.rivdu.entidades.Expediente;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ExpedienteServicio;
import com.rivdu.util.Respuesta;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author PROPIETARIO
 */
@RestController
@RequestMapping("expediente")
public class ExpedienteControlador {
    
    private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
    @Autowired
    private ExpedienteServicio expedienteServicio;
    
    @RequestMapping(value = "/subir", method = RequestMethod.POST)
    public ResponseEntity subir(HttpServletRequest request,
            @RequestParam("files[]") MultipartFile[] files, 
            @RequestParam(name = "idoperacion", required = false) Long idoperacion,
            @RequestParam(name = "idtipoexpediente", required = false) Long idtipoExpediente) throws GeneralException, IOException{
        Respuesta rsp = new Respuesta();
        if(files != null && files.length>0){
            expedienteServicio.insertar(files, idoperacion, idtipoExpediente);
            rsp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
            rsp.setOperacionMensaje("Operacion Exitosa");
            rsp.setExtraInfo(idoperacion);
        }else{
            throw new GeneralException("Lista no disponible", "No hay datos", loggerControlador);
        }
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }
    
    @GetMapping("/descargar/{id}")
    @ResponseBody
    public String descargar(HttpServletResponse response, @PathVariable Long id) throws ParseException, GeneralException, Exception{
        try {
            if(id>0){
                return expedienteServicio.descargar(response, id);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
}
