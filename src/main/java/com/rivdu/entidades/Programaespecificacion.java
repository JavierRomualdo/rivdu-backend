/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author PROPIETARIO
 */
@Data
@Entity
@Table(name = "programaespecificacion")
@NamedQueries({
    @NamedQuery(name = "Programaespecificacion.findAll", query = "SELECT p FROM Programaespecificacion p")})
public class Programaespecificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProgramaespecificacionPK programaespecificacionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "idespecificacion", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Especificaciones idespecificacion;
    @Column(name = "idprograma", insertable = false, updatable = false)
    private Long idprograma;

    public Programaespecificacion() {
    }

    public Programaespecificacion(ProgramaespecificacionPK programaespecificacionPK) {
        this.programaespecificacionPK = programaespecificacionPK;
    }

    public Programaespecificacion(ProgramaespecificacionPK programaespecificacionPK, boolean estado) {
        this.programaespecificacionPK = programaespecificacionPK;
        this.estado = estado;
    }

    public Programaespecificacion(long idprograma, long idespecificacion) {
        this.programaespecificacionPK = new ProgramaespecificacionPK(idprograma, idespecificacion);
    }

    public ProgramaespecificacionPK getProgramaespecificacionPK() {
        return programaespecificacionPK;
    }

    public void setProgramaespecificacionPK(ProgramaespecificacionPK programaespecificacionPK) {
        this.programaespecificacionPK = programaespecificacionPK;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Especificaciones getEspecificaciones() {
        return idespecificacion;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programaespecificacionPK != null ? programaespecificacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programaespecificacion)) {
            return false;
        }
        Programaespecificacion other = (Programaespecificacion) object;
        if ((this.programaespecificacionPK == null && other.programaespecificacionPK != null) || (this.programaespecificacionPK != null && !this.programaespecificacionPK.equals(other.programaespecificacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Programaespecificacion[ programaespecificacionPK=" + programaespecificacionPK + " ]";
    }
    
}
