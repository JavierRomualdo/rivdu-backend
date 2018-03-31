/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.dto;

/**
 *
 * @author MarioMario
 */
public class ExpedienteChildrenDTO {
    
    public Long id;
    public String nombre;
    public String tipo;
    public String label;
    public String icon;
    public String contenttype;

    public ExpedienteChildrenDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.label = this.nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        this.label = this.nombre;
        setIcon(icon);
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = this.nombre;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        switch(this.tipo.toLowerCase()){
            case "pdf":
                this.icon = "fa-file-pdf-o";
                break;
            case "doc":
            case "docx":
                this.icon = "fa-file-word-o";
                break;
            case "jpg":
            case "png":
            case "gif":
            case "bmp":
                this.icon = "fa-file-image-o";
                break;
            case "xls":
            case "xlsx":
                this.icon = "fa-file-excel-o";
                break;
            default:
                this.icon = "fa-file-o";
                break;    
        }
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }
    
}
