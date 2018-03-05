/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paginador;

/**
 *
 * @author LUIS ORTIZ
 */
public class PaginaItem {
    private int numero;
    private boolean actual;

    public PaginaItem(int numero, boolean actual) {
        this.numero = numero;
        this.actual = actual;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isActual() {
        return actual;
    }
    
            
}
