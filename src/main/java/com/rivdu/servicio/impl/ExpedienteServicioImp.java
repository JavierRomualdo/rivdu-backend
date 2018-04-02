/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Compraexpediente;
import com.rivdu.entidades.CompraexpedientePK;
import com.rivdu.entidades.Expediente;
import com.rivdu.entidades.Tipoexpediente;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.ExpedienteServicio;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author PROPIETARIO
 */
@Service
@Transactional
public class ExpedienteServicioImp extends GenericoServicioImpl<Expediente, Long> implements ExpedienteServicio {
    
    private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Expediente, Long> expedienteDao;
    @Autowired
    private GenericoDao<Compraexpediente, CompraexpedientePK> compraexpedienteDao;
 
    public ExpedienteServicioImp(GenericoDao<Expediente, Long> genericoHibernate) {
        super(genericoHibernate);
    }

    @Override
    public void insertar(MultipartFile[] files, Long idcompra, Long idtipoExpediente) throws GeneralException, IOException {
        for (int i = 0; i < files.length; i++) {
            Expediente e = new Expediente();
            Tipoexpediente tp = new Tipoexpediente(idtipoExpediente);
            String extension = FilenameUtils.getExtension(files[i].getOriginalFilename());
            e.setEstado(Boolean.TRUE);
            e.setFile(files[i].getBytes());
            e.setIdtipoexpediente(tp);
            e.setNombre(files[i].getOriginalFilename());
            e.setTipofile(extension);
            e.setContenttype(files[i].getContentType());
            e = expedienteDao.insertar(e);
            if(e!=null && e.getId()>0){
                Compraexpediente cp = new Compraexpediente(idcompra, e.getId());
                cp.setEstado(Boolean.TRUE);
                compraexpedienteDao.insertar(cp);
            }
        }
    }

    @Override
    public String descargar(HttpServletResponse response, Long id) throws GeneralException{
        try{
            Expediente e = expedienteDao.obtener(Expediente.class, id);
            if(e!=null && e.getFile()!=null){
                response.setHeader("Content-disposition", "inline; filename=" + e.getNombre());
                response.setHeader("Cache-Control", "max-age=30");
                response.setContentType(e.getContenttype());
                response.setHeader("Pragma", "No-cache");
                response.setDateHeader("Expires", 0);
                response.setContentLength(e.getFile().length);
                ServletOutputStream out = response.getOutputStream();
                out.write(e.getFile(), 0, e.getFile().length);
                out.flush();
                out.close();
            }
            return null;
        }catch (Exception e) {
            return null;
        }
    }
}
