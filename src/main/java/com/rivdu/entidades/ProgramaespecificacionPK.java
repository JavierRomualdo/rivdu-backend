/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author PROPIETARIO
 */
@Embeddable
public class ProgramaespecificacionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idprograma")
    private long idprograma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idespecificacion")
    private long idespecificacion;

    public ProgramaespecificacionPK() {
    }

    public ProgramaespecificacionPK(long idprograma, long idespecificacion) {
        this.idprograma = idprograma;
        this.idespecificacion = idespecificacion;
    }

    public long getIdprograma() {
        return idprograma;
    }

    public void setIdprograma(long idprograma) {
        this.idprograma = idprograma;
    }

    public long getIdespecificacion() {
        return idespecificacion;
    }

    public void setIdespecificacion(long idespecificacion) {
        this.idespecificacion = idespecificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idprograma;
        hash += (int) idespecificacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramaespecificacionPK)) {
            return false;
        }
        ProgramaespecificacionPK other = (ProgramaespecificacionPK) object;
        if (this.idprograma != other.idprograma) {
            return false;
        }
        if (this.idespecificacion != other.idespecificacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.ProgramaespecificacionPK[ idprograma=" + idprograma + ", idespecificacion=" + idespecificacion + " ]";
    }
    
}
