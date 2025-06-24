package com.service.category.Exceptions;

public class ResourceNotPresentException extends  RuntimeException{
    public ResourceNotPresentException (){
        super("Resource not present");
    }

    public ResourceNotPresentException(String courseNotPresent){
    super(courseNotPresent);
    }
}