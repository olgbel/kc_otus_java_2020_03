package ru.otus.command;

import ru.otus.BanknoteAmountEnum;
import ru.otus.ATMImpl;
import ru.otus.ATMCellImpl;

import java.util.Map;

public class GiveATMBalance implements ATMCommand {
    @Override
    public long execute(ATMImpl atm) {
        long balance = 0;
        for (Map.Entry<BanknoteAmountEnum, ATMCellImpl> cell : atm.getCells().entrySet()) {
            balance += cell.getKey().getAmount() * cell.getValue().getBanknotesCount();
        }
        return balance;
    }
}
