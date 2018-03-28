/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.dto;

import com.rivdu.entidades.Captador;
import com.rivdu.entidades.Colindante;
import com.rivdu.entidades.Compra;
import com.rivdu.entidades.Personacompra;
import com.rivdu.entidades.Predio;
import com.rivdu.entidades.Servicios;
import lombok.Data;

/**
 *
 * @author javie
 */

@Data
public class SaveCompraDTO {
    private Compra compra;
    private Predio predio;
    private Colindante colindante;
    private Servicios[] servicios;
    private Captador captador;
    private Personacompra [] personacompra;
    private Personacompra [] personacompra2;

    public SaveCompraDTO() {
    }
}
