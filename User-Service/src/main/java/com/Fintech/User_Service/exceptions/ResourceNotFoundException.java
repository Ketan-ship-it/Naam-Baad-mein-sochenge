package com.Fintech.User_Service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){}
    public ResourceNotFoundException(String message){
        super(message);
    }
}
