/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.controlador;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rivdu.entidades.Usuario;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.UsuarioServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.RivduUtil;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta;
import org.springframework.web.bind.annotation.GetMapping;
/**
 *
 * @author dev-out-03
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final Logger loggerControlador = LoggerFactory.getLogger(getClass());
    @Autowired
    private UsuarioServicio usuarioServicio;

    @RequestMapping(value = "pagina/{pagina}/cantidadPorPagina/{cantidadPorPagina}", method = RequestMethod.POST)
    public ResponseEntity<BusquedaPaginada> busquedaPaginada(HttpServletRequest request, @PathVariable("pagina") Long pagina,
            @PathVariable("cantidadPorPagina") Long cantidadPorPagina,
            @RequestBody Map<String, Object> parametros) throws GeneralException {
        try {
            String dni, nomusu;
            BusquedaPaginada busquedaPaginada = new BusquedaPaginada();
            busquedaPaginada.setBuscar(parametros);
            Usuario entidadBuscar = new Usuario();
            dni = busquedaPaginada.obtenerFiltroComoString("dni");
            nomusu = busquedaPaginada.obtenerFiltroComoString("nomusu");
            busquedaPaginada.setPaginaActual(pagina);
            busquedaPaginada.setCantidadPorPagina(cantidadPorPagina);
            busquedaPaginada = usuarioServicio.busquedaPaginada(entidadBuscar, busquedaPaginada, dni, nomusu);
            return new ResponseEntity<>(busquedaPaginada, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "obtener", method = RequestMethod.POST)
    public ResponseEntity obtener(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            Usuario usuario = usuarioServicio.obtener(id);
            if (usuario!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(usuario);
            } else {
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity crear(HttpServletRequest request, @RequestBody Usuario entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                Usuario guardado = usuarioServicio.insertar(entidad);
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

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity actualizar(HttpServletRequest request, @RequestBody Usuario entidad) throws GeneralException {
        Respuesta resp = new Respuesta();
        if (entidad != null) {
            try {
                Usuario guardado = usuarioServicio.actualizar(entidad);
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

    @RequestMapping(value = "eliminar", method = RequestMethod.POST)
    public ResponseEntity eliminar(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        Respuesta resp = new Respuesta();
        try {
            Long id = RivduUtil.obtenerFiltroComoLong(parametros, "id");
            Usuario unidad = usuarioServicio.obtener(Usuario.class, id);
            if(unidad.getEstado()== true){
            unidad.setEstado(Boolean.FALSE);
            }
            else{
            unidad.setEstado(Boolean.TRUE);
            }
            unidad = usuarioServicio.actualizar(unidad);
            if (unidad.getId() != null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
                resp.setExtraInfo(unidad.getId());
            }else{
                throw new GeneralException(Mensaje.ERROR_CRUD_LISTAR, "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }
    
    @RequestMapping(value="validarDni", method = RequestMethod.POST)
    public ResponseEntity show(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException{
        Respuesta resp = new Respuesta();
        try {
            String dni = RivduUtil.obtenerFiltroComoString(parametros, "dni");
            Usuario e = usuarioServicio.validarDni(dni);
            if (e!=null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(e);
            }else{
                throw new GeneralException("La Persona no se encuentra Registrada en el Sistema", "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping("show/{username}")
    public ResponseEntity show(@PathVariable String username) throws GeneralException {
        Respuesta resp = new Respuesta();
        try {
            Usuario u = usuarioServicio.show(username);
            if (u != null) {
                resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(u);
            } else {
                throw new GeneralException("Usuario no registrado", "No hay datos", loggerControlador);
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping("validarPassword/{username}/{passwordTipeada}")
    public ResponseEntity validarPassword(@PathVariable String username, @PathVariable String passwordTipeada) throws GeneralException {
        Respuesta resp = new Respuesta();
        try {
            resp.setEstadoOperacion(Respuesta.EstadoOperacionEnum.EXITO.getValor());
            if (usuarioServicio.validarNuevaPassword(username, passwordTipeada)) {
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("");
            } else {
                resp.setExtraInfo(false);
                resp.setOperacionMensaje("La contraseña ingresada no coincide con la actual");
            }
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            loggerControlador.error(e.getMessage());
            throw e;
        }
    }

}
