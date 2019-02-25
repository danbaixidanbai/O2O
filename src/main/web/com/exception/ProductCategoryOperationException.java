package com.exception;

public class ProductCategoryOperationException extends RuntimeException {
    public ProductCategoryOperationException(String msg){
        super(msg);
    }
}
