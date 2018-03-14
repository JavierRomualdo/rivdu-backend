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
 * @author MarioMario
 */
@Data
@Entity
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @Size(max = 300)
    @Column(name = "avcajiron")
    private String avcajiron;
    @Size(max = 10)
    @Column(name = "lote")
    private String lote;
    @Size(max = 10)
    @Column(name = "manzana")
    private String manzana;
    @Size(max = 20)
    @Column(name = "numeropartida")
    private String numeropartida;
    @Size(max = 50)
    @Column(name = "oficinaregistral")
    private String oficinaregistral;
    @Size(max = 255)
    @Column(name = "razonsocial")
    private String razonsocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "ruc")
    private String ruc;
    @Size(max = 100)
    @Column(name = "urbanizacion")
    private String urbanizacion;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="idempresa")
//    private List<Sucursal> sucursalList;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="idempresa")
//    private List<Usuario> usuarioList;
    @JoinColumn(name = "idubigeo", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Ubigeo idubigeo;
    
     @JoinColumn(name = "idgerente", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Persona idgerente;
     
     

    public Empresa() {
    }

    public Empresa(Long id) {
        this.id = id;
    }

    public Empresa(Long id, boolean estado, String numeropartida, String ruc) {
        this.id = id;
        this.estado = estado;
        this.numeropartida = numeropartida;
        this.ruc = ruc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.rivdu.entidades.Empresa[ id=" + id + " ]";
    }
    
}
