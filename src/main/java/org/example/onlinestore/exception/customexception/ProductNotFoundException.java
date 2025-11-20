package org.example.onlinestore.exception.customexception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("Mahsulot topilmadi: ID = " + id);
    }
    public ProductNotFoundException(String message){
        super(message);
    }
}
