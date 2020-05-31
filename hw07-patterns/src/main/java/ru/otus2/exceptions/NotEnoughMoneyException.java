package ru.otus2.exceptions;

public class NotEnoughMoneyException extends ATMException {

    public  NotEnoughMoneyException(String message) {
        super(message);
    }
}
