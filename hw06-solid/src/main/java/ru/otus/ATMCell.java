package ru.otus;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ATMCell {
    private final List<Banknote> banknotes;

    public ATMCell(List<Banknote> banknotes) {
        this.banknotes = banknotes;
    }

    public List<Banknote> getBanknotes() {
        return this.banknotes;
    }

    public void put(Banknote banknote) {
        banknotes.add(banknote);
    }

    public Banknote withdraw() {
        return banknotes.remove(0);
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
