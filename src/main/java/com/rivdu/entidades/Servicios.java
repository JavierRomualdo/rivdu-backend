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
@Table(name = "servicios")
@NamedQueries({
    @NamedQuery(name = "Servicios.findAll", query = "SELECT s FROM Servicios s")})
public class Servicios implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
//    @OneToMany(mappedBy = "idservicio")
//    private List<Predioservicio> predioservicioList;

    public Servicios() {
    }

    public Servicios(Long id) {
        this.id = id;
    }

    

//    public List<Predioservicio> getPredioservicioList() {
//        return predioservicioList;
//    }
//
//    public void setPredioservicioList(List<Predioservicio> predioservicioList) {
//        this.predioservicioList = predioservicioList;
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
        if (!(object instanceof Servicios)) {
            return false;
        }
        Servicios other = (Servicios) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Servicios[ id=" + id + " ]";
    }
    
}
