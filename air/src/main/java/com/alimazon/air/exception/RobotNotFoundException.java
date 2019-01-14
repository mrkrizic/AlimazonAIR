package com.alimazon.air.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RobotNotFoundException extends Exception {

    public RobotNotFoundException(String message){
        super(message);
    }
}
