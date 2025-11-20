package org.example.onlinestore.exception.globalexception;

/**
 * Barcha business xatolar uchun umumiy parent exception
 * ertaga yana qanaqadir exception qoshmoqchi bose shundan meros olamiz
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
