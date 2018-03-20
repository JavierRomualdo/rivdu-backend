/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "predio")
@NamedQueries({
    @NamedQuery(name = "Predio.findAll", query = "SELECT p FROM Predio p")})
public class Predio implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;

    @JoinColumn(name = "idCompra", referencedColumnName = "id")
    @ManyToOne
    private Compra idCompra;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 20)
    @Column(name = "partida")
    private String partida;
    @Size(max = 200)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Size(max = 10)
    @Column(name = "mz")
    private String mz;
    @Size(max = 10)
    @Column(name = "lote")
    private String lote;
    @Size(max = 10)
    @Column(name = "sublote")
    private String sublote;
    @Size(max = 50)
    @Column(name = "frente")
    private String frente;
    @Size(max = 20)
    @Column(name = "codigosnip")
    private String codigosnip;
//    @OneToMany(mappedBy = "idpredio")
//    private List<Predioservicio> predioservicioList;
//    @OneToMany(mappedBy = "idpredio")
//    private List<Compra> compraList;
    @JoinColumn(name = "idubigeo", referencedColumnName = "id")
    @ManyToOne
    private Ubigeo idubigeo;
//    @OneToMany(mappedBy = "idpredio")
//    private List<Colindante> colindanteList;

    public Predio() {
    }

    public Predio(Long id) {
        this.id = id;
    }

//    public List<Predioservicio> getPredioservicioList() {
//        return predioservicioList;
//    }
//
//    public void setPredioservicioList(List<Predioservicio> predioservicioList) {
//        this.predioservicioList = predioservicioList;
//    }
//
//    public List<Compra> getCompraList() {
//        return compraList;
//    }
//
//    public void setCompraList(List<Compra> compraList) {
//        this.compraList = compraList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Predio)) {
            return false;
        }
        Predio other = (Predio) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Predio[ id=" + id + " ]";
    }
    
}
