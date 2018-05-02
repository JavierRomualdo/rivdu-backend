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
import javax.persistence.FetchType;
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
 * @author PROPIETARIO
 */
@Entity
@Table(name = "venta")
@Data
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")
    , @NamedQuery(name = "Venta.findById", query = "SELECT v FROM Venta v WHERE v.id = :id")
    , @NamedQuery(name = "Venta.findByCorrelativo", query = "SELECT v FROM Venta v WHERE v.correlativo = :correlativo")
    , @NamedQuery(name = "Venta.findBySerie", query = "SELECT v FROM Venta v WHERE v.serie = :serie")
    , @NamedQuery(name = "Venta.findByEstado", query = "SELECT v FROM Venta v WHERE v.estado = :estado")
    , @NamedQuery(name = "Venta.findByCopialiteral", query = "SELECT v FROM Venta v WHERE v.copialiteral = :copialiteral")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "correlativo")
    private int correlativo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "serie")
    private String serie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @Size(min=1, max = 90)
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuariocrea")
    private String usuariocrea;
    @Size(min=1, max = 90)
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuarioeditaa")
    private String usuarioedita;
    @Basic(optional = false)
    @NotNull
    @Column(name = "copialiteral")
    private boolean copialiteral;
    @JoinColumn(name = "idpredio", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Predio idpredio;
    @JoinColumn(name = "idprograma", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Programas idprograma;
    @JoinColumn(name = "idusuariovende", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Usuario idusuariovende;

    public Venta() {
    }

    public Venta(Long id) {
        this.id = id;
    }

    public Venta(Long id, int correlativo, String serie, boolean estado, boolean copialiteral) {
        this.id = id;
        this.correlativo = correlativo;
        this.serie = serie;
        this.estado = estado;
        this.copialiteral = copialiteral;
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
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Venta[ id=" + id + " ]";
    }
    
}
