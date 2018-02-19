package com.rivdu.util;

import lombok.*;
import com.rivdu.dto.SessionItemDTO;

@Data
@EqualsAndHashCode(callSuper = false)
public class SessionResponse extends Respuesta {
    private SessionItemDTO item;
}
