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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "especificaciones")
@NamedQueries({
    @NamedQuery(name = "Especificaciones.findAll", query = "SELECT e FROM Especificaciones e")})
public class Especificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 100)
    @Column(name = "especificaciones")
    private String especificaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "categoria")
    private Character categoria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "etapa")
    private String etapa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valorm2")
    private BigDecimal valorm2;
    
    @JoinColumn(name = "idestructura", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estructura idestructura;

    public Especificaciones() {
    }

    public Especificaciones(Long id) {
        this.id = id;
    }

    public Especificaciones(Long id, boolean estado, Character estadoconstruccion, String etapa, BigDecimal valorm2) {
        this.id = id;
        this.estado = estado;
        this.categoria = estadoconstruccion;
        this.etapa = etapa;
        this.valorm2 = valorm2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
