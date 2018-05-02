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
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "predioservicio")
@NamedQueries({
    @NamedQuery(name = "Predioservicio.findAll", query = "SELECT p FROM Predioservicio p")})
public class Predioservicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PredioservicioPK predioservicioPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "idpredio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Predio predio;
    @JoinColumn(name = "idservicio", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servicios servicios;

    public Predioservicio() {
    }

    public Predioservicio(PredioservicioPK predioservicioPK) {
        this.predioservicioPK = predioservicioPK;
    }

    public Predioservicio(PredioservicioPK predioservicioPK, boolean estado) {
        this.predioservicioPK = predioservicioPK;
        this.estado = estado;
    }

    public Predioservicio(long idpredio, long idservicio) {
        this.predioservicioPK = new PredioservicioPK(idpredio, idservicio);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (predioservicioPK != null ? predioservicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Predioservicio)) {
            return false;
        }
        Predioservicio other = (Predioservicio) object;
        return !((this.predioservicioPK == null && other.predioservicioPK != null) || (this.predioservicioPK != null && !this.predioservicioPK.equals(other.predioservicioPK)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Predioservicio[ predioservicioPK=" + predioservicioPK + " ]";
    }
    
}
