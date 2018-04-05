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
@Table(name = "predio")
@NamedQueries({
    @NamedQuery(name = "Predio.findAll", query = "SELECT p FROM Predio p")})
public class Predio implements Serializable {
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
    @JoinColumn(name = "idubigeo", referencedColumnName = "id")
    @ManyToOne
    private Ubigeo idubigeo;

    public Predio() {
    }

    public Predio(Long id) {
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
