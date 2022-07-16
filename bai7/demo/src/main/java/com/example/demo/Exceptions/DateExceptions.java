package com.example.demo.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_ACCEPTABLE)
public class DateExceptions extends RuntimeException{
    public DateExceptions(String sms){
        super(sms);
    }
}
