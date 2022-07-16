package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PAYLOAD_TOO_LARGE)
public class TooLongSize extends RuntimeException{
    public TooLongSize(String sms){
        super(sms);
    }
}
