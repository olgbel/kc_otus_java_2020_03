package ru.otus.exceptions;

public class IncorrectFormatAmountException extends ATMException {
    public  IncorrectFormatAmountException(String message) {
        super(message);
    }
}
