package org.example.exception;

public class ItemAlreadyBorrowedException extends RuntimeException {
    public ItemAlreadyBorrowedException(String message) {
        super(message);
    }
}
