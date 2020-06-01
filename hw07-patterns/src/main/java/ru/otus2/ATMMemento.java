package ru.otus2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMMemento {
    private final Map<BanknoteAmountEnum, ATMCell> cells;

    public ATMMemento(Map<BanknoteAmountEnum, ATMCell> cells) {
        Map<BanknoteAmountEnum, ATMCell> mementoCells = new HashMap<>();
        cells.forEach((banknoteAmount, atmCell) -> {
            List<Banknote> banknotes = new ArrayList<>();
            for (int i = 0; i < atmCell.getBanknotesCount(); i++) {
                banknotes.add(new Banknote(banknoteAmount));
            }
            mementoCells.put(banknoteAmount, new ATMCell(banknotes));
        });
        this.cells = mementoCells;
    }

    public Map<BanknoteAmountEnum, ATMCell> getState() {
        return cells;
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}
