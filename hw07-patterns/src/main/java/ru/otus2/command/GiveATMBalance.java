package ru.otus2.command;

import ru.otus2.ATM;
import ru.otus2.ATMCell;
import ru.otus2.BanknoteAmountEnum;
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
