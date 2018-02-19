package com.rivdu.controlador;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import com.rivdu.dao.UsuarioDao;
import com.rivdu.dto.LoginDTO;
import com.rivdu.entidades.Usuario;
import com.rivdu.dto.SessionItemDTO;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.util.SessionResponse;
import com.rivdu.util.Mensaje;
import com.rivdu.util.Respuesta.EstadoOperacionEnum;

@RestController
public class MenuControlador {
    
    @Autowired
    private UsuarioDao usuarioDao;

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public ResponseEntity traerMenu(@RequestBody LoginDTO login, HttpServletRequest request) throws GeneralException {
        Usuario usuario = usuarioDao.findOneByUserIdAndPassword(login.getUsername(), login.getPassword()).orElse(null);
        SessionResponse resp = new SessionResponse();
        SessionItemDTO sessionItem = new SessionItemDTO();
        if (usuario!=null) {
            sessionItem.setToken("xxx.xxx.xxx");
            sessionItem.setUsuarioId(usuario.getUserId());
            sessionItem.setNombre(usuario.getNombre());
            resp.setEstadoOperacion(EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje(Mensaje.OPERACION_CORRECTA);
            resp.setItem(sessionItem);
        }
        else{
            throw new BadCredentialsException("Usuario o Contraseña Incorreta");
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
