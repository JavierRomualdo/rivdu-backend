package com.rivdu.controllers;

import com.rivdu.entidades.Persona;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import com.rivdu.servicios.IClienteServicio;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    public IClienteServicio clienteService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listar(HttpServletRequest request) throws GeneralException{
        Respuesta resp = new Respuesta();
        try {
            List<Persona> personas = clienteService.findAll();
            if (!personas.isEmpty()) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(personas);
            }else{
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", null);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (GeneralException e) {
            throw e;
        }
    }
    
}
