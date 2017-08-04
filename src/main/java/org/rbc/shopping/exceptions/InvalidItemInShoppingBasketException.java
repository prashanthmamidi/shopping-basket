package org.rbc.shopping.exceptions;


public class InvalidItemInShoppingBasketException extends RuntimeException {
    public InvalidItemInShoppingBasketException(String message) {
        super(message);
    }
}
