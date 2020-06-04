package ru.otus.command;

import ru.otus.ATMImpl;

@FunctionalInterface
public interface ATMCommand {
    long execute(ATMImpl atm);
}
