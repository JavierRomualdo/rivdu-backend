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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "expediente")
@NamedQueries({
    @NamedQuery(name = "Expediente.findAll", query = "SELECT e FROM Expediente e")})
public class Expediente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 200)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 150)
    @Column(name = "url")
    private String url;
    @Lob
    @Column(name = "file")
    private byte[] file;
    @Size(max = 50)
    @Column(name = "tipofile")
    private String tipofile;
    @Size(max = 150)
    @Column(name = "contenttype")
    private String contenttype;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "idtipoexpediente", referencedColumnName = "id")
    @ManyToOne
    private Tipoexpediente idtipoexpediente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expediente")
    private List<Compraexpediente> compraexpedienteList;

    public Expediente() {
    }

    public Expediente(Long id) {
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
        if (!(object instanceof Expediente)) {
            return false;
        }
        Expediente other = (Expediente) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Expediente[ id=" + id + " ]";
    }
    
}
