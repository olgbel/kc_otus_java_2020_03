package ru.otus2.exceptions;

public class IncorrectFormatAmountException extends ATMException {

    public  IncorrectFormatAmountException(String message) {
        super(message);
    }
}
