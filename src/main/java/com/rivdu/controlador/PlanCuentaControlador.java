
package com.rivdu.controlador;

import com.rivdu.entidades.Plandecuentas;
import com.rivdu.servicio.PlanCuentaServicio;
import com.rivdu.util.Respuesta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PCQUISPE
 */

@RestController
@RequestMapping("plancuenta")
public class PlanCuentaControlador {
    
     private final Logger loggerControlador = LoggerFactory.getLogger(getClass());

    @Autowired
    private PlanCuentaServicio plancuentaservicio;
    
}

//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity crear(HttpServletRequest request, @RequestBody Plandecuentas entidad){
//        Respuesta resp = new Respuesta();
//       
//    }
