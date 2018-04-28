package com.rivdu.dto;

import java.util.List;
import lombok.Data;

@Data
public class RolMenuDTO {

    private Long idrol;
    private List<Long> ids;

    public RolMenuDTO() {
    }

}
