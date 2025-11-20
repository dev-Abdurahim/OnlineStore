package org.example.onlinestore.exception.customexception;


public class InsufficientStockException extends RuntimeException{

    public InsufficientStockException(String productName, int requested, int available){
        super(String.format("Omborda yetarli mahsulot yoq! %s soraldi: %d, mavjud: %d",
                productName, requested, available));
    }
}
