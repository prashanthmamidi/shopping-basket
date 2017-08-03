package org.rbc.shopping.exceptions;


public class InvalidItemInBasketException extends RuntimeException {
    public InvalidItemInBasketException(String message) {
        super(message);
    }
}
