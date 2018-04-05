/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio;

import com.rivdu.entidades.Expediente;
import com.rivdu.excepcion.GeneralException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author PROPIETARIO
 */
public interface ExpedienteServicio extends GenericoServicio<Expediente, Long>{
    public void insertar(MultipartFile[] files, Long idcompra, Long idtipoExpediente) throws GeneralException, IOException;
    public String descargar(HttpServletResponse response, Long id) throws GeneralException;
}
