/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "programas")
public class Programas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codigoET")
    private String codigoET;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "importe")
    private BigDecimal importe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maximovalor")
    private BigDecimal maximovalor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "correlativocontacto")
    private String correlativocontacto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprograma")
    private List<Responsable> responsableList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprograma")
    private List<Ahorroporprograma> ahorroporprogramaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprograma")
    private List<Especificaciones> especificacionesList;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;

    public Programas() {
    }

    public Programas(Integer id) {
        this.id = id;
    }

    public Programas(Integer id, String nombre, String codigoET, BigDecimal importe, BigDecimal maximovalor, String correlativocontacto) {
        this.id = id;
        this.nombre = nombre;
        this.codigoET = codigoET;
        this.importe = importe;
        this.maximovalor = maximovalor;
        this.correlativocontacto = correlativocontacto;
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
        if (!(object instanceof Programas)) {
            return false;
        }
        Programas other = (Programas) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Programas[ id=" + id + " ]";
    }
    
}
