package ru.otus.command;

import ru.otus.BanknoteAmountEnum;
import ru.otus.ATM;
import ru.otus.ATMCell;

import java.util.Map;

public class GiveATMBalance implements ATMCommand {
    @Override
    public long execute(ATM atm) {
        long balance = 0;
        for (Map.Entry<BanknoteAmountEnum, ATMCell> cell : atm.getCells().entrySet()) {
            balance += cell.getKey().getAmount() * cell.getValue().getBanknotesCount();
        }
        return balance;
    }
}
