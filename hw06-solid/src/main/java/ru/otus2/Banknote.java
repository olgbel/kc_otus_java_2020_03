package ru.otus2;

import java.util.Objects;

public class Banknote {
    private final BanknoteAmountEnum amount;

    public Banknote(BanknoteAmountEnum amount) {
        this.amount = amount;
    }

    public BanknoteAmountEnum getAmount() {
        return this.amount;
    }

    @Override
    public String toString() {
        return this.amount.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) return false;

        Banknote objB = (Banknote) obj;
        return this.amount == objB.amount;
    }
}
