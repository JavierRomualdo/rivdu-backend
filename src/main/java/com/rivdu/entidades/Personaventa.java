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
import javax.persistence.Id;
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
@Table(name = "personaventa")
@NamedQueries({
    @NamedQuery(name = "Personaventa.findAll", query = "SELECT p FROM Personaventa p")
    , @NamedQuery(name = "Personaventa.findById", query = "SELECT p FROM Personaventa p WHERE p.id = :id")
    , @NamedQuery(name = "Personaventa.findByEstado", query = "SELECT p FROM Personaventa p WHERE p.estado = :estado")})
public class Personaventa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "idpersona", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Persona idpersona;
    @Column(name = "idventa")
     private Long  idventa;

    public Personaventa() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personaventa)) {
            return false;
        }
        Personaventa other = (Personaventa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Personaventa[ id=" + id + " ]";
    }
    
}
