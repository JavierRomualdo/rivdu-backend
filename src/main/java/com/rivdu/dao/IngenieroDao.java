/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rivdu.dao;

import com.rivdu.entidades.Persona;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author LUIS ORTIZ
 */
public interface IngenieroDao extends PagingAndSortingRepository<Persona, Long>{
    
}
