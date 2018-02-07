/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "especificaciones")
public class Especificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "etapa")
    private String etapa;
    @Size(max = 100)
    @Column(name = "espcificaciones")
    private String espcificaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estadoconstruccion")
    private Character estadoconstruccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorM2")
    private BigDecimal valorM2;
    @JoinColumn(name = "idprograma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Programas idprograma;
    @JoinColumn(name = "idestructura", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estructura idestructura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    
    public Especificaciones() {
    }

    public Especificaciones(Integer id) {
        this.id = id;
    }

    public Especificaciones(Integer id, String etapa, Character estadoconstruccion, BigDecimal valorM2) {
        this.id = id;
        this.etapa = etapa;
        this.estadoconstruccion = estadoconstruccion;
        this.valorM2 = valorM2;
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
        if (!(object instanceof Especificaciones)) {
            return false;
        }
        Especificaciones other = (Especificaciones) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Especificaciones[ id=" + id + " ]";
    }
    
}
