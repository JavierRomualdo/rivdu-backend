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
@Table(name = "personarol")
@NamedQueries({
    @NamedQuery(name = "Personarol.findAll", query = "SELECT p FROM Personarol p")})
public class Personarol implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PersonarolPK personarolPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "idrol", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private     Rol idrol;
    @JoinColumn(name = "idpersona", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona idpersona;

    public Personarol() {
    }

    public Personarol(PersonarolPK personarolPK) {
        this.personarolPK = personarolPK;
    }

    public Personarol(PersonarolPK personarolPK, boolean estado) {
        this.personarolPK = personarolPK;
        this.estado = estado;
    }

    public Personarol(long idpersona, long idrol) {
        this.personarolPK = new PersonarolPK(idpersona, idrol);
    }

    public PersonarolPK getPersonarolPK() {
        return personarolPK;
    }

    public void setPersonarolPK(PersonarolPK personarolPK) {
        this.personarolPK = personarolPK;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personarolPK != null ? personarolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personarol)) {
            return false;
        }
        Personarol other = (Personarol) object;
        if ((this.personarolPK == null && other.personarolPK != null) || (this.personarolPK != null && !this.personarolPK.equals(other.personarolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Personarol[ personarolPK=" + personarolPK + " ]";
    }
    
}
