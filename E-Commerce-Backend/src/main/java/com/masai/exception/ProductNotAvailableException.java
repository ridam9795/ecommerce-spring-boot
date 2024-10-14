package com.masai.exception;

public class ProductNotAvailableException extends RuntimeException{
    public ProductNotAvailableException() {
        // TODO Auto-generated constructor stub
    }

    public ProductNotAvailableException(String message){
        super(message);
    }
}
