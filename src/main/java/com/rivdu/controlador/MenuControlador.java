package com.rivdu.controlador;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.rivdu.entidades.Empresa;
import com.rivdu.entidades.Menu;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.MenuServicio;
import com.rivdu.util.Respuesta;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("menu")
public class MenuControlador {
    
    private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
    @Autowired
    MenuServicio menuServicio;

    @GetMapping("/traer/{login}")
    public ResponseEntity taerMenus(@PathVariable String login) throws GeneralException{
        Respuesta resp = new Respuesta();
        try {
            List<Menu> lm = menuServicio.listarMenus(login);
            if (lm!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(lm);
            }else{
                throw new GeneralException("Usuario No tiene menu asignado", "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }

}
