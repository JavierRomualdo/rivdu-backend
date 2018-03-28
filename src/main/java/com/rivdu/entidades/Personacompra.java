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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author javie
 */
@Data
@Entity
@Table(name = "personacompra")
@NamedQueries({
    @NamedQuery(name = "Personacompra.findAll", query = "SELECT p FROM Personacompra p")})
public class Personacompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "idcompra", referencedColumnName = "id")
    @ManyToOne
    private Compra idcompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "idpersona", referencedColumnName = "id")
    @ManyToOne
    private Persona idpersona;
    @JoinColumn(name = "idrelacion", referencedColumnName = "id")
    @ManyToOne
    private Relacion idrelacion;
    @JoinColumn(name = "idestadocliente", referencedColumnName = "id")
    @ManyToOne
    private Estadocliente idestadocliente;

    public Personacompra() {
    }

    public Personacompra(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Personacompra)) {
            return false;
        }
        Personacompra other = (Personacompra) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Personacompra[ id=" + id + " ]";
    }
    
}
