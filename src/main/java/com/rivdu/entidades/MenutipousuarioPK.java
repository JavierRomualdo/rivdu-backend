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
public class MenutipousuarioPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idmenu")
    private long idmenu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idtipousuario")
    private long idtipousuario;

    public MenutipousuarioPK() {
    }

    public MenutipousuarioPK(long idmenu, long idtipousuario) {
        this.idmenu = idmenu;
        this.idtipousuario = idtipousuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idmenu;
        hash += (int) idtipousuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MenutipousuarioPK)) {
            return false;
        }
        MenutipousuarioPK other = (MenutipousuarioPK) object;
        if (this.idmenu != other.idmenu) {
            return false;
        }
        return this.idtipousuario == other.idtipousuario;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.MenutipousuarioPK[ idmenu=" + idmenu + ", idtipousuario=" + idtipousuario + " ]";
    }
    
}
