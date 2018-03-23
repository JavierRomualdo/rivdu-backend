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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "programas")
@NamedQueries({
    @NamedQuery(name = "Programas.findAll", query = "SELECT p FROM Programas p")})
public class Programas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codigoet")
    private String codigoet;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "correlativocontrato1")
    private String correlativocontrato1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "correlativocontrato2")
    private String correlativocontrato2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
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
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
   /* @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprograma")
    private List<Ahorroporprograma> ahorroporprogramaList;*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprograma")
    private List<Responsable> responsableList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprograma")
    private List<Programaespecificacion> programaespecificacionesList;

    public Programas() {
    }

    public Programas(Long id) {
        this.id = id;
    }

    public Programas(Long id, String codigoet, String correlativocontacto, boolean estado, BigDecimal importe, BigDecimal maximovalor, String nombre) {
        this.id = id;
        this.codigoet = codigoet;
        this.correlativocontrato1 = correlativocontacto;
        this.estado = estado;
        this.importe = importe;
        this.maximovalor = maximovalor;
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
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
