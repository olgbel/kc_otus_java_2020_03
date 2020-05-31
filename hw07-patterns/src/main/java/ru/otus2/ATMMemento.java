package ru.otus2;

import java.util.Map;

public class ATMMemento {
    private final Map<BanknoteAmountEnum, ATMCell> cells;

    public ATMMemento(Map<BanknoteAmountEnum, ATMCell> cells) {
        this.cells = cells;
    }

    public Map<BanknoteAmountEnum, ATMCell> getState() {
        return cells;
    }

    @Override
    public String toString() {
        return cells.toString();
    }
}
