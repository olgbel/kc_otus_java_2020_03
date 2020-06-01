package ru.otus2.command;

import ru.otus2.ATM;

@FunctionalInterface
public interface ATMCommand {
    long execute(ATM atm);
}
