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
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author javie
 */
@Data
@Entity
@Table(name = "colindante")
@NamedQueries({
    @NamedQuery(name = "Colindante.findAll", query = "SELECT c FROM Colindante c")})
public class Colindante implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "area")
    private Double area;
    @Size(max = 30)
    @Column(name = "frente1")
    private String frente1;
    @Size(max = 30)
    @Column(name = "frente2")
    private String frente2;
    @Size(max = 30)
    @Column(name = "derecha1")
    private String derecha1;
    @Size(max = 30)
    @Column(name = "derecha2")
    private String derecha2;
    @Size(max = 30)
    @Column(name = "izquierda1")
    private String izquierda1;
    @Size(max = 30)
    @Column(name = "izquierda2")
    private String izquierda2;
    @Size(max = 30)
    @Column(name = "fondo1")
    private String fondo1;
    @Size(max = 30)
    @Column(name = "fondo2")
    private String fondo2;
    @JoinColumn(name = "idpredio", referencedColumnName = "id")
    @ManyToOne
    private Predio idpredio;

    public Colindante() {
    }

    public Colindante(Long id) {
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colindante)) {
            return false;
        }
        Colindante other = (Colindante) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Colindante[ id=" + id + " ]";
    }
    
}
