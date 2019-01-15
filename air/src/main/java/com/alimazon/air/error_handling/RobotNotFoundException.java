package com.alimazon.air.error_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RobotNotFoundException extends RuntimeException {

    public RobotNotFoundException(String message){
        super(message);
    }
}
