/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "menuopcion")
@NamedQueries({
    @NamedQuery(name = "Menuopcion.findAll", query = "SELECT m FROM Menuopcion m")})
public class Menuopcion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "idopcion", referencedColumnName = "id")
    @ManyToOne
    private Opcion idopcion;
    @JoinColumn(name = "idmenu", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Menu idmenu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmenuopcion")
    private List<Tipousuariomenuopcion> tipousuariomenuopcionList;

    public Menuopcion() {
    }

    public Menuopcion(Long id) {
        this.id = id;
    }

    public Menuopcion(Long id, boolean estado) {
        this.id = id;
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Menuopcion)) {
            return false;
        }
        Menuopcion other = (Menuopcion) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Menuopcion[ id=" + id + " ]";
    }
    
}
