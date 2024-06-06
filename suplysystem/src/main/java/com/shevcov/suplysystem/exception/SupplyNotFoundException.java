package com.shevcov.suplysystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SupplyNotFoundException extends ResponseStatusException {

    public SupplyNotFoundException(String reason){
        super(HttpStatus.NOT_FOUND, reason);
    }
}
