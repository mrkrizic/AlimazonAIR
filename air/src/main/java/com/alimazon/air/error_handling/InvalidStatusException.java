package com.alimazon.air.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception if an invalid status is being passed
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException(String message) {
        super(message);
    }
}
