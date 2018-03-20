/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author Christhian
 */
@Data
@Entity
@Table(name = "labores")
public class Labores implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 300)
    @Column(name = "detalle")
    private String detalle;
    @Column(name = "costo")
    private long costo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;

     public Labores() {
    }

    public Labores(Long id) {
        this.id = id;
    }

    public Labores(Long id,
            String detalleLabor, long costoLabor, boolean estadoLabor) {
        
        this.id = id;
        this.detalle = detalleLabor;
        this.costo = costoLabor;
        this.estado = estadoLabor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Labores)) {
            return false;
        }
        Labores other = (Labores) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Labores[ id=" + id + " ]";
    }
    
}
