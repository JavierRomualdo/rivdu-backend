package com.rivdu.excepcion;

import org.slf4j.Logger;

public final class GeneralException extends RuntimeException {

    public GeneralException(String message, Throwable cause, final Logger log) {
        super(message, cause);
        log.error(message+ " a causa de: " +cause);
    }

    public GeneralException(final String mensaje, final String causa, final Logger log) {
        super(mensaje);
        log.error(mensaje+ " a causa de: " +causa);
    }

}
