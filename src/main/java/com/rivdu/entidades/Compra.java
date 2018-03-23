/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author javie
 */
@Data
@Entity
@Table(name = "compra")
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")})
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "idpredio", referencedColumnName = "id")
    @ManyToOne
    private Predio idpredio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @OneToMany(mappedBy = "idcompra")
    private List<Captador> captadorList;
    @OneToMany(mappedBy = "idcompra")
    private List<Personacompra> personacosmpraList;
//    @OneToMany(mappedBy = "idcompra")
//    private List<Captador> captadorList;
//    @OneToMany(mappedBy = "idcompra")
//    private List<Personacompra> personacompraList;

    public Compra() {
    }

    public Compra(Long id) {
        this.id = id;
    }
    
//    public List<Captador> getCaptadorList() {
//        return captadorList;
//    }
//
//    public void setCaptadorList(List<Captador> captadorList) {
//        this.captadorList = captadorList;
//    }
//
//    public List<Personacompra> getPersonacompraList() {
//        return personacompraList;
//    }
//
//    public void setPersonacompraList(List<Personacompra> personacompraList) {
//        this.personacompraList = personacompraList;
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
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Compra[ id=" + id + " ]";
    }
}
