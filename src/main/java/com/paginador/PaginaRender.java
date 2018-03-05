/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paginador;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author LUIS ORTIZ
 */
public class PaginaRender<T> {
    private String url;
    private Page<T> page;
    
    private int totalPaginas;
    private int numElementosPorpaginas;
    private int paginaActual;
    private List<PaginaItem> paginas;
    
    public PaginaRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PaginaItem>();
        
        numElementosPorpaginas = page.getSize();
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1 ;
        
        int desde, hasta;
        if (totalPaginas <=numElementosPorpaginas) {
            desde = 1;
            hasta = totalPaginas;
        }else{
            if (paginaActual <= numElementosPorpaginas/2) {
                desde = 1;
                hasta = numElementosPorpaginas;
            }else if (paginaActual >= totalPaginas- numElementosPorpaginas/2) {
                desde = totalPaginas - numElementosPorpaginas + 1 ;
                hasta = numElementosPorpaginas;
            }else{
                desde = paginaActual-numElementosPorpaginas/2;
                hasta = numElementosPorpaginas;
            }
        }
        for (int i = 0; i < hasta; i++) {
            paginas.add(new PaginaItem(desde + i , paginaActual == desde + i));
        }
    }

    public String getUrl() {
        return url;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PaginaItem> getPaginas() {
        return paginas;
    }
    
    public boolean isFirst(){
        return page.isFirst();
    }
    public boolean isLast(){
        return page.isLast();
    }
    public boolean isHasNext(){
        return page.hasNext();
    }
    public boolean isHasPrevious(){
        return page.hasPrevious();  
    }
     
        
     
    
}
