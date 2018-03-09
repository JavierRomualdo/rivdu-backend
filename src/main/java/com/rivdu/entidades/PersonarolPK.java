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
public class PersonarolPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idpersona")
    private long idpersona;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idrol")
    private long idrol;

    public PersonarolPK() {
    }

    public PersonarolPK(long idpersona, long idrol) {
        this.idpersona = idpersona;
        this.idrol = idrol;
    }

    public long getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(long idpersona) {
        this.idpersona = idpersona;
    }

    public long getIdrol() {
        return idrol;
    }

    public void setIdrol(long idrol) {
        this.idrol = idrol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idpersona;
        hash += (int) idrol;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonarolPK)) {
            return false;
        }
        PersonarolPK other = (PersonarolPK) object;
        if (this.idpersona != other.idpersona) {
            return false;
        }
        if (this.idrol != other.idrol) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.PersonarolPK[ idpersona=" + idpersona + ", idrol=" + idrol + " ]";
    }
    
}
