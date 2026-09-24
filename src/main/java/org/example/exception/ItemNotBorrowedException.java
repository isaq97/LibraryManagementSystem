package org.example.exception;

public class ItemNotBorrowedException extends RuntimeException {
    public ItemNotBorrowedException(String message) {
        super(message);
    }
}
