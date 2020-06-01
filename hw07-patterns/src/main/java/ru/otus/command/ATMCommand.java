package ru.otus.command;

import ru.otus.ATM;

@FunctionalInterface
public interface ATMCommand {
    long execute(ATM atm);
}
