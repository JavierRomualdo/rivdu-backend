package com.rivdu.dto;

import java.util.List;
import lombok.Data;

@Data
public class ExpedientesDTO {

    private Long id;
    private Long idcompra;
    private String label;
    private String expandedIcon = "fa-folder-open";
    private String collapsedIcon = "fa-folder";
    private List<ExpedienteChildrenDTO> children;
    

    public ExpedientesDTO() {
    }
    
}
