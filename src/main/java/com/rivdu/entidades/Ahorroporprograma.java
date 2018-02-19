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
import lombok.Data;

/**
 *
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "ahorroporprograma")
@NamedQueries({
    @NamedQuery(name = "Ahorroporprograma.findAll", query = "SELECT a FROM Ahorroporprograma a")})
public class Ahorroporprograma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ahorro")
    private BigDecimal ahorro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "idubigeo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ubigeo idubigeo;
    @JoinColumn(name = "idprograma", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Programas idprograma;

    public Ahorroporprograma() {
    }

    public Ahorroporprograma(Long id) {
        this.id = id;
    }

    public Ahorroporprograma(Long id, BigDecimal ahorro, boolean estado) {
        this.id = id;
        this.ahorro = ahorro;
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
        if (!(object instanceof Ahorroporprograma)) {
            return false;
        }
        Ahorroporprograma other = (Ahorroporprograma) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Ahorroporprograma[ id=" + id + " ]";
    }
    
}
