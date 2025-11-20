package org.example.onlinestore.exception.customexception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long id){
        super("Buyurtma topilmadi: ID = " + id);
    }

}
