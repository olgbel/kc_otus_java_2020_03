package ru.otus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMMemento {
    private final Map<BanknoteAmountEnum, ATMCell> cells;

    public ATMMemento(Map<BanknoteAmountEnum, ATMCell> cells) {
        Map<BanknoteAmountEnum, ATMCell> ccc = new HashMap<>();
        cells.forEach((key, value) -> {
            List<Banknote> banknotes = new ArrayList<>();
            for (int i = 0; i < value.getBanknotesCount(); i++) {
                banknotes.add(new Banknote(key));
            }
            ccc.put(key, new ATMCell(banknotes));
        });
        this.cells = ccc;
    }

    public Map<BanknoteAmountEnum, ATMCell> getState() {
        return cells;
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}
