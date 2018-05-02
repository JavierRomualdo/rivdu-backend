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
import lombok.Data;

/**
 *
 * @author MarioMario
 */
@Data
@Embeddable
public class PredioservicioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpredio")
    private long idpredio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idservicio")
    private long idservicio;

    public PredioservicioPK() {
    }

    public PredioservicioPK(long idpredio, long idservicio) {
        this.idpredio = idpredio;
        this.idservicio = idservicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idpredio;
        hash += (int) idservicio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PredioservicioPK)) {
            return false;
        }
        PredioservicioPK other = (PredioservicioPK) object;
        if (this.idpredio != other.idpredio) {
            return false;
        }
        return this.idservicio == other.idservicio;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.PredioservicioPK[ idpredio=" + idpredio + ", idservicio=" + idservicio + " ]";
    }
    
}
