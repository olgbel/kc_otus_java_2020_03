package ru.otus.memento;

import ru.otus.Banknote;
import ru.otus.BanknoteAmountEnum;
import ru.otus.ATMCellImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMMemento {
    private final Map<BanknoteAmountEnum, ATMCellImpl> cells;

    public ATMMemento(Map<BanknoteAmountEnum, ATMCellImpl> cells) {
        Map<BanknoteAmountEnum, ATMCellImpl> mementoCells = new HashMap<>();
        cells.forEach((banknoteAmount, atmCell) -> {
            List<Banknote> banknotes = new ArrayList<>();
            for (int i = 0; i < atmCell.getBanknotesCount(); i++) {
                banknotes.add(new Banknote(banknoteAmount));
            }
            mementoCells.put(banknoteAmount, new ATMCellImpl(banknotes));
        });
        this.cells = mementoCells;
    }

    public Map<BanknoteAmountEnum, ATMCellImpl> getState() {
        return cells;
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}
