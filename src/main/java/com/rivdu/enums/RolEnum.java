/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.enums;

/**
 *
 * @author PROPIETARIO
 */
public enum RolEnum {
    
    PARAMETRO_ID_CAPTADOR(new Long(2)),
    PARAMETRO_ID_ADMINISTRADOR(new Long(1));
	
	private Long value;

	private RolEnum(Long valor) {
		this.value = valor;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
