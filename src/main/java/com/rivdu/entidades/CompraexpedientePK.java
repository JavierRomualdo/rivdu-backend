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
public class CompraexpedientePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcompra")
    private long idcompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idexpediente")
    private long idexpediente;

    public CompraexpedientePK() {
    }

    public CompraexpedientePK(long idcompra, long idexpediente) {
        this.idcompra = idcompra;
        this.idexpediente = idexpediente;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcompra;
        hash += (int) idexpediente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CompraexpedientePK)) {
            return false;
        }
        CompraexpedientePK other = (CompraexpedientePK) object;
        if (this.idcompra != other.idcompra) {
            return false;
        }
        return this.idexpediente == other.idexpediente;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.CompraexpedientePK[ idcompra=" + idcompra + ", idexpediente=" + idexpediente + " ]";
    }
    
}
