package org.example.onlinestore.exception.customexception;

public class InvalidOrderStatusException extends RuntimeException{

    public InvalidOrderStatusException(String message){
        super(message);
    }

    public InvalidOrderStatusException(Long orderId, String currentStatus, String requestedStatus){
        super(String.format("Buyurtma %d uchun status o'zgartirib bo'lmaydi! Hozirgi: %s → So'ralgan: %s",
                orderId, currentStatus, requestedStatus));
    }
}
