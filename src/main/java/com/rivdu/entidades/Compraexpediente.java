/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "compraexpediente")
@NamedQueries({
    @NamedQuery(name = "Compraexpediente.findAll", query = "SELECT c FROM Compraexpediente c")})
public class Compraexpediente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompraexpedientePK compraexpedientePK;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "idcompra", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Compra compra;
    @JoinColumn(name = "idexpediente", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Expediente expediente;

    public Compraexpediente() {
    }

    public Compraexpediente(CompraexpedientePK compraexpedientePK) {
        this.compraexpedientePK = compraexpedientePK;
    }

    public Compraexpediente(long idcompra, long idexpediente) {
        this.compraexpedientePK = new CompraexpedientePK(idcompra, idexpediente);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (compraexpedientePK != null ? compraexpedientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Compraexpediente)) {
            return false;
        }
        Compraexpediente other = (Compraexpediente) object;
        return !((this.compraexpedientePK == null && other.compraexpedientePK != null) || (this.compraexpedientePK != null && !this.compraexpedientePK.equals(other.compraexpedientePK)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Compraexpediente[ compraexpedientePK=" + compraexpedientePK + " ]";
    }
    
}
