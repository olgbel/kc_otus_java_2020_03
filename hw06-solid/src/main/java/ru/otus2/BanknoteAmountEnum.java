package ru.otus2;

public enum BanknoteAmountEnum {
    FIFTY(50),
    HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private final int amount;

    BanknoteAmountEnum(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
