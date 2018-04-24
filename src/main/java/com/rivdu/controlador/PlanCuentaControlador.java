package com.rivdu.controlador;

import com.rivdu.entidades.Cuentabanco;
import com.rivdu.entidades.Plandecuentas;
import com.rivdu.entidades.Ubigeo;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.PlanCuentaServicio;
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
 * @author PCQUISPE
 */
@RestController
@RequestMapping("plancuenta")
public class PlanCuentaControlador {

    private final Logger loggerControlador = LoggerFactory.getLogger(getClass());

    @Autowired
    private PlanCuentaServicio plancuentaservicio;

    //metedo padre
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity crear(HttpServletRequest request, @RequestBody Plandecuentas entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                Plandecuentas guardado;
                if (entidad.getId() != null) {
                    guardado = plancuentaservicio.actualizar(entidad);
                } else {
                    guardado = plancuentaservicio.crear(entidad);
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

    //metodo listar busqueda
    @RequestMapping(value = "pagina/{pagina}/cantidadPorPagina/{cantidadPorPagina}", method = RequestMethod.POST)
    public ResponseEntity<BusquedaPaginada> busquedaPaginada(HttpServletRequest request, @PathVariable("pagina") Long pagina,
            @PathVariable("cantidadPorPagina") Long cantidadPorPagina,
            @RequestBody Map<String, Object> parametros) throws GeneralException {
        try {
            String codigo;
            BusquedaPaginada busquedaPaginada = new BusquedaPaginada();
            busquedaPaginada.setBuscar(parametros);
            Plandecuentas entidadBuscar = new Plandecuentas();
            codigo = busquedaPaginada.obtenerFiltroComoString("codigo");
            busquedaPaginada.setPaginaActual(pagina);
            busquedaPaginada.setCantidadPorPagina(cantidadPorPagina);
            busquedaPaginada = plancuentaservicio.busquedaPaginada(entidadBuscar, busquedaPaginada, codigo);
            return new ResponseEntity<>(busquedaPaginada, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
}

//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity crear(HttpServletRequest request, @RequestBody Plandecuentas entidad){
//        Respuesta resp = new Respuesta();
//       
//    }

