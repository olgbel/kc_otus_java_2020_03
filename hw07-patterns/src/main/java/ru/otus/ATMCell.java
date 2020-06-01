package ru.otus;

import ru.otus.exceptions.NotEnoughMoneyException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ATMCell implements IATMCell {
    private final List<Banknote> banknotes;

    public ATMCell(List<Banknote> banknotes) {
        this.banknotes = banknotes;
    }

    public int getBanknotesCount() {
        return this.banknotes.size();
    }

    public void put(Banknote banknote) {
        banknotes.add(banknote);
    }

    public Banknote withdraw() {
        if (banknotes.isEmpty()) {
            throw new NotEnoughMoneyException("There is no banknotes in the cell.");
        }
        return banknotes.remove(banknotes.size() - 1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banknotes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) return false;

        ATMCell cell = (ATMCell) obj;
        return Arrays.equals(cell.banknotes.toArray(), banknotes.toArray());
    }

    @Override
    public String toString() {
        return this.banknotes.toString();
    }
}
