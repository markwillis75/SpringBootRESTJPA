package com.mkwillis.springboot2restservicebasic.entity;

/**
 * Created by Mark on 20/10/2018.
 */
public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String str){
        super (str);
    }
}
